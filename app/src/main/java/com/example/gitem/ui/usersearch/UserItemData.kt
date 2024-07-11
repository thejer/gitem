package com.example.gitem.ui.usersearch

import com.example.gitem.data.model.GithubUser

data class UserItemData (
    val avatarUrl: String,
    val username: String,
)

fun GithubUser.toUserItemData() = UserItemData (
    avatarUrl = avatarUrl,
    username = username
)