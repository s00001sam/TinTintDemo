package com.sam.tintintdemo.data

sealed class State<out T> {
    data object Loading : State<Nothing>()
    data class Success<out T>(val data: T) : State<T>()
    data class Error(val throwable: Throwable) : State<Nothing>()
}
