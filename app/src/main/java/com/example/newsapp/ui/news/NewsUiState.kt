package com.example.newsapp.ui.news

import com.example.newsapp.data.datamodel.Article
import com.example.newsapp.ui.uimodel.UiError

sealed interface NewsUiState {
    object Loading : NewsUiState
    data class Success(val articles: List<Article>) : NewsUiState
    data class Empty(val message: String) : NewsUiState
    data class Error(val uiError: UiError) : NewsUiState
}