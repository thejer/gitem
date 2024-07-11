package com.example.gitem.ui.repositories

data class RepoItemData(
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String?,
    val stars: Int,
    val language: String?,
    val topics: List<String>,
    val owner: OwnerData
)

data class OwnerData(
    val ownerName: String,
    val avatarUrl: String
)


val defaultRepoItemData = RepoItemData (
    id = 1L,
    name = "Vance",
    fullName = "Joy/Vance",
    description = "These are random words that will be replaced in due time. Config files for my github profile These are random words that will be replaced in due time. Config files for my github profile These are random words that will be replaced in due time. Config files for my github profile",
    stars = 100,
    language = "Python",
    topics = listOf("Design System", "Types", "Shadows","Design System", "Types", "Shadows", "Design System", "Types", "Shadows","Design System", "Types", "Shadows","Design System", "Types", "Shadows", "Design System", "Types", "Shadows",),
    owner = OwnerData("Joy", "https://avatars.githubusercontent.com/u/54256709?v=4")
)