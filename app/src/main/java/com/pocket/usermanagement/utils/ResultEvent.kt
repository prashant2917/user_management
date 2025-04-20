package com.pocket.usermanagement.utils

sealed class ResultEvent<out R> {

    data class Success<out T>(val data: T) : ResultEvent<T>()
    data class Error(val exception: Exception) : ResultEvent<Nothing>()
    object Loading : ResultEvent<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}