package com.example.gitem.data.repo

import androidx.paging.PagingData
import com.example.gitem.data.model.GithubRepo
import com.example.gitem.data.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

    fun getRepoSearchResultStream(query: String): Flow<PagingData<GithubRepo>>

    fun getUserSearchResultStream(query: String): Flow<PagingData<GithubUser>>
}