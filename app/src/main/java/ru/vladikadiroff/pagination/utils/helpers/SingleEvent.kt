package ru.vladikadiroff.pagination.utils.helpers

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class SingleEvent<out T>(private val content: T) {

    var contentHasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (contentHasBeenHandled) null
        else {
            contentHasBeenHandled = true
            content
        }
    }

    fun peekContent() = content

}

inline fun <T> Flow<SingleEvent<T>>.onSingleEvent(crossinline block: (T) -> Unit) =
    onEach { event -> event.getContentIfNotHandled()?.let { content -> block.invoke(content) } }
