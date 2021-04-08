package ru.vladikadiroff.pagination.presentation.models

data class PhotoInfoModel(
    val published: String,
    val downloads: String,
    val dimensions: String,
    val views: String,
    val camera: CameraInfoModel
) {
    data class CameraInfoModel(
        val camera: String,
        val model: String,
        val shutter: String,
        val aperture: String,
        val apertureAbbreviature: Int?,
        val focal: String,
        val iso: String
    )
}