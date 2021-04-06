package ru.vladikadiroff.pagination.data

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.vladikadiroff.pagination.domain.models.PhotoInfoModel
import ru.vladikadiroff.pagination.domain.models.PhotoModel
import ru.vladikadiroff.pagination.ui.adapters.models.PhotosAdapterModel

interface Repository {

    fun getPhotos(): Flow<PagingData<PhotosAdapterModel>>

    suspend fun getPhotoInfo(id: String): PhotoInfoModel

}