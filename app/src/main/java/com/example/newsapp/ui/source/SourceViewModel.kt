package com.example.newsapp.ui.source

import com.example.newsapp.domain.common.ResultState
import com.example.newsapp.domain.repository.SourceRepository
import com.example.newsapp.ui.uimodel.UiErrorMapper
import com.example.newsapp.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SourceViewModel(
    private val sourceRepository: SourceRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<SourceUiState>(SourceUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchSources()
    }

    fun retry() = fetchSources()

    private fun fetchSources() {
        launchSafely(showLoading = true) {
            sourceRepository.getSourcesList().collect { sources ->
                when (sources) {
                    is ResultState.Loading -> {
                        _uiState.value = SourceUiState.Loading
                    }

                    is ResultState.Success -> {
                        val src = sources.data.sources
                        _uiState.value = if (src.isEmpty()) {
                            SourceUiState.Empty("No sources available right now.")
                        } else {
                            SourceUiState.Success(src)
                        }
                    }

                    is ResultState.Error -> {
                        _uiState.value = SourceUiState.Error(
                            uiError = UiErrorMapper.map(sources.error)
                        )
                    }
                }
            }
        }
    }
}