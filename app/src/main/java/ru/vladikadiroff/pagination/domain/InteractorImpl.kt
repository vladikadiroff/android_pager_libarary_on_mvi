package ru.vladikadiroff.pagination.domain

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.vladikadiroff.pagination.data.RepositoryImpl
import ru.vladikadiroff.pagination.di.annotations.DispatcherIO
import ru.vladikadiroff.pagination.domain.mappers.PhotoInfoModelConverter
import ru.vladikadiroff.pagination.domain.models.InteractorLoadState
import ru.vladikadiroff.pagination.domain.models.PhotoInfoModel
import ru.vladikadiroff.pagination.domain.models.PhotoModel
import ru.vladikadiroff.pagination.utils.abstracts.CoroutineInteractor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InteractorImpl @Inject constructor(
    private val repository: RepositoryImpl,
    private val photoConverter: PhotoInfoModelConverter,
    @DispatcherIO ioDispatcher: CoroutineDispatcher
) : CoroutineInteractor(ioDispatcher), Interactor {

    override fun getPhotos(): Flow<PagingData<PhotoModel>> = repository.getPhotos()

    override fun getPhotoInfo(id: String): StateFlow<InteractorLoadState<PhotoInfoModel>> {
        val state =
            MutableStateFlow<InteractorLoadState<PhotoInfoModel>>(InteractorLoadState.Loading())
        launchBackground {
            try {
                val content = photoConverter.map(repository.getPhotoInfo(id))
                state.value = InteractorLoadState.Content(content)
            } catch (e: Exception) {
                state.value = InteractorLoadState.Error(e.toString())
            }
        }
        return state
    }

}