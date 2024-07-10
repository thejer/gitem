package com.example.gitem.data

import com.example.gitem.data.model.GithubRepo
import com.example.gitem.data.model.GithubSearchResponse
import com.example.gitem.data.model.GithubUser

interface GithubDataSource {

    suspend fun searchRepo(query: String, page: Int, itemsPerPage: Int): GithubSearchResponse<GithubRepo>

    suspend fun searchUser(query: String, page: Int, itemsPerPage: Int): GithubSearchResponse<GithubUser>
}