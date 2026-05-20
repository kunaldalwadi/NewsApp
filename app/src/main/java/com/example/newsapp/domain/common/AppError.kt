package com.example.newsapp.domain.common

sealed interface AppError {
    object NetworkUnavailable : AppError
    object OperationCancelled : AppError
    object Timeout : AppError
    data class Http(val code: Int) : AppError
    object Unauthorized : AppError
    object Forbidden : AppError
    object NotFound : AppError
    object Server : AppError
    data class Unknown(val cause: Throwable? = null) : AppError
}