package com.example.newsapp.domain.network

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.remotedatasource.NewsServiceEndpoints
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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