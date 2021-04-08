package ru.vladikadiroff.pagination.ui.adapters.viewholders

import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import ru.vladikadiroff.pagination.R
import ru.vladikadiroff.pagination.databinding.ItemFooterBinding
import ru.vladikadiroff.pagination.databinding.ItemHeaderBinding
import ru.vladikadiroff.pagination.databinding.ItemImageBinding
import ru.vladikadiroff.pagination.presentation.models.*
import ru.vladikadiroff.pagination.presentation.models.PhotosViewEvent.*
import ru.vladikadiroff.pagination.utils.abstracts.BaseViewHolder
import ru.vladikadiroff.pagination.utils.extensions.startVectorAnimation
import ru.vladikadiroff.pagination.utils.extensions.truncateNumber
import ru.vladikadiroff.pagination.utils.extensions.withGlide

class PhotosViewHoldersFactory {

    companion object {

        fun create(parent: ViewGroup, viewType: Int, listener: (PhotosViewEvent) -> Unit) =
            when (viewType) {
                PhotoModel.HEADER -> PhotoHeaderViewHolder(parent)
                PhotoModel.IMAGE -> PhotoImageViewHolder(parent, listener)
                PhotoModel.FOOTER -> PhotoFooterViewHolder(parent, listener)
                else -> throw IllegalArgumentException("ViewHolder with type $viewType not found")
            }

    }

    class PhotoHeaderViewHolder(parent: ViewGroup) : BaseViewHolder(R.layout.item_header, parent) {

        private val binding = ItemHeaderBinding.bind(itemView)

        fun bind(model: PhotoHeaderModel) = with(binding) {
            userName.text = model.userName
            userProfile.text = model.userAccount
            userImage.withGlide(model.userProfileImage)
        }

    }

    class PhotoImageViewHolder(parent: ViewGroup, private val listener: (PhotosViewEvent) -> Unit) :
        BaseViewHolder(R.layout.item_image, parent) {

        private val binding = ItemImageBinding.bind(itemView)
        private val image = binding.pictureImage
        private val likeImage = binding.likeImage

        fun bind(model: PhotoImageModel) {
            image.withGlide(model.image)
            image.aspectRatio = model.ratio
            image.transitionName = model.id
            image.setOnClickListener {
                listener(ItemPhotoClick(model, FragmentNavigatorExtras(image to model.id)))
            }
        }

        fun startLikeAnimation() {
            likeImage.alpha = 0.70f
            likeImage.drawable.startVectorAnimation()
        }

    }

    class PhotoFooterViewHolder(parent: ViewGroup, private val listener: (PhotosViewEvent) -> Unit) :
        BaseViewHolder(R.layout.item_footer, parent) {

        private val binding = ItemFooterBinding.bind(itemView)
        private val container = parent as RecyclerView

        fun bind(model: PhotoFooterModel) =  with(binding) {
            likesCount.text = countLikes(model)
            likeButton.setOnCheckedChangeListener(null)
            likeButton.isChecked = model.likeUser
            shareButton.setOnClickListener { listener(ItemShareClick(model)) }
            infoButton.setOnClickListener { listener(ItemInfoClick(model)) }
            likeButton.setOnCheckedChangeListener { _, checked ->
                model.likeUser = checked
                likesCount.text = countLikes(model)
                if (checked) startLikeAnimation()
            }
        }

        private fun countLikes(model: PhotoFooterModel): String {
            val likes = model.likes.toInt() + if (model.likeUser) 1 else 0
            return likes.truncateNumber()
        }

        private fun startLikeAnimation() {
            val holder = container.findViewHolderForAdapterPosition(absoluteAdapterPosition - 1)
            (holder as PhotoImageViewHolder)?.startLikeAnimation()
        }

    }

}