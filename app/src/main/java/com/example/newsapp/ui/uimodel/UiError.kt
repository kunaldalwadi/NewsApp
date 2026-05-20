package com.example.newsapp.ui.uimodel

data class UiError(
    val title: String,
    val message: String,
    val canRetry: Boolean = true,
    val debugMessage: String? = null
)