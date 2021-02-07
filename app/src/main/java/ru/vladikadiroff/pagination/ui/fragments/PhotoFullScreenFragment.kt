package ru.vladikadiroff.pagination.ui.fragments

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.chrisbanes.photoview.OnSingleFlingListener
import com.github.chrisbanes.photoview.PhotoViewAttacher
import com.google.android.material.transition.MaterialContainerTransform
import ru.vladikadiroff.pagination.R
import ru.vladikadiroff.pagination.databinding.FragmentPhotoFullscreenBinding
import ru.vladikadiroff.pagination.ui.activities.MainActivity
import ru.vladikadiroff.pagination.utils.abstracts.ViewBindingFragment
import ru.vladikadiroff.pagination.utils.extensions.*
import kotlin.math.abs

class PhotoFullScreenFragment : ViewBindingFragment<FragmentPhotoFullscreenBinding>() {

    private var isShowBar = false
    private val args: PhotoFullScreenFragmentArgs by navArgs()
    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) ->
    FragmentPhotoFullscreenBinding = FragmentPhotoFullscreenBinding::inflate

    override fun initFragment() {
        showOrHideBar()
        postponeEnterTransition()
        initTransitionAnimation()
        initViews()
    }

    private fun initViews() {
        binding.transitionView.aspectRatio = args.model.ratio
        binding.transitionView.transitionName = args.model.id
        binding.pictureImage.withGlide(args.model.photoThumbnail, onLoad = { startPostponedEnterTransition() })
        binding.navigateImage.setOnClickListener { findNavController().navigateUp() }
        binding.upBar.doOnApplyWindowInsets { bar, insets, _ ->
            bar.updatePadding(top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top)
            insets
        }
        val attacher = PhotoViewAttacher(binding.pictureImage)
        attacher.setOnSingleFlingListener(onSingleFlingListener())
        attacher.setOnViewTapListener { _, _, _ -> showOrHideBar() }
    }

    private fun onSingleFlingListener() = OnSingleFlingListener { e1, e2, _, velocityY ->
        val swipeMinDistance = 120
        val swipeThresholdVelocity = 200
        if (e1.y - e2.y > swipeMinDistance && abs(velocityY) > swipeThresholdVelocity)
            findNavController().navigateUp() // top to bottom
        else if (e2.y - e1.y > swipeMinDistance && abs(velocityY) > swipeThresholdVelocity)
            findNavController().navigateUp() // bottom to top
        true
    }

    private fun initTransitionAnimation() {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.navHost
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
            doOnTransitionAnimationEnd { binding.pictureImage.isVisible = true }
        }
    }

    private fun showOrHideBar() {
        isShowBar = !isShowBar
        binding.upBar.setVisibleWithFadeAnimation(isShowBar)
        if (!isShowBar) hideSystemUI()
        else showSystemUI()
    }

    private fun makeTransparentStatusBar(transparent: Boolean = true) {
        val color = if (!transparent) R.color.white else R.color.background_opacity_system_ui
        setLightStatusBar(!transparent)
        (requireActivity() as MainActivity).showToolbar(!transparent)
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), color)
    }

    override fun onResume() {
        super.onResume()
        makeTransparentStatusBar()
    }

    override fun onStop() {
        super.onStop()
        makeTransparentStatusBar(false)
        showSystemUI()
    }

}