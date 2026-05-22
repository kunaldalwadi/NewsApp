package com.example.newsapp.domain.repository

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.datamodel.News
import com.example.newsapp.data.remotedatasource.NewsServiceEndpoints
import com.example.newsapp.domain.common.ResultState
import com.example.newsapp.domain.network.mapThrowableToAppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.CancellationException

class NewsRepository(
    private val newsService: NewsServiceEndpoints
) {
    // This is a placeholder for the actual implementation of the NewsRepository.
    // In a real application, this class would contain methods to fetch news data from a remote source,
    // such as an API, and possibly also methods to cache data locally.
    private val apiKey = BuildConfig.API_KEY

    /**
     * Updating the return type to Flow<ResultState<News>> allows us to emit multiple values over time,
     * which is useful for handling loading states, success states, and error states in a more reactive way.
     *
     * When the return type is a type of flow suspend keyword is not needed as we are not returning
     * a single value but a stream of values that can be collected asynchronously.
     */
    fun fetchTopHeadlinesForCountry(country: String): Flow<ResultState<News>> = flow {
        emit(value = ResultState.Loading)

        val topHeadlinesForCountry =
            newsService.getTopHeadlinesForCountry(apiKey = apiKey, country = country)

        emit(value = ResultState.Success(data = topHeadlinesForCountry))

        /**
         *  - we need a cold flow from Repository to emit data to viewmodel.
         *  - we need a hot flow from viewmodel to emit data to UI.
         */

    }.catch {
        if (it is CancellationException) throw it
        emit(value = ResultState.Error(mapThrowableToAppError(throwable = it)))
    }

    fun fetchNewsForInput(input: String): Flow<ResultState<News>> = flow<ResultState<News>> {
        emit(value = ResultState.Loading)

        val newsForInput = newsService.getNewsForInput(apiKey = apiKey, input = input)

        emit(value = ResultState.Success(data = newsForInput))
    }.catch {
        if (it is CancellationException) throw it
        emit(value = ResultState.Error(mapThrowableToAppError(throwable = it)))
    }
}