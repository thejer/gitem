package com.example.gitem.ui.usersearch

import com.example.gitem.data.model.GithubUser

data class UserItemData(
    val id: Int,
    val avatarUrl: String,
    val username: String,
)

fun GithubUser.toUserItemData() = UserItemData(
    id = id,
    avatarUrl = avatarUrl,
    username = username
)