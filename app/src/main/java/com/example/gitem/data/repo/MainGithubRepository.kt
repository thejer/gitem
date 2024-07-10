package com.example.gitem.data.repo

import androidx.paging.PagingData
import com.example.gitem.data.GithubDataSource
import com.example.gitem.data.model.GithubRepo
import com.example.gitem.data.model.GithubUser
import com.example.gitem.data.paging.createPager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainGithubRepository @Inject constructor(
    private val githubDataSource: GithubDataSource
) : GithubRepository {

    override fun getRepoSearchResultStream(query: String): Flow<PagingData<GithubRepo>> =
        createPager(query, false) { apiQuery, page, size->
            githubDataSource.searchRepo(apiQuery, page, size)
        }

    override fun getUserSearchResultStream(query: String): Flow<PagingData<GithubUser>> =
        createPager(query, false) { apiQuery, page, size ->
            githubDataSource.searchUser(apiQuery, page, size)
        }
}