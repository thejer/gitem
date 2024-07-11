package com.example.gitem.data.model

import com.google.gson.annotations.SerializedName

data class GithubUser (
    @field:SerializedName("avatar_url")
    val avatarUrl: String,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("login")
    val username: String,
)