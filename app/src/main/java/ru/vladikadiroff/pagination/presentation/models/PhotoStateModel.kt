package ru.vladikadiroff.pagination.presentation.models

import androidx.navigation.fragment.FragmentNavigator
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import ru.vladikadiroff.pagination.domain.models.PhotoModel
import ru.vladikadiroff.pagination.ui.adapters.models.PhotosAdapterModel

data class PhotosViewState(
    val refresh: Boolean = false,
    val loadingScreen: Boolean = false,
    val errorScreen: Boolean = false,
    val pager: PagingData<PhotosAdapterModel>? = null
)

sealed class PhotosViewEvent {
    object Refresh : PhotosViewEvent()
    class ItemPhotoClick(
        val model: PhotoModel,
        val transitionExtras: FragmentNavigator.Extras
    ) : PhotosViewEvent()

    class ItemInfoClick(val model: PhotoModel) : PhotosViewEvent()
    class ItemShareClick(val model: PhotoModel) : PhotosViewEvent()
    class PagingLoadState(val state: CombinedLoadStates) : PhotosViewEvent()
}

sealed class PhotosViewAction {
    object RefreshList : PhotosViewAction()
    class ShowPhotoInfo(val model: PhotoModel) : PhotosViewAction()
    class SharePhoto(val url: String) : PhotosViewAction()
    class NavigateToPhotoFullScreen(
        val model: PhotoModel,
        val extras: FragmentNavigator.Extras
    ) : PhotosViewAction()

    object StopSwipeRefresh : PhotosViewAction()
    object ScrollListToStartPosition : PhotosViewAction()
    class ShowToast(val msg: String) : PhotosViewAction()
}