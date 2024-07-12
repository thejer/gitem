package com.example.gitem.ui.repositories

import com.example.gitem.data.model.GithubRepo

data class RepoItemData(
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String?,
    val stars: Int,
    val language: String?,
    val updatedDate: String,
    val private: Boolean,
    val isForked: Boolean,
    val topics: List<String>,
    val owner: OwnerData
)

data class OwnerData(
    val ownerName: String,
    val avatarUrl: String
)


val defaultRepoItemData = RepoItemData(
    id = 1L,
    name = "Vance",
    fullName = "Joy/Vance",
    description = "These are random words that will be replaced in due time. Config files for my github profile These are random words that will be replaced in due time. Config files for my github profile These are random words that will be replaced in due time. Config files for my github profile",
    stars = 100,
    language = "Python",
    updatedDate = "2019-10-21T22:11:24Z",
    private = false,
    isForked = true,
    topics = listOf(
        "Design System",
        "Types",
        "Shadows",
        "Design System",
        "Types",
        "Shadows",
        "Design System",
        "Types",
        "Shadows",
        "Design System",
        "Types",
        "Shadows",
        "Design System",
        "Types",
        "Shadows",
        "Design System",
        "Types",
        "Shadows",
    ),
    owner = OwnerData("Joy", "")
)

fun GithubRepo.toRepoItemData() = RepoItemData(
    id = id,
    name = name,
    fullName = fullName,
    description = description,
    stars = stars,
    language = language,
    updatedDate = updatedDate,
    private = private,
    isForked = isForked,
    topics = topics,
    owner = OwnerData(ownerName = owner.ownerName, avatarUrl = owner.avatarUrl)
)