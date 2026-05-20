package com.example.newsapp.ui.uimodel

import com.example.newsapp.domain.common.AppError

object UiErrorMapper {

    fun map(error: AppError): UiError {
        return when (error) {
            AppError.NetworkUnavailable -> UiError(
                title = "No internet connection",
                message = "Please check your network and try again.",
                canRetry = true
            )

            AppError.Timeout -> UiError(
                title = "Request timed out",
                message = "The server took too long to respond. Please try again.",
                canRetry = true
            )

            AppError.Unauthorized -> UiError(
                title = "Session expired",
                message = "Please sign in again.",
                canRetry = false
            )

            AppError.Forbidden -> UiError(
                title = "Access denied",
                message = "You do not have permission to view this content.",
                canRetry = false
            )

            AppError.NotFound -> UiError(
                title = "News not found",
                message = "The requested resource was not found.",
                canRetry = false
            )

            AppError.Server -> UiError(
                title = "Server unavailable",
                message = "Our servers are having trouble. Please try again shortly.",
                canRetry = true
            )

            is AppError.Http -> UiError(
                title = "Request failed",
                message = "Unexpected server response (${error.code}). Please try again.",
                canRetry = true
            )

            AppError.OperationCancelled -> UiError(
                title = "Request cancelled",
                message = "The operation was cancelled.",
                canRetry = true
            )

            is AppError.Unknown -> UiError(
                title = "Something went wrong",
                message = "An unexpected error occurred. Please try again.",
                canRetry = true,
                debugMessage = error.cause?.message
            )
        }
    }
}