package com.example.gitem.ui.userdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gitem.data.model.GithubRepo
import com.example.gitem.data.repo.GitemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: GitemRepository
) : ViewModel() {

    private val userId: Int = checkNotNull(savedStateHandle["userId"])

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    val pagingDataFlow: Flow<PagingData<GithubRepo>>


    init {
        getUserDetails(userId)
        pagingDataFlow = getUserRepos(userId).cachedIn(viewModelScope)
    }

    private fun getUserDetails(userId: Int) = viewModelScope.launch {
        repository.getUserDetails(userId)
            .collectLatest { userDetails ->
                _uiState.update { state ->
                    state.copy(
                        userDetailsData = userDetails.toUserDetailsData(),
                    )
                }
            }
    }

    private fun getUserRepos(userId: Int): Flow<PagingData<GithubRepo>> = repository.getUserReposStream(userId.toString())
}


data class UiState(
    val userDetailsData: UserDetailsData = defaultUserDetails(),
)