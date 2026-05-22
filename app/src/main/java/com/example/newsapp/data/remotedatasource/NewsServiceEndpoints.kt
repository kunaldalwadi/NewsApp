package com.example.newsapp.data.remotedatasource

import com.example.newsapp.data.datamodel.News
import com.example.newsapp.data.datamodel.Sources
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsServiceEndpoints {

    @GET(value = "top-headlines")
    suspend fun getTopHeadlinesForCountry(
        @Header("Authorization") apiKey: String,
        @Query("country") country: String = "us"
    ): News

    @GET(value = "everything")
    suspend fun getNewsForInput(
        @Header("Authorization") apiKey: String,
        @Query("q") input: String
    ): News

    @GET(value = "sources")
    suspend fun getNewsSources(
        @Header("Authorization") apiKey: String
    ) : Sources
}