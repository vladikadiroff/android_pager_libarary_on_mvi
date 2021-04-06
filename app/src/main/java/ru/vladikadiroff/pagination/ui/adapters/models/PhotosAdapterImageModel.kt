package ru.vladikadiroff.pagination.ui.adapters.models

import ru.vladikadiroff.pagination.ui.adapters.models.PhotosAdapterModel.*
import ru.vladikadiroff.pagination.utils.helpers.SingleEvent

data class PhotosAdapterImageModel(
    val id: String,
    val image: String,
    val ratio: Float,
    var likeUser: SingleEvent<Boolean> = SingleEvent(false)
) : PhotosAdapterModel {
    override val viewType = PhotosAdapterViewTypes.Image
}