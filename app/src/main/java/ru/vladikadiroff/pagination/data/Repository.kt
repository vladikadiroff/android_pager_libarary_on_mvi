package ru.vladikadiroff.pagination.data

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.vladikadiroff.pagination.presentation.models.PhotoInfoModel
import ru.vladikadiroff.pagination.presentation.models.PhotoModel

interface Repository {

    fun getPhotos(): Flow<PagingData<PhotoModel>>

    suspend fun getPhotoInfo(id: String): PhotoInfoModel

}