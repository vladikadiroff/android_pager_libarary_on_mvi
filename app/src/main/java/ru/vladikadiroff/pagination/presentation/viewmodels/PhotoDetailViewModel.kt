package ru.vladikadiroff.pagination.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.vladikadiroff.pagination.domain.InteractorImpl
import ru.vladikadiroff.pagination.domain.models.InteractorLoadState
import ru.vladikadiroff.pagination.presentation.models.PhotoInfoViewAction
import ru.vladikadiroff.pagination.presentation.models.PhotoInfoViewEvent
import ru.vladikadiroff.pagination.presentation.models.PhotoInfoViewState
import ru.vladikadiroff.pagination.utils.abstracts.MviViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(private val interactor: InteractorImpl) :
    MviViewModel<PhotoInfoViewState, PhotoInfoViewAction, PhotoInfoViewEvent>
        (PhotoInfoViewState(loadingScreen = true)) {

    private fun fetchInfo(id: String) {
        interactor.getPhotoInfo(id).onEach { status ->
            viewState = when (status) {
                is InteractorLoadState.Loading -> PhotoInfoViewState(loadingScreen = true)
                is InteractorLoadState.Content -> PhotoInfoViewState(infoScreen = status.content)
                is InteractorLoadState.Error -> {
                    viewAction = PhotoInfoViewAction.ShowToast(status.error)
                    PhotoInfoViewState(errorScreen = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun obtainEvent(event: PhotoInfoViewEvent) {
        when (event) {
            is PhotoInfoViewEvent.LoadContent -> fetchInfo(event.id)
        }
    }

}