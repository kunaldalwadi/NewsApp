package com.example.newsapp.domain.repository

import coil.network.HttpException
import com.example.newsapp.BuildConfig
import com.example.newsapp.data.model.News
import com.example.newsapp.data.remotedatasource.NewsServiceEndpoints
import com.example.newsapp.domain.common.ResultState
import kotlinx.coroutines.CancellationException
import okio.IOException

class NewsRepository(
    private val newsService: NewsServiceEndpoints
) {
    // This is a placeholder for the actual implementation of the NewsRepository.
    // In a real application, this class would contain methods to fetch news data from a remote source, such as an API, and possibly also methods to cache data locally.
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


        } catch (e: HttpException) {
            // Handle HTTP exceptions, such as 404 or 500 errors
            ResultState.Error("HTTP error: ${e.response.code}", e)
        } catch (e: CancellationException) {
            // Handle coroutine cancellation
            ResultState.Error("Operation cancelled", e)
        } catch (e: IOException) {
            // Handle any other exceptions that may occur
            ResultState.Error("Network error: ${e.message}", e)
        } catch (e: Exception) {
            // Handle any other exceptions that may occur
            ResultState.Error("An unexpected error occurred: ${e.message}", e)
        }
    }
}