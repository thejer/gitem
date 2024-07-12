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

    override suspend fun getUserRepos(
        userId: String,
        page: Int,
        itemsPerPage: Int
    ): List<GithubRepo> =
        apiService.getGithubUserRepos(userId, page, itemsPerPage)

    override suspend fun getUserDetails(userId: Int): Result<GithubUserDetails> {
        return try {
            val response = apiService.getGithubUser(userId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body() ?: throw Exception("Response body is null"))
            } else {
                Result.failure(Exception("Error fetching user details"))
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}