package ru.vladikadiroff.pagination.ui.dialogs

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ru.vladikadiroff.pagination.databinding.DialogPhotoInfoBinding
import ru.vladikadiroff.pagination.domain.models.PhotoInfoModel
import ru.vladikadiroff.pagination.presentation.models.PhotoInfoViewAction
import ru.vladikadiroff.pagination.presentation.models.PhotoInfoViewEvent
import ru.vladikadiroff.pagination.presentation.models.PhotoInfoViewState
import ru.vladikadiroff.pagination.presentation.viewmodels.PhotoDetailViewModel
import ru.vladikadiroff.pagination.utils.abstracts.MviBottomSheetDialog
import ru.vladikadiroff.pagination.utils.extensions.setVisibleWithFadeAnimation
import ru.vladikadiroff.pagination.utils.extensions.showToast
import ru.vladikadiroff.pagination.utils.extensions.withGlide

@AndroidEntryPoint
class PhotoInfoBottomDialog : MviBottomSheetDialog<DialogPhotoInfoBinding, PhotoInfoViewState,
        PhotoInfoViewAction, PhotoInfoViewEvent, PhotoDetailViewModel>() {

    private val args: PhotoInfoBottomDialogArgs by navArgs()
    override val viewModel: PhotoDetailViewModel by viewModels()
    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) ->
    DialogPhotoInfoBinding = DialogPhotoInfoBinding::inflate

    override fun initDialog()  = with(binding){
        postEvent(PhotoInfoViewEvent.LoadContent(args.model.id))
        photoBackground.withGlide(args.model.photoThumbnail)
        photoBackground.colorFilter =
            ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })
        retryButton.setOnClickListener { postEvent(PhotoInfoViewEvent.LoadContent(args.model.id)) }
    }

    override fun render(state: PhotoInfoViewState): Unit = with(binding){
        loadingScreen.isVisible = state.loadingScreen
        errorScreen.isVisible = state.errorScreen
        if (state.infoScreen == null) infoScreen.isInvisible = true
        else infoScreen.setVisibleWithFadeAnimation(true)
        state.infoScreen?.let { setContent(it) }
    }

    override fun renderAction(action: PhotoInfoViewAction) = when (action) {
        is PhotoInfoViewAction.ShowToast -> showToast(action.msg)
    }

    private fun setContent(model: PhotoInfoModel) = with(binding) {
        textPublished.text = model.published
        textViewsCount.text = model.views
        textDownloadsCount.text = model.downloads
        textCamera.text = model.camera.camera
        textCameraModel.text = model.camera.model
        textAperture.text = model.camera.aperture
        textFocalLength.text = model.camera.focal
        textISO.text = model.camera.iso
        textShutter.text = model.camera.shutter
        textDimensions.text = model.dimensions
        model.camera.apertureAbbreviature?.let { textApertureAbbreviature.setText(it) }
    }

}