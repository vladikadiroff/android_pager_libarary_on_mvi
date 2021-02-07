package ru.vladikadiroff.pagination.data

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.vladikadiroff.pagination.data.models.PhotoInfoRequestModel
import ru.vladikadiroff.pagination.domain.models.PhotoModel

interface Repository {

    fun getPhotos(): Flow<PagingData<PhotoModel>>

    suspend fun getPhotoInfo(id: String): PhotoInfoRequestModel

}