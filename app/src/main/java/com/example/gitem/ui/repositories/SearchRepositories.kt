package com.example.gitem.ui.repositories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.gitem.R
import com.example.gitem.data.model.GithubRepo
import com.example.gitem.ui.theme.GitemTheme
import com.example.gitem.ui.theme.White
import com.example.gitem.ui.uiutils.EmptyState
import com.example.gitem.ui.uiutils.Header
import com.example.gitem.ui.uiutils.SearchField
import com.example.gitem.ui.uiutils.VerticalSpace

@Composable
fun SearchRepositories(
    modifier: Modifier = Modifier,
    viewModel: SearchRepositoriesViewModel = hiltViewModel()
) {

    val searchResult = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val uiState = viewModel.state.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .padding(start = 21.dp, top = 40.dp, end = 21.dp)
    ) {

        Header(title = stringResource(R.string.repositories))

        VerticalSpace(31.dp)

        SearchField(R.string.search_for_repositories,
            onSearchClicked = {
                viewModel.onQuery(it)
            }
        ) {
            viewModel.onQuery(it)
        }

        if (searchResult.itemCount == 0) {
            val emptyTitle = if (uiState.value.query.isNotEmpty())
                R.string.no_results
            else R.string.empty_repo_search
            EmptyState(emptyTitle)
        } else {
            RepoSearchResultList(searchResult)
        }
    }
}

@Composable
private fun RepoSearchResultList(searchResult: LazyPagingItems<GithubRepo>) {

    LazyColumn(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        items(count = searchResult.itemCount) { index ->
            if (index == 0) VerticalSpace(height = 14.dp)

            val repoItemData = searchResult[index]?.toRepoItemData()
            repoItemData?.let { repo ->
                RepoItem(repoItemData = repo)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchRepositoriesPreview() {
    GitemTheme {
        SearchRepositories()
    }
}