package ru.vladikadiroff.pagination.utils.abstracts

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class CoroutineInteractor (private val ioDispatcher: CoroutineDispatcher) {

    private var interactorJob = SupervisorJob()
    private val interactorScope = CoroutineScope(Dispatchers.Main + interactorJob)

    fun <P> CoroutineInteractor.launchBackground(event: suspend CoroutineScope.() -> P) {
        launchCoroutine(event, interactorScope, ioDispatcher)
    }

    private inline fun <P> launchCoroutine(
        crossinline event: suspend CoroutineScope.() -> P,
        coroutineScope: CoroutineScope,
        coroutineContext: CoroutineContext
    ) {
        coroutineScope.launch {
            withContext(coroutineContext) {
                event.invoke(this)
            }
        }
    }

    fun stop() {
        interactorJob.cancelChildren()
    }

}