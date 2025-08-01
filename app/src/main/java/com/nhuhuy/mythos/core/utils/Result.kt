package com.nhuhuy.mythos.core.utils

sealed class Result<out T> {
    data class Success<out T>(val data: T?) : Result<T>()
    data class Failure(val exception: Exception) : Result<Nothing>()
}

fun <T> Result<T>.getDataOrNull(): T? {
    return when (this) {
        is Result.Success -> this.data
        is Result.Failure -> null
    }
}

