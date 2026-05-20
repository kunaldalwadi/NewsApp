package com.example.newsapp.ui.news

import com.example.newsapp.data.datamodel.News
import com.example.newsapp.domain.common.ResultState
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.ui.uimodel.UiErrorMapper
import com.example.newsapp.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewsViewModel(
    private val newsRepository: NewsRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getTopHeadlinesFromCountry()
    }

    fun retry() = getTopHeadlinesFromCountry()

    private fun getTopHeadlinesFromCountry(country: String = "us") {
        launchSafely(showLoading = true) {
            _uiState.value = NewsUiState.Loading
            when (val result = newsRepository.fetchTopHeadlinesForCountry(country)) {
                is ResultState.Success -> {
                    val articles = result.data.articles
                    _uiState.value = if (articles.isEmpty()) {
                        NewsUiState.Empty("No headlines available right now.")
                    } else {
                        NewsUiState.Success(articles)
                    }
                }

                is ResultState.Error -> {
                    _uiState.value = NewsUiState.Error(
                        uiError = UiErrorMapper.map(result.error)
                    )
                }
            }
        }
    }

    private fun getNewsForInput(input: String) {
        launchSafely(showLoading = true) {
            when(val result = newsRepository.fetchNewsForInput(input)) {
                is ResultState.Success -> {

                }

                is ResultState.Error -> {

                }
            }
        }
    }
}