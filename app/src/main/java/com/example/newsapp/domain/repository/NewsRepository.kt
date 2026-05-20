package com.example.newsapp.domain.repository

import retrofit2.HttpException
import com.example.newsapp.BuildConfig
import com.example.newsapp.data.datamodel.News
import com.example.newsapp.data.remotedatasource.NewsServiceEndpoints
import com.example.newsapp.domain.common.AppError
import com.example.newsapp.domain.common.ResultState
import kotlinx.coroutines.CancellationException
import okio.IOException
import java.net.SocketTimeoutException

class NewsRepository(
    private val newsService: NewsServiceEndpoints
) {
    // This is a placeholder for the actual implementation of the NewsRepository.
    // In a real application, this class would contain methods to fetch news data from a remote source,
    // such as an API, and possibly also methods to cache data locally.
    private val apiKey = BuildConfig.API_KEY

    suspend fun fetchTopHeadlinesForCountry(country: String): ResultState<News> {
        return try {
            val topHeadlinesForCountry =
                newsService.getTopHeadlinesForCountry(apiKey = apiKey, country = country)
            ResultState.Success(topHeadlinesForCountry)

            /**
             * From what we have learnt,
             *  - we need a cold flow from Repository to emit data to viewmodel.
             *  - we need a hot flow from viewmodel to emit data to UI.
             */

        } catch (e: Throwable) {
            ResultState.Error(mapThrowableToAppError(e))
        }
    }

    suspend fun fetchNewsForInput(input: String): ResultState<News> {
        return try {
            val newsForInput = newsService.getNewsForInput(apiKey = apiKey, input = input)
            ResultState.Success(newsForInput)
        } catch (e: Throwable) {
            ResultState.Error(mapThrowableToAppError(e))
        }
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