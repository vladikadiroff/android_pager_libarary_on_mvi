package ru.vladikadiroff.pagination.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.vladikadiroff.pagination.domain.InteractorImpl
import ru.vladikadiroff.pagination.presentation.models.PhotosViewAction
import ru.vladikadiroff.pagination.presentation.models.PhotosViewEvent
import ru.vladikadiroff.pagination.presentation.models.PhotosViewState
import ru.vladikadiroff.pagination.utils.abstracts.MviViewModel
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(private val interactor: InteractorImpl) :
    MviViewModel<PhotosViewState, PhotosViewAction, PhotosViewEvent>(PhotosViewState(loadingScreen = true)) {

    init {
        fetchPhotos()
    }

    private fun reduce(event: PhotosViewEvent) {
        viewState = when (event) {
            is PhotosViewEvent.Refresh -> {
                if (viewState.loadingScreen) {
                    viewAction = PhotosViewAction.StopSwipeRefresh
                    return
                }
                viewAction = PhotosViewAction.RefreshList
                viewState.copy(refresh = true)
            }
            is PhotosViewEvent.PagingLoadState -> {
                checkOnErrorAndShow(event.state)
                when (event.state.refresh) {
                    is LoadState.Loading ->
                        if (viewState.errorScreen) viewState.copy(
                            refresh = false,
                            errorScreen = false,
                            loadingScreen = true
                        ) else return
                    is LoadState.Error ->
                        if (viewState.loadingScreen) viewState.copy(
                            refresh = false,
                            errorScreen = true,
                            loadingScreen = false
                        ) else return
                    is LoadState.NotLoading ->
                        if (viewState.loadingScreen && event.state.prepend.endOfPaginationReached || viewState.refresh) {
                            viewAction = PhotosViewAction.ScrollListToStartPosition
                            viewState.copy(
                                refresh = false,
                                errorScreen = false,
                                loadingScreen = false
                            )
                        } else return
                }
            }
            else -> viewState.copy()
        }
    }

    private fun fetchPhotos() {
        interactor.getPhotos()
            .cachedIn(viewModelScope)
            .onEach { pager -> viewState = viewState.copy(pager = pager) }
            .launchIn(viewModelScope)
    }

    override fun obtainEvent(event: PhotosViewEvent) {
        when (event) {
            is PhotosViewEvent.ItemPhotoClick ->
                viewAction = PhotosViewAction.NavigateToPhotoFullScreen(
                    event.model,
                    event.transitionExtras
                )
            is PhotosViewEvent.ItemShareClick ->
                viewAction = PhotosViewAction.SharePhoto(event.model.photoThumbnail)
            is PhotosViewEvent.ItemInfoClick ->
                viewAction = PhotosViewAction.ShowPhotoInfo(event.model)
            else -> reduce(event)
        }
    }

    private fun checkOnErrorAndShow(state: CombinedLoadStates) {
        if (state.refresh !is LoadState.Error && state.append !is LoadState.Error) return
        val errorState = state.refresh as? LoadState.Error
            ?: state.append as? LoadState.Error
        errorState?.error?.localizedMessage?.let {
            viewAction = PhotosViewAction.ShowToast(it)
        }
    }

}