package ru.vladikadiroff.pagination.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

interface PhotoModel {
    val id: String
    val viewType: Int

    companion object {
        const val HEADER = 0
        const val IMAGE = 1
        const val FOOTER = 2
    }

}

data class PhotoHeaderModel(
    val userName: String,
    val userAccount: String,
    val userProfileImage: String,
    override val id: String,
    override val viewType: Int = PhotoModel.HEADER
) : PhotoModel

@Parcelize
data class PhotoFooterModel(
    val likes: String,
    val image: String,
    val imageDownload: String,
    var likeUser: Boolean = false,
    override val id: String,
    override val viewType: Int = PhotoModel.FOOTER
) : PhotoModel, Parcelable

@Parcelize
data class PhotoImageModel(
    val image: String,
    val ratio: Float,
    override val id: String,
    override val viewType: Int = PhotoModel.IMAGE
) : PhotoModel, Parcelable