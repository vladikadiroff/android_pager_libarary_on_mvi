package ru.vladikadiroff.pagination.ui.adapters.viewholders

import android.view.ViewGroup
import ru.vladikadiroff.pagination.R
import ru.vladikadiroff.pagination.databinding.ItemImageBinding
import ru.vladikadiroff.pagination.ui.adapters.models.PhotosAdapterImageModel
import ru.vladikadiroff.pagination.utils.abstracts.BaseViewHolder
import ru.vladikadiroff.pagination.utils.extensions.startVectorAnimation
import ru.vladikadiroff.pagination.utils.extensions.withGlide

class PhotoImageViewHolder(parent: ViewGroup) : BaseViewHolder(R.layout.item_image, parent) {

    private val binding =  ItemImageBinding.bind(itemView)
    private val image = binding.pictureImage
    private val likeImage = binding.likeImage

    fun bind(model: PhotosAdapterImageModel) {
        image.withGlide(model.image)
        image.aspectRatio = model.ratio
        image.transitionName = model.id
        image.setOnClickListener {  }
        model.likeUser.getContentIfNotHandled()?.let { like -> if (like) runLikeAnimation() }
    }

    fun runLikeAnimation() {
        likeImage.alpha = 0.70f
        likeImage.drawable.startVectorAnimation()
    }

}