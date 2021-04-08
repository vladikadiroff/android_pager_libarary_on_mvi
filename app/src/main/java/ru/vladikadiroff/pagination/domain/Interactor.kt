package ru.vladikadiroff.pagination.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.vladikadiroff.pagination.domain.models.InteractorLoadState
import ru.vladikadiroff.pagination.presentation.models.PhotoInfoModel
import ru.vladikadiroff.pagination.presentation.models.PhotoModel

interface Interactor {

    fun getPhotos(): Flow<PagingData<PhotoModel>>

    fun getPhotoInfo(id: String): Flow<InteractorLoadState<PhotoInfoModel>>

}