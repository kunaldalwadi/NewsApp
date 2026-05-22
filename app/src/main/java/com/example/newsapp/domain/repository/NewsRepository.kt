package com.example.newsapp.domain.repository

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.datamodel.News
import com.example.newsapp.data.remotedatasource.NewsServiceEndpoints
import com.example.newsapp.domain.common.AppError
import com.example.newsapp.domain.common.ResultState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

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
        emit(value = ResultState.Error(mapThrowableToAppError(throwable = it)))
    }

    fun fetchNewsForInput(input: String): Flow<ResultState<News>> = flow<ResultState<News>> {
        emit(value = ResultState.Loading)

        val newsForInput = newsService.getNewsForInput(apiKey = apiKey, input = input)

        emit(value = ResultState.Success(data = newsForInput))
    }.catch {
        emit(value = ResultState.Error(mapThrowableToAppError(throwable = it)))
    }

    private fun mapThrowableToAppError(throwable: Throwable): AppError {
        return when (throwable) {
            is CancellationException -> AppError.OperationCancelled
            is SocketTimeoutException -> AppError.Timeout
            is IOException -> AppError.NetworkUnavailable
            is HttpException -> mapHttpCodeToAppError(throwable.code())
            else -> AppError.Unknown(throwable)
        }
    }

    private fun mapHttpCodeToAppError(code: Int): AppError {
        return when (code) {
            401 -> AppError.Unauthorized
            403 -> AppError.Forbidden
            404 -> AppError.NotFound
            in 500..599 -> AppError.Server
            else -> AppError.Http(code)
        }
    }
}