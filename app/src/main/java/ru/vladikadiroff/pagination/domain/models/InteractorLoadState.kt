package ru.vladikadiroff.pagination.domain.models

sealed class InteractorLoadState<out T> {
    object Loading : InteractorLoadState<Nothing>()
    data class Content<T>(val content: T) : InteractorLoadState<T>()
    data class Error(val error: String) : InteractorLoadState<Nothing>()
}