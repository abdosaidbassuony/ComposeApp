package com.compose_app.core.utils

sealed class Results<out T> {
    data class Success<out T>(val data: T) : Results<T>()
    data class Failure(val exception: Throwable) : Results<Nothing>()
    data object Loading : Results<Nothing>()
}