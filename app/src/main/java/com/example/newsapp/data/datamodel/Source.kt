package com.example.newsapp.data.datamodel


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Source(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String
)