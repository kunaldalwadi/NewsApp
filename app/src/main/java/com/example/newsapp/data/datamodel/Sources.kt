package com.example.newsapp.data.datamodel


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Sources(
    @SerialName("sources")
    val sources: List<Source>
) : Parcelable