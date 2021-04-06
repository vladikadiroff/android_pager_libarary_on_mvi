package ru.vladikadiroff.pagination.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.vladikadiroff.pagination.domain.models.InteractorLoadState
import ru.vladikadiroff.pagination.domain.models.PhotoInfoModel
import ru.vladikadiroff.pagination.domain.models.PhotoModel
import ru.vladikadiroff.pagination.ui.adapters.models.PhotosAdapterModel

interface Interactor {

    fun getPhotos(): Flow<PagingData<PhotosAdapterModel>>

    fun getPhotoInfo(id: String): Flow<InteractorLoadState<PhotoInfoModel>>

}