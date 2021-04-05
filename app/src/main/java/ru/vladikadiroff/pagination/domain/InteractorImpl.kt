package ru.vladikadiroff.pagination.domain

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ru.vladikadiroff.pagination.data.RepositoryImpl
import ru.vladikadiroff.pagination.di.annotations.DispatcherIO
import ru.vladikadiroff.pagination.domain.mappers.PhotoInfoModelConverter
import ru.vladikadiroff.pagination.domain.models.InteractorLoadState
import ru.vladikadiroff.pagination.domain.models.PhotoModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InteractorImpl @Inject constructor(
    private val repository: RepositoryImpl,
    private val photoConverter: PhotoInfoModelConverter,
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher
) : Interactor {

    override fun getPhotos(): Flow<PagingData<PhotoModel>> = repository.getPhotos()

    override fun getPhotoInfo(id: String) = flow {
        emit(InteractorLoadState.Loading)
        kotlin.runCatching {
            withContext(ioDispatcher) { photoConverter.map(repository.getPhotoInfo(id)) }
        }
            .onSuccess { content -> emit(InteractorLoadState.Content(content)) }
            .onFailure { error -> error.localizedMessage?.let { emit(InteractorLoadState.Error(it)) } }
    }

}