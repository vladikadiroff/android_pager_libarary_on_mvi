package ru.vladikadiroff.pagination.ui.fragments

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.vladikadiroff.pagination.R
import ru.vladikadiroff.pagination.databinding.FragmentPhotosBinding
import ru.vladikadiroff.pagination.presentation.models.PhotosViewAction
import ru.vladikadiroff.pagination.presentation.models.PhotosViewEvent
import ru.vladikadiroff.pagination.presentation.models.PhotosViewState
import ru.vladikadiroff.pagination.presentation.viewmodels.PhotosViewModel
import ru.vladikadiroff.pagination.ui.adapters.LoadStateAdapter
import ru.vladikadiroff.pagination.ui.adapters.PhotosAdapter
import ru.vladikadiroff.pagination.utils.abstracts.MviFragment
import ru.vladikadiroff.pagination.utils.extensions.doOnApplyWindowInsets
import ru.vladikadiroff.pagination.utils.extensions.showToast
import ru.vladikadiroff.pagination.utils.extensions.withLifecycleHandler
import ru.vladikadiroff.pagination.utils.helpers.ShimmerWithLifecycleHandler
import javax.inject.Inject

@AndroidEntryPoint
class PhotosFragment : MviFragment<FragmentPhotosBinding, PhotosViewState,
        PhotosViewAction, PhotosViewEvent, PhotosViewModel>() {

    @Inject
    lateinit var adapter: PhotosAdapter
    private lateinit var loadingScreen: ShimmerWithLifecycleHandler
    override val viewModel: PhotosViewModel by viewModels()
    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) ->
    FragmentPhotosBinding = FragmentPhotosBinding::inflate

    override fun initFragment() {
        postponeEnterTransition()
        initViews()
        initAdapter()
    }

    private fun initViews() = with(binding){
        list.doOnPreDraw { startPostponedEnterTransition() }
        this@PhotosFragment.loadingScreen = loadingScreen.withLifecycleHandler(viewLifecycleOwner)
        swipeRefresh.setOnRefreshListener { postEvent(PhotosViewEvent.Refresh) }
        list.adapter = adapter.withLoadStateFooter(LoadStateAdapter(adapter::retry))
        list.doOnApplyWindowInsets { list, insets, _ ->
            list.updatePadding(bottom = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom)
            insets
        }
    }

    private fun initAdapter() {
        adapter.addEventListener { postEvent(it) }
        adapter.addLoadStateListener { postEvent(PhotosViewEvent.PagingLoadState(it)) }
    }

    override fun render(state: PhotosViewState) {
        loadingScreen.isVisible = state.loadingScreen
        binding.swipeRefresh.isRefreshing = state.refresh
        binding.errorScreen.isVisible = state.errorScreen
        state.pager?.let { lifecycleScope.launch { adapter.submitData(it) } }
    }

    override fun renderAction(action: PhotosViewAction) = when (action) {
        is PhotosViewAction.ShowToast -> showToast(action.msg)
        is PhotosViewAction.RefreshList -> adapter.refresh()
        is PhotosViewAction.StopSwipeRefresh -> binding.swipeRefresh.isRefreshing = false
        is PhotosViewAction.ScrollListToStartPosition -> binding.list.scrollToPosition(0)
        is PhotosViewAction.SharePhoto -> sharePhoto(action.url)
        is PhotosViewAction.ShowPhotoInfo -> findNavController().navigate(
           PhotosFragmentDirections.actionToPhotoInfo(action.model))
        is PhotosViewAction.NavigateToPhotoFullScreen ->  findNavController().navigate(
            PhotosFragmentDirections.actionToPhotoFullScreen(action.model), action.extras)

    }

    private fun sharePhoto(url: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, getString(R.string.share)))
    }

}