package ru.vladikadiroff.pagination.data.models

import com.squareup.moshi.Json

data class PhotoInfoRequestModel(
    @Json(name = "width")
    val width: String,
    @Json(name = "height")
    val height: String,
    @Json(name = "promoted_at")
    val published: String?,
    @Json(name = "created_at")
    val created: String?,
    @Json(name = "updated_at")
    val updated: String?,
    @Json(name = "downloads")
    val downloads: String,
    @Json(name = "views")
    val views: String,
    @Json(name = "exif")
    val cameraInfo: CameraInfoRequestModel = CameraInfoRequestModel()
)

data class CameraInfoRequestModel(
    @Json(name = "make")
    val camera: String? = null,
    @Json(name = "model")
    val model: String? = null,
    @Json(name = "exposure_time")
    val shutter: String? = null,
    @Json(name = "aperture")
    val aperture: String? = null,
    @Json(name = "focal_length")
    val focal: String? = null,
    @Json(name = "iso")
    val iso: String? = null
)


