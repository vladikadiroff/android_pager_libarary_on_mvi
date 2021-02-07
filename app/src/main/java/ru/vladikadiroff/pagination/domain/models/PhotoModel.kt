package ru.vladikadiroff.pagination.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoModel(
    val id: String,
    val ratio: Float,
    val likes: String,
    val photoThumbnail: String,
    val photoLarge: String,
    val photoDownload: String,
    val userName: String,
    val userAccount: String,
    val userProfileImage: String,
    var likeUser: Boolean = false
) : Parcelable