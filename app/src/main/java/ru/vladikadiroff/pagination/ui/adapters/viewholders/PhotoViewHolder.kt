package ru.vladikadiroff.pagination.ui.adapters.viewholders

import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigatorExtras
import ru.vladikadiroff.pagination.R
import ru.vladikadiroff.pagination.databinding.ItemPhotoBinding
import ru.vladikadiroff.pagination.domain.models.PhotoModel
import ru.vladikadiroff.pagination.presentation.models.PhotosViewEvent
import ru.vladikadiroff.pagination.utils.abstracts.BaseViewHolder
import ru.vladikadiroff.pagination.utils.extensions.startVectorAnimation
import ru.vladikadiroff.pagination.utils.extensions.withGlide
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.ln
import kotlin.math.pow


class PhotoViewHolder(parent: ViewGroup, private val listener: ((PhotosViewEvent) -> Unit)?) :
    BaseViewHolder(R.layout.item_photo, parent) {

    private val binding = ItemPhotoBinding.bind(itemView)

    fun bind(model: PhotoModel) = with(binding) {
        initListeners(model)
        userName.text = model.userName
        userProfile.text = model.userAccount
        userImage.withGlide(model.userProfileImage)
        pictureImage.aspectRatio = model.ratio
        pictureImage.transitionName = model.id
        pictureImage.withGlide(model.photoThumbnail)
        likesCount.text = countLikes(model)
        likeButton.isChecked = model.likeUser
    }

    private fun initListeners(model: PhotoModel) = with(binding) {
        shareButton.setOnClickListener { listener?.invoke(PhotosViewEvent.ItemShareClick(model)) }
        infoButton.setOnClickListener { listener?.invoke(PhotosViewEvent.ItemInfoClick(model)) }
        pictureImage.setOnClickListener {
            listener?.invoke(PhotosViewEvent.ItemPhotoClick(model, FragmentNavigatorExtras(pictureImage to model.id)))}
        likeButton.setOnCheckedChangeListener { _, checked ->
            model.likeUser = checked
            likesCount.text = countLikes(model)
            if (checked) {
                likeImage.alpha = 0.70f
                likeImage.drawable.startVectorAnimation()
            }
        }
    }

    private fun countLikes(model: PhotoModel): String {
        val likes = model.likes.toInt() + if (model.likeUser) 1 else 0
        return truncateNumber(likes)
    }

    private fun truncateNumber(number: Int): String {
        return try {
            val number = number.toDouble()
            val suffixChars = "KMGTPE"
            val formatter = DecimalFormat("###.#").also { it.roundingMode = RoundingMode.DOWN }
            if (number < 1000.0) formatter.format(number)
            else {
                val exp = (ln(number) / ln(1000.0)).toInt()
                formatter.format(number / 1000.0.pow(exp.toDouble())) + suffixChars[exp - 1]
            }
        } catch (e: Exception){
            number.toString()
        }
    }

}