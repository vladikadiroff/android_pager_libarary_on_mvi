package ru.vladikadiroff.pagination.ui.adapters.viewholders

import android.view.ViewGroup
import ru.vladikadiroff.pagination.ui.adapters.models.PhotosAdapterModel.PhotosAdapterViewTypes

class PhotosViewHoldersFabric {
    companion object {
        fun create(parent: ViewGroup, viewType: Int) = when (viewType) {
            PhotosAdapterViewTypes.Header.value -> PhotoHeaderViewHolder(parent)
            PhotosAdapterViewTypes.Image.value -> PhotoImageViewHolder(parent)
            PhotosAdapterViewTypes.Footer.value -> PhotoFooterViewHolder(parent)
            else -> throw IllegalArgumentException("ViewHolder with type $viewType not found")
        }
    }
}