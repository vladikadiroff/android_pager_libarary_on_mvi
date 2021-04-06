package ru.vladikadiroff.pagination.ui.adapters.models

import ru.vladikadiroff.pagination.ui.adapters.models.PhotosAdapterModel.*

class PhotosAdapterHeaderModel(
    val id: String,
    val userName: String,
    val userAccount: String,
    val userProfileImage: String
) : PhotosAdapterModel {
    override val viewType = PhotosAdapterViewTypes.Header
}