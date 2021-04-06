package ru.vladikadiroff.pagination.ui.adapters.viewholders

import android.view.ViewGroup
import ru.vladikadiroff.pagination.R
import ru.vladikadiroff.pagination.databinding.ItemHeaderBinding
import ru.vladikadiroff.pagination.ui.adapters.models.PhotosAdapterHeaderModel
import ru.vladikadiroff.pagination.utils.abstracts.BaseViewHolder
import ru.vladikadiroff.pagination.utils.extensions.withGlide

class PhotoHeaderViewHolder(parent: ViewGroup) : BaseViewHolder(R.layout.item_header, parent) {

    private val binding = ItemHeaderBinding.bind(itemView)

    fun bind(model: PhotosAdapterHeaderModel) = with(binding){
        userName.text = model.userName
        userProfile.text = model.userAccount
        userImage.withGlide(model.userProfileImage)
    }

}