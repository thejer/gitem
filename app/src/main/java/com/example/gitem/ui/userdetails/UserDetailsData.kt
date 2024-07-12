package com.example.gitem.ui.userdetails

import com.example.gitem.data.model.GithubUserDetails

data class UserDetailsData(
    val avatarUrl: String,
    val bio: String?,
    val blog: String?,
    val followers: Int,
    val following: Int,
    val id: Int,
    val location: String?,
    val username: String,
    val name: String?,
    val publicRepos: Int
)

fun defaultUserDetails() = UserDetailsData (
    avatarUrl = "",
    bio = "",
    blog = "",
    followers = 0,
    following = 0,
    id = 0,
    location = "",
    username = "",
    name = "",
    publicRepos = 0
)

fun GithubUserDetails.toUserDetailsData() = UserDetailsData (
    avatarUrl = avatarUrl,
    bio = bio,
    blog = blog,
    followers = followers,
    following = following,
    id = id,
    location = location,
    username = username,
    name = name,
    publicRepos = publicRepos
)