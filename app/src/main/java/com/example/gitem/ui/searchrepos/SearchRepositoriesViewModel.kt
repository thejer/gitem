package com.example.gitem.ui.searchrepos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gitem.data.model.GithubRepo
import com.example.gitem.data.repo.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRepositoriesViewModel @Inject constructor(
    private val repository: GithubRepository,
): ViewModel() {

    val state: StateFlow<UiState>

    val pagingDataFlow: Flow<PagingData<GithubRepo>>

    val onQuery: (String) -> Unit

    init {
        val initialQuery = ""
        val queryStateFlow = MutableSharedFlow<String>()

        val searches = queryStateFlow
            .distinctUntilChanged()
            .onStart { emit(initialQuery) }

        pagingDataFlow = searches
            .flatMapLatest { searchRepo(it) }
            .cachedIn(viewModelScope)

        state = searches
            .map { UiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
                initialValue = UiState()
            )
        onQuery = {
            viewModelScope.launch { queryStateFlow.emit(it) }
        }
    }

    private fun searchRepo(queryString: String): Flow<PagingData<GithubRepo>> = repository.getRepoSearchResultStream(queryString)
}

data class UiState(
    val query: String = "",
//    val hasNotScrolledForCurrentSearch: Boolean = false
)