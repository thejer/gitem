package com.example.gitem.data.model

import com.google.gson.annotations.SerializedName

data class GithubSearchResponse<T>(
    @SerializedName("total_count")
    val total: Int = 0,
    @SerializedName("items")
    val items: List<T> = emptyList(),
)
