package com.example.gitem.utils

import com.example.gitem.data.model.GithubRepo
import com.example.gitem.data.model.GithubUser
import com.example.gitem.data.model.GithubUserDetails
import com.example.gitem.data.model.Owner

fun testRepoItemData(id: Long) = GithubRepo(
    id = id,
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
    owner = Owner("Joy", "")
)

fun testGithubUser(id: Int) = GithubUser (
    avatarUrl = "",
    id = id,
    username = "username",
)

fun testGitHubUserDetails() = GithubUserDetails(
    avatarUrl = "avatarUrl",
    bio = "Bio",
    blog = "blog",
    followers = 0,
    following = 0,
    id = 1,
    location = "location",
    username = "username",
    name = "name",
    publicRepos = 1
)