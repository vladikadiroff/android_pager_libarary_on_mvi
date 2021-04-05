package ru.vladikadiroff.pagination.utils.abstracts

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.vladikadiroff.pagination.utils.helpers.SingleEvent

abstract class MviViewModel<VS, VA, VE>(initialViewState: VS) : ViewModel() {

    private val viewStateFlow = MutableStateFlow(initialViewState)
    val obtainViewState = viewStateFlow.asStateFlow()

    private var _viewState = initialViewState
    protected var viewState: VS
        get() = _viewState
        set(value) {
            _viewState = value
            viewStateFlow.value = value
        }

    private val viewActionLiveData = MutableSharedFlow<SingleEvent<VA>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val obtainViewAction = viewActionLiveData.asSharedFlow()

    private var _viewAction: VA? = null
    protected var viewAction: VA
        get() = _viewAction
            ?: throw UninitializedPropertyAccessException("\"viewAction\" was queried before being initialized")
        set(value) {
            _viewAction = value
            viewActionLiveData.tryEmit(SingleEvent(value))
        }

    abstract fun obtainEvent(event: VE)

}