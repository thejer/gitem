package com.example.gitem.data.model


import com.google.gson.annotations.SerializedName

data class GithubUserDetails(
    @field:SerializedName("avatar_url")
    val avatarUrl: String,
    @field:SerializedName("bio")
    val bio: String?,
    @field:SerializedName("blog")
    val blog: String?,
    @field:SerializedName("followers")
    val followers: Int,
    @field:SerializedName("following")
    val following: Int,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("location")
    val location: String?,
    @field:SerializedName("login")
    val username: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("public_repos")
    val publicRepos: Int
)