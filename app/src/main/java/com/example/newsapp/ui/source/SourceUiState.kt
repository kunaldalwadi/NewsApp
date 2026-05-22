package com.example.newsapp.ui.source

import com.example.newsapp.data.datamodel.Source
import com.example.newsapp.ui.uimodel.UiError

sealed interface SourceUiState {
    object Loading : SourceUiState
    data class Success(val sources: List<Source>) : SourceUiState
    data class Empty(val message: String) : SourceUiState
    data class Error(val uiError: UiError) : SourceUiState
}