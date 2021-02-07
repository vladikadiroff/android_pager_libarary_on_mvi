package ru.vladikadiroff.pagination.presentation.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.vladikadiroff.pagination.domain.InteractorImpl
import ru.vladikadiroff.pagination.domain.models.InteractorLoadState
import ru.vladikadiroff.pagination.presentation.models.PhotoInfoViewAction
import ru.vladikadiroff.pagination.presentation.models.PhotoInfoViewEvent
import ru.vladikadiroff.pagination.presentation.models.PhotoInfoViewState
import ru.vladikadiroff.pagination.utils.abstracts.MviViewModel

class PhotoDetailViewModel @ViewModelInject constructor(private val interactor: InteractorImpl) :
    MviViewModel<PhotoInfoViewState, PhotoInfoViewAction, PhotoInfoViewEvent>() {

    private var id: String? = null

    private fun load(id: String) {
        if (this.id != id) getContent(id)
        this.id = id
    }

    private fun getContent(id: String) {
        viewModelScope.launch {
            interactor.getPhotoInfo(id).collect { status ->
                viewState = when (status) {
                    is InteractorLoadState.Loading -> PhotoInfoViewState(loadingScreen = true)
                    is InteractorLoadState.Content -> PhotoInfoViewState(infoScreen = status.content)
                    is InteractorLoadState.Error -> {
                        viewAction = PhotoInfoViewAction.ShowToast(status.error)
                        PhotoInfoViewState(errorScreen = true)
                    }
                }
            }
        }
    }

    override fun obtainEvent(event: PhotoInfoViewEvent) {
        when (event) {
            is PhotoInfoViewEvent.Retry -> id?.let { getContent(it) }
            is PhotoInfoViewEvent.LoadContent -> load(event.id)
        }
    }


}