package com.example.gitem.data.repo

import app.cash.turbine.test
import com.example.gitem.data.GithubDataSource
import com.example.gitem.data.model.GithubUserDetails
import com.example.gitem.utils.testGitHubUserDetails
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MainGitemRepositoryTest {
    private val githubDataSource: GithubDataSource = mock()
    private lateinit var repository: MainGitemRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = MainGitemRepository(githubDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUserDetails with right user id returns success`() = runTest {
        val userId = 1
        val userDetails = testGitHubUserDetails()
        whenever(githubDataSource.getUserDetails(userId)).thenReturn(Result.success(userDetails))

        repository.getUserDetails(userId).test {
            assertThat(awaitItem()).isEqualTo(Result.success(userDetails))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getUserDetails with wrong user id returns failure`() = runTest {
        val userId = 2
        val exception = Exception("Error fetching user details")
        whenever(githubDataSource.getUserDetails(userId)).thenReturn(Result.failure(exception))

        repository.getUserDetails(userId).test {
            assertThat(awaitItem()).isEqualTo(Result.failure<GithubUserDetails>(exception))
            cancelAndIgnoreRemainingEvents()
        }
    }
}