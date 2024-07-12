package com.example.gitem.ui.usersearch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gitem.R
import com.example.gitem.ui.theme.GitemTheme
import com.example.gitem.ui.theme.White
import com.example.gitem.ui.uiutils.EmptyState
import com.example.gitem.ui.uiutils.Header
import com.example.gitem.ui.uiutils.LoadingIndicator
import com.example.gitem.ui.uiutils.SearchField
import com.example.gitem.ui.uiutils.VerticalSpace
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SearchUsers(
    modifier: Modifier = Modifier,
    onUserClicked: (Int) -> Unit = {},
    viewModel: SearchUsersViewModel = hiltViewModel()
) {

    val searchResult = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val uiState = viewModel.state.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .padding(start = 21.dp, top = 40.dp, end = 21.dp)
    ) {

        SnackbarHost(hostState = snackBarHostState)

        Header(title = stringResource(R.string.users))

        VerticalSpace(31.dp)

        SearchField(
            hint = R.string.search_for_users,
            onSearchClicked = {
                searchAndScroll(coroutineScope, listState, viewModel, it)
            },
            onTextChange = {
                searchAndScroll(coroutineScope, listState, viewModel, it)
            }
        )

        searchResult.loadState.let { loadState ->
            LoadingIndicator(isLoading = loadState.refresh is LoadState.Loading)
            if (loadState.refresh is LoadState.Error && uiState.value.query.isNotEmpty()) {
                val errorMessage = (loadState.refresh as LoadState.Error).error.message
                    ?: "Unknown error"
                LaunchedEffect(key1 = errorMessage) {
                    snackBarHostState.showSnackbar(errorMessage)
                }
            }
        }

        if (searchResult.itemCount == 0) {
            val emptyTitle = if (uiState.value.query.isNotEmpty())
                R.string.users_no_results
            else R.string.empty_users_search
            EmptyState(emptyTitle)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(5.dp), state = listState) {
                items(count = searchResult.itemCount) { index ->
                    if (index == 0) VerticalSpace(height = 14.dp)
                    val userItemData = searchResult[index]?.toUserItemData()
                    userItemData?.let { user ->
                        UserItem(userItemData = user) {
                            onUserClicked(user.id)
                        }
                    }
                }
            }
        }
    }
}

private fun searchAndScroll(
    coroutineScope: CoroutineScope,
    listState: LazyListState,
    viewModel: SearchUsersViewModel,
    query: String
) {
    coroutineScope.launch {
        listState.animateScrollToItem(0)
    }
    viewModel.onQuery(query)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GitemTheme {
        SearchUsers()
    }
}