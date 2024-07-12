package com.example.gitem.data.repo

import androidx.paging.PagingData
import com.example.gitem.data.model.GithubRepo
import com.example.gitem.data.model.GithubUser
import com.example.gitem.data.model.GithubUserDetails
import kotlinx.coroutines.flow.Flow

interface GitemRepository {

    fun getRepoSearchResultStream(query: String): Flow<PagingData<GithubRepo>>

    fun getUserSearchResultStream(query: String): Flow<PagingData<GithubUser>>

    fun getUserDetails(userId: Int): Flow<Result<GithubUserDetails>>

    fun getUserReposStream(userId: String): Flow<PagingData<GithubRepo>>
}