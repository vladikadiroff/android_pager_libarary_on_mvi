package ru.vladikadiroff.pagination.ui.adapters.models

import ru.vladikadiroff.pagination.ui.adapters.models.PhotosAdapterModel.*

data class PhotosAdapterFooterModel(
    val id: String,
    val likes: String,
    val photoDownload: String,
    var likeUser: Boolean = false
) : PhotosAdapterModel {
    override val viewType = PhotosAdapterViewTypes.Footer
}