package com.example.gitem.ui.userdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gitem.data.model.GithubRepo
import com.example.gitem.data.repo.GitemRepository
import com.example.gitem.utils.getExceptionMessage
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
        _uiState.update { state ->
            state.copy(loadingState = LoadingState.Loading)
        }
        repository.getUserDetails(userId).collectLatest { userDetailsResult ->
            if (userDetailsResult.isSuccess) {
                val userDetails = userDetailsResult.getOrNull()
                userDetails?.let {
                    _uiState.update { state ->
                        state.copy(
                            userDetailsData = userDetails.toUserDetailsData(),
                            loadingState = LoadingState.NotLoading
                        )
                    }
                }
            } else {
                val errorMessage = userDetailsResult.exceptionOrNull()?.getExceptionMessage() ?: "Unknown error"
                _uiState.update { state ->
                    state.copy(loadingState = LoadingState.Error(errorMessage))
                }
            }
        }
    }

    private fun getUserRepos(userId: Int): Flow<PagingData<GithubRepo>> =
        repository.getUserReposStream(userId.toString())
}

data class UiState(
    val userDetailsData: UserDetailsData = defaultUserDetails(),
    val loadingState: LoadingState = LoadingState.NotLoading
)

sealed class LoadingState {
    data object Loading : LoadingState()
    data object NotLoading : LoadingState()
    data class Error(val message: String) : LoadingState()
}
