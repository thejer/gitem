package com.example.gitem.data.model

import com.google.gson.annotations.SerializedName

data class GithubRepo(
    @field:SerializedName("id")
    val id: Long,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("full_name")
    val fullName: String,
    @field:SerializedName("description")
    val description: String?,
    @field:SerializedName("stargazers_count")
    val stars: Int,
    @field:SerializedName("language")
    val language: String?,
    @field:SerializedName("topics")
    val topics: List<String>,
    @field:SerializedName("owner")
    val owner: Owner
)

data class Owner(
    @field:SerializedName("login")
    val ownerName: String,
    @field:SerializedName("avatar_url")
    val avatarUrl: String
)