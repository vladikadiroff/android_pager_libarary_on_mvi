package ru.vladikadiroff.pagination.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.vladikadiroff.pagination.domain.models.InteractorLoadState
import ru.vladikadiroff.pagination.domain.models.PhotoInfoModel
import ru.vladikadiroff.pagination.domain.models.PhotoModel

interface Interactor {

    fun getPhotos(): Flow<PagingData<PhotoModel>>

    fun getPhotoInfo(id: String): StateFlow<InteractorLoadState<PhotoInfoModel>>

}