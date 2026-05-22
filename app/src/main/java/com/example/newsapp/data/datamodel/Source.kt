package com.example.newsapp.data.datamodel


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Source(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("url")
    val url: String,
    @SerialName("category")
    val category: String,
    @SerialName("language")
    val language: String,
    @SerialName("country")
    val country: String
) : Parcelable