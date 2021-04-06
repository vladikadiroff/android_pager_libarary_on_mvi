package ru.vladikadiroff.pagination.domain

import kotlinx.coroutines.flow.flow
import ru.vladikadiroff.pagination.data.RepositoryImpl
import ru.vladikadiroff.pagination.domain.models.InteractorLoadState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InteractorImpl @Inject constructor(private val repository: RepositoryImpl) : Interactor {

    override fun getPhotos() = repository.getPhotos()

    override fun getPhotoInfo(id: String) = flow {
        emit(InteractorLoadState.Loading)
        kotlin.runCatching { repository.getPhotoInfo(id) }
            .onSuccess { content -> emit(InteractorLoadState.Content(content)) }
            .onFailure { error -> emit(InteractorLoadState.Error(error.localizedMessage ?: error.toString())) }
    }

}