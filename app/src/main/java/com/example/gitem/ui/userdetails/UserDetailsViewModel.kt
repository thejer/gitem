package com.example.gitem.ui.userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitem.data.repo.GitemRepository
import com.example.gitem.ui.repositories.RepoItemData
import com.example.gitem.ui.repositories.toRepoItemData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(
    private val repository: GitemRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun getUserDetails(userId: Int) = viewModelScope.launch {
        repository.getUserDetailsWithRepos(userId)
            .collectLatest { userPair ->
                _uiState.update { state ->
                    state.copy(
                        userDetailsData = userPair.first.toUserDetailsData(),
                        userRepos = userPair.second.map { it.toRepoItemData() }
                    )
                }
            }
    }
}


data class UiState(
    val userDetailsData: UserDetailsData = defaultUserDetails(),
    val userRepos: List<RepoItemData> = emptyList()
)