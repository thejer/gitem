package com.example.gitem.data

import com.example.gitem.data.model.GithubSearchResponse
import com.example.gitem.data.model.GithubUserDetails
import com.example.gitem.data.remote.ApiService
import com.example.gitem.utils.testGitHubUserDetails
import com.example.gitem.utils.testGithubUser
import com.example.gitem.utils.testRepoItemData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class MainGithubDataSourceTest {

    private val apiService: ApiService = mock()
    private lateinit var dataSource: MainGithubDataSource

    private val mockRepos = listOf(
        testRepoItemData(1L),
        testRepoItemData(2L),
        testRepoItemData(3L),
    )
    private val mockUsers = listOf(
        testGithubUser(1),
        testGithubUser(2),
        testGithubUser(3),
    )
    @Before
    fun setup() {
        dataSource = MainGithubDataSource(apiService)
    }

    @org.junit.Test
    fun `searchRepo returns expected response`() = runTest {
        val query = "Vance"
        val page = 1
        val itemsPerPage = 3
        val expectedResponse = GithubSearchResponse(
            items = mockRepos, total = 3
        )
        whenever(apiService.searchGithubRepository(query, page, itemsPerPage))
            .thenReturn(expectedResponse)

        val actualResponse = dataSource.searchRepo(query, page, itemsPerPage)

        assertThat(actualResponse).isEqualTo(expectedResponse)
    }

    @org.junit.Test
    fun `searchUser returns expected response`() = runTest {
        val query = "name"
        val page = 1
        val itemsPerPage = 3
        val expectedResponse = GithubSearchResponse(items = mockUsers, total = 3)
        whenever(apiService.searchGithubUsers(query, page, itemsPerPage)).thenReturn(expectedResponse)

        val actualResponse = dataSource.searchUser(query, page, itemsPerPage)

        assertThat(actualResponse).isEqualTo(expectedResponse)
    }

    @org.junit.Test
    fun `getUserRepos returns expected list`() = runTest {
        val userId = "1"
        val page = 1
        val itemsPerPage = 2
        val expectedList = mockRepos
        whenever(apiService.getGithubUserRepos(userId, page, itemsPerPage))
            .thenReturn(expectedList)

        val actualList = dataSource.getUserRepos(userId, page, itemsPerPage)

        assertThat(actualList).isEqualTo(expectedList)
    }

    @org.junit.Test
    fun `getUserDetails returns Success when response is successful`() = runTest {
        val userId = 1
        val userDetails = testGitHubUserDetails()
        val response = Response.success(userDetails)
        whenever(apiService.getGithubUser(userId)).thenReturn(response)

        val result = dataSource.getUserDetails(userId)

        assertThat(result).isEqualTo(Result.success(userDetails))
    }

    @org.junit.Test
    fun `getUserDetails returns Failure when response is unsuccessful`() = runTest {
        val userId = 1
        val errorBody = "{}".toResponseBody("application/json".toMediaTypeOrNull())
        val response = Response.error<GithubUserDetails>(404, errorBody)
        whenever(apiService.getGithubUser(userId)).thenReturn(response)

        val result = dataSource.getUserDetails(userId)

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(Exception::class.java)
    }

    @org.junit.Test
    fun `getUserDetails returns Failure when exception occurs`() = runTest {
        val userId = 1
        val exception = RuntimeException()
        whenever(apiService.getGithubUser(userId)).thenThrow(exception)

        val result = dataSource.getUserDetails(userId)

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(exception)
    }
}