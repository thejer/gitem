package com.example.gitem.data.repo

import androidx.paging.PagingData
import com.example.gitem.data.GithubDataSource
import com.example.gitem.data.model.GithubRepo
import com.example.gitem.data.model.GithubUser
import com.example.gitem.data.model.GithubUserDetails
import com.example.gitem.data.paging.createPager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainGitemRepository @Inject constructor(
    private val githubDataSource: GithubDataSource
) : GitemRepository {

    override fun getRepoSearchResultStream(query: String): Flow<PagingData<GithubRepo>> =
        createPager(query, false) { apiQuery, page, size->
            githubDataSource.searchRepo(apiQuery, page, size)
        }

    override fun getUserSearchResultStream(query: String): Flow<PagingData<GithubUser>> =
        createPager(query, false) { apiQuery, page, size ->
            githubDataSource.searchUser(apiQuery, page, size)
        }

    override fun getUserDetails(userId: Int): Flow<GithubUserDetails> = flow {
        emit(githubDataSource.getUserDetails(userId))
    }.flowOn(Dispatchers.IO)

    override fun getUserRepos(userId: Int): Flow<List<GithubRepo>> = flow {
        emit(githubDataSource.getUserRepos(userId))
    }.flowOn(Dispatchers.IO)

    override fun getUserDetailsWithRepos(userId: Int): Flow<Pair<GithubUserDetails, List<GithubRepo>>> =
        combine(
             getUserDetails(userId),
             getUserRepos(userId),
             ::Pair
         )
}