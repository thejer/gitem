package com.example.gitem.data

import com.example.gitem.data.model.GithubRepo
import com.example.gitem.data.model.GithubSearchResponse
import com.example.gitem.data.model.GithubUser
import com.example.gitem.data.model.GithubUserDetails
import com.example.gitem.data.remote.ApiService
import javax.inject.Inject

class MainGithubDataSource @Inject constructor(
    private val apiService: ApiService
) : GithubDataSource {

    override suspend fun searchRepo(
        query: String,
        page: Int,
        itemsPerPage: Int
    ): GithubSearchResponse<GithubRepo> =
        apiService.searchGithubRepository(query, page, itemsPerPage)

    override suspend fun searchUser(
        query: String,
        page: Int,
        itemsPerPage: Int
    ): GithubSearchResponse<GithubUser> =
        apiService.searchGithubUsers(query, page, itemsPerPage)

    override suspend fun getUserRepos(userId: Int): List<GithubRepo> =
        apiService.getGithubUserRepos(userId)

    override suspend fun getUserDetails(userId: Int): GithubUserDetails =
        apiService.getGithubUser(userId)
}