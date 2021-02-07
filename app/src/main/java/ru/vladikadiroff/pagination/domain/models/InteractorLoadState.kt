package ru.vladikadiroff.pagination.domain.models

sealed class InteractorLoadState<T> {
    class Loading<T> : InteractorLoadState<T>()
    data class Content<T>(val content: T) : InteractorLoadState<T>()
    data class Error<T>(val error: String) : InteractorLoadState<T>()
}