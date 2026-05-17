package com.example.newsapp.ui.viewmodel

import com.example.newsapp.data.model.News
import com.example.newsapp.domain.common.ResultState
import com.example.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NewsViewModel(
    private val newsRepository: NewsRepository
) : BaseViewModel() {

    private val _topHeadlines = MutableStateFlow<News?>(null)
    val topHeadlines = _topHeadlines.asStateFlow()

    private val _resultsForInput = MutableStateFlow<News?>(null)
    val resultsForInput = _resultsForInput.asStateFlow()

    init {
        getTopHeadlinesFromCountry()
    }

    private fun getTopHeadlinesFromCountry(country: String = "us") {
        launchSafely(showLoading = true) {
            when (val result = newsRepository.fetchTopHeadlinesForCountry(country)) {
                is ResultState.Success -> {
                    _topHeadlines.value = result.data
                }

                is ResultState.Error -> {
                    setError(result.message)
                }
            }
        }
    }

    private fun getNewsForInput(input: String) {
        launchSafely(showLoading = true) {
            when(val result = newsRepository.fetchNewsForInput(input)) {
                is ResultState.Success -> {
                    _resultsForInput.value = result.data
                }

                is ResultState.Error -> {
                    setError(result.message)
                }
            }
        }
    }
}