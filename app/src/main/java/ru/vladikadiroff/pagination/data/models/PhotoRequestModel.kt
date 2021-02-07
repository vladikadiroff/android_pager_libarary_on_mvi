package ru.vladikadiroff.pagination.data.models

import com.squareup.moshi.Json

data class PhotoRequestModel(
    @Json(name = "id")
    val id: String,
    @Json(name = "width")
    val width: String,
    @Json(name = "height")
    val height: String,
    @Json(name = "likes")
    val likes: String,
    @Json(name = "urls")
    val photoUrls: PhotoUrlsRequestModel,
    @Json(name = "user")
    val user: PhotoUserRequestModel,
    @Json(name = "links")
    val downloadUrl: PhotoDownloadLinks
)

data class PhotoUrlsRequestModel(
    @Json(name = "raw")
    val raw: String,
    @Json(name = "full")
    val full: String,
    @Json(name = "regular")
    val regular: String,
    @Json(name = "small")
    val small: String,
    @Json(name = "thumb")
    val thumb: String
)

data class PhotoUserRequestModel(
    @Json(name = "name")
    val name: String,
    @Json(name = "username")
    val username: String,
    @Json(name = "instagram_username")
    val instagram: String?,
    @Json(name = "twitter_username")
    val twitter: String?,
    @Json(name = "profile_image")
    val avatar: PhotoUserAvatarRequestModel
)

data class PhotoUserAvatarRequestModel(
    @Json(name = "small")
    val small: String,
    @Json(name = "medium")
    val medium: String,
    @Json(name = "large")
    val large: String
)

data class PhotoDownloadLinks(
    @Json(name = "download")
    val urlDownload: String?,
    @Json(name = "download_location")
    val urlDownloadLocation: String?
)
