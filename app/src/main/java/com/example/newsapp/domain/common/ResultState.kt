package com.example.newsapp.domain.common

sealed interface ResultState<out T> {
    object Loading : ResultState<Nothing>
    data class Success<T>(val data: T) : ResultState<T>
    data class Error(val error: AppError) : ResultState<Nothing>
}