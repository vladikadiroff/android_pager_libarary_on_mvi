package ru.vladikadiroff.pagination.utils.abstracts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.vladikadiroff.pagination.utils.extensions.toSingleAction

abstract class MviViewModel<VS, VA, VE> : ViewModel() {

    // For check attach to new instance
    private var isNewInstanceAttached = false

    private val viewStateLiveData = MutableLiveData<VS>()
    val obtainViewState: LiveData<VS> = viewStateLiveData

    private var _viewState: VS? = null
    protected var viewState: VS
        get() = _viewState
            ?: throw UninitializedPropertyAccessException("\"viewState\" was queried before being initialized")
        set(value) {
            _viewState = value
            viewStateLiveData.postValue(value)
        }

    private val viewActionLiveData = MutableLiveData<VA>()
    val obtainViewAction = viewActionLiveData.toSingleAction()

    private var _viewAction: VA? = null
    protected var viewAction: VA
        get() = _viewAction
            ?: throw UninitializedPropertyAccessException("\"viewAction\" was queried before being initialized")
        set(value) {
            _viewAction = value
            viewActionLiveData.postValue(value)
        }

    // update isNewInstanceAttached on attach to new instance
    fun attachToNewInstance() {
        isNewInstanceAttached = true
    }

    // check for new instance attached and update to old instance
    protected fun checkOnAttachToNewInstanceAndUpdate(): Boolean {
        val newInstance = isNewInstanceAttached
        isNewInstanceAttached = false
        return newInstance
    }

    abstract fun obtainEvent(event: VE)

}