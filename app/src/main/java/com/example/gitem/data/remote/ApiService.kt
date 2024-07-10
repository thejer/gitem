package com.example.gitem.data.remote

import com.example.gitem.data.model.GithubRepo
import com.example.gitem.data.model.GithubSearchResponse
import com.example.gitem.data.model.GithubUser
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search/repositories?sort=stars")
    suspend fun searchGithubRepository(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): GithubSearchResponse<GithubRepo>

    @GET("search/users")
    suspend fun searchGithubUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): GithubSearchResponse<GithubUser>


}