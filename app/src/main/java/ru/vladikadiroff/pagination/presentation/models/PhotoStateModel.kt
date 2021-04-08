package ru.vladikadiroff.pagination.presentation.models

import androidx.navigation.fragment.FragmentNavigator
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData

data class PhotosViewState(
    val refresh: Boolean = false,
    val loadingScreen: Boolean = false,
    val errorScreen: Boolean = false,
    val pager: PagingData<PhotoModel>? = null
)

sealed class PhotosViewEvent {
    object Refresh : PhotosViewEvent()
    class ItemPhotoClick(
        val model: PhotoImageModel,
        val transitionExtras: FragmentNavigator.Extras
    ) : PhotosViewEvent()

    class ItemInfoClick(val model: PhotoFooterModel) : PhotosViewEvent()
    class ItemShareClick(val model: PhotoFooterModel) : PhotosViewEvent()
    class PagingLoadState(val state: CombinedLoadStates) : PhotosViewEvent()
}

sealed class PhotosViewAction {
    object RefreshList : PhotosViewAction()
    class ShowPhotoInfo(val model: PhotoFooterModel) : PhotosViewAction()
    class SharePhoto(val url: String) : PhotosViewAction()
    class NavigateToPhotoFullScreen(
        val model: PhotoImageModel,
        val extras: FragmentNavigator.Extras
    ) : PhotosViewAction()

    object StopSwipeRefresh : PhotosViewAction()
    object ScrollListToStartPosition : PhotosViewAction()
    class ShowToast(val msg: String) : PhotosViewAction()
}