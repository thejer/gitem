package com.example.gitem.data.repo

import androidx.paging.PagingData
import com.example.gitem.data.GithubDataSource
import com.example.gitem.data.model.GithubRepo
import com.example.gitem.data.model.GithubUser
import com.example.gitem.data.model.GithubUserDetails
import com.example.gitem.data.paging.createPager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainGitemRepository @Inject constructor(
    private val githubDataSource: GithubDataSource
) : GitemRepository {

    override fun getRepoSearchResultStream(query: String): Flow<PagingData<GithubRepo>> =
        createPager(query, false) { apiQuery, page, size->
            githubDataSource.searchRepo(apiQuery, page, size).items
        }

    override fun getUserSearchResultStream(query: String): Flow<PagingData<GithubUser>> =
        createPager(query, false) { apiQuery, page, size ->
            githubDataSource.searchUser(apiQuery, page, size).items
        }

    override fun getUserDetails(userId: Int): Flow<Result<GithubUserDetails>> = flow {
        emit(githubDataSource.getUserDetails(userId))
    }.flowOn(Dispatchers.IO)

    override fun getUserReposStream(userId: String): Flow<PagingData<GithubRepo>> =
        createPager(userId, false) { id, page, size ->
            githubDataSource.getUserRepos(id, page, size)
        }

}