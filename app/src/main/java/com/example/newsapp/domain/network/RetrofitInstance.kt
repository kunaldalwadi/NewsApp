package com.example.newsapp.domain.network

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.remotedatasource.NewsServiceEndpoints
import com.example.newsapp.domain.common.AppError
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException

object RetrofitInstance {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val newsServiceEndpoints: NewsServiceEndpoints by lazy {
        retrofit.create(NewsServiceEndpoints::class.java)
    }
}

fun mapThrowableToAppError(throwable: Throwable): AppError {
    return when (throwable) {
        is CancellationException -> AppError.OperationCancelled
        is SocketTimeoutException -> AppError.Timeout
        is IOException -> AppError.NetworkUnavailable
        is HttpException -> mapHttpCodeToAppError(throwable.code())
        else -> AppError.Unknown(throwable)
    }
}

fun mapHttpCodeToAppError(code: Int): AppError {
    return when (code) {
        401 -> AppError.Unauthorized
        403 -> AppError.Forbidden
        404 -> AppError.NotFound
        in 500..599 -> AppError.Server
        else -> AppError.Http(code)
    }
}