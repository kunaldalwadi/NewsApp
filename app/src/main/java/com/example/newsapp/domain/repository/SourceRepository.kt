package com.example.newsapp.domain.repository

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.datamodel.Sources
import com.example.newsapp.data.remotedatasource.NewsServiceEndpoints
import com.example.newsapp.domain.common.ResultState
import com.example.newsapp.domain.network.mapThrowableToAppError
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class SourceRepository(
    private val newsServiceEndpoints: NewsServiceEndpoints
) {

    private val apiKey = BuildConfig.API_KEY

    fun getSourcesList() : Flow<ResultState<Sources>> = flow {
        emit(value = ResultState.Loading)
        val sources = newsServiceEndpoints.getNewsSources(apiKey = apiKey)
        emit(value = ResultState.Success(data = sources))
    }.catch {
        if (it is CancellationException) throw it
        emit(value = ResultState.Error(mapThrowableToAppError(throwable = it)))
    }
}