package ru.vladikadiroff.pagination.ui.adapters.models

interface PhotosAdapterModel {

    val viewType: PhotosAdapterViewTypes

    enum class PhotosAdapterViewTypes(val value: Int) {
        Header(0),
        Image(1),
        Footer(2)
    }

}