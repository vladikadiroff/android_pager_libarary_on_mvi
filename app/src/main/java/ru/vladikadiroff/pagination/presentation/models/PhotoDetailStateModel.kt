package ru.vladikadiroff.pagination.presentation.models

data class PhotoInfoViewState(
    val loadingScreen: Boolean = false,
    val errorScreen: Boolean = false,
    val infoScreen: PhotoInfoModel? = null
)

sealed class PhotoInfoViewEvent {
    class LoadContent(val id: String) : PhotoInfoViewEvent()
}

sealed class PhotoInfoViewAction {
    class ShowToast(val msg: String): PhotoInfoViewAction()
}