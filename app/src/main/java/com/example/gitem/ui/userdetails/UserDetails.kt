package com.example.gitem.ui.userdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.gitem.R
import com.example.gitem.data.model.GithubRepo
import com.example.gitem.ui.repositories.toRepoItemData
import com.example.gitem.ui.theme.Black
import com.example.gitem.ui.theme.GitemTheme
import com.example.gitem.ui.theme.LostGrey
import com.example.gitem.ui.theme.ManropeFontFamily
import com.example.gitem.ui.theme.Midnight
import com.example.gitem.ui.theme.Midnight_55
import com.example.gitem.ui.theme.Navy
import com.example.gitem.ui.theme.White
import com.example.gitem.ui.uiutils.HorizontalSpace
import com.example.gitem.ui.uiutils.LoadingIndicator
import com.example.gitem.ui.uiutils.VerticalSpace

@Composable
fun UserDetails(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
    viewModel: UserDetailsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val userRepos = viewModel.pagingDataFlow.collectAsLazyPagingItems()

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    uiState.value.loadingState.let { loadingState ->
        if (loadingState is LoadingState.Error) {
            LaunchedEffect(key1 = loadingState.message) {
                snackBarHostState.showSnackbar(loadingState.message)
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .padding(vertical = 31.dp, horizontal = 20.dp)

    ) {
        SnackbarHost(hostState = snackBarHostState)

        UpButton(onBackPressed)

        VerticalSpace(height = 18.dp)

        val userDetailsData = uiState.value.userDetailsData

        MainUserDetails(userDetailsData)

        VerticalSpace(height = 20.dp)
        LoadingIndicator(isLoading = uiState.value.loadingState is LoadingState.Loading)
        UserDetailsRepos(userDetailsData, userRepos, snackBarHostState)
    }
}

@Composable
private fun UpButton(onBackPressed: () -> Unit) {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .clickable { onBackPressed() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            tint = Navy,
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 5.dp, vertical = 2.dp),
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = stringResource(R.string.back_button)
        )

        HorizontalSpace(width = 8.dp)

        Text(
            text = stringResource(id = R.string.users),
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontFamily = ManropeFontFamily,
                color = Black,
                fontSize = 12.sp
            )
        )
    }
}

@Composable
private fun UserDetailsRepos(
    userDetailsData: UserDetailsData,
    repos: LazyPagingItems<GithubRepo>,
    snackbarHostState: SnackbarHostState
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = stringResource(id = R.string.repositories),
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = Black
            )
            HorizontalSpace(width = 2.dp)

            RepoCountPill(count = userDetailsData.publicRepos)
        }
        VerticalSpace(height = 8.dp)

        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            HorizontalDivider(thickness = 1.dp, color = Black, modifier = Modifier.width(93.dp))

            HorizontalDivider(thickness = 1.dp, color = LostGrey, modifier = Modifier.weight(1f))
        }

        Column {
            repos.loadState.let { loadState ->
                LoadingIndicator(isLoading = loadState.refresh is LoadState.Loading)
                if (loadState.refresh is LoadState.Error) {
                    val errorMessage = (loadState.refresh as LoadState.Error).error.message
                        ?: "Unknown error"
                    LaunchedEffect(key1 = errorMessage) {
                        snackbarHostState.showSnackbar(errorMessage)
                    }
                }
            }
            if (repos.itemCount == 0) {
                EmptyReposState()
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    items(repos.itemCount) { index ->
                        if (index == 0) VerticalSpace(height = 10.dp)
                        val repoItemData = repos[index]?.toRepoItemData()
                        repoItemData?.let {
                            UserRepoItem(repoItemData = repoItemData)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun RepoCountPill(count: Int) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(8.dp))
            .background(LostGrey)
    ) {
        Text(
            modifier = Modifier.padding(vertical = 1.dp, horizontal = 5.dp),
            text = count.toString(),
            style = TextStyle(
                color = Black,
                fontSize = 8.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}


@Composable
fun EmptyReposState() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_no_user_repos),
                contentDescription = stringResource(R.string.no_user_repos)
            )

            VerticalSpace(27.dp)

            Text(
                modifier = Modifier
                    .wrapContentHeight()
                    .width(242.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.no_user_repos),
                textAlign = TextAlign.Center,
                color = Black,
                fontSize = 10.sp,
                fontFamily = ManropeFontFamily
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RepoCountPillPreview() {
    GitemTheme {
        RepoCountPill(200)
    }
}

@Composable
private fun MainUserDetails(userDetailsData: UserDetailsData) {
    Column {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(userDetailsData.avatarUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = userDetailsData.name,
                placeholder = painterResource(id = R.drawable.ic_user_filled)
            )

            HorizontalSpace(width = 12.dp)
            Column {
                userDetailsData.name?.let {
                    Text(
                        text = userDetailsData.name,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = ManropeFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Black
                        )
                    )
                }

                Text(
                    text = userDetailsData.username,
                    style = TextStyle(
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Black
                    )
                )
            }
        }

        VerticalSpace(height = 15.dp)

        userDetailsData.bio?.let { bio ->
            Text(
                text = bio,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Black
                )
            )
        }
        VerticalSpace(height = 10.dp)

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            userDetailsData.location?.let { location ->
                Icon(
                    modifier = Modifier
                        .size(15.dp),
                    tint = Midnight_55,
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = stringResource(R.string.location)
                )

                HorizontalSpace(width = 6.dp)

                Text(
                    text = location,
                    style = TextStyle(
                        color = Midnight_55,
                        fontSize = 10.sp,
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            HorizontalSpace(width = 8.dp)

            userDetailsData.blog?.let { blog ->
                Icon(
                    modifier = Modifier
                        .size(15.dp),
                    tint = Midnight_55,
                    painter = painterResource(id = R.drawable.ic_link),
                    contentDescription = stringResource(R.string.blog_link)
                )

                HorizontalSpace(width = 6.dp)

                Text(
                    text = blog,
                    style = TextStyle(
                        color = Midnight,
                        fontSize = 10.sp,
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

        VerticalSpace(height = 10.dp)

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(15.dp),
                tint = Midnight_55,
                painter = painterResource(id = R.drawable.ic_people),
                contentDescription = stringResource(R.string.followers)
            )

            HorizontalSpace(width = 6.dp)

            Text(
                text = stringResource(R.string.followers_template, userDetailsData.followers),
                style = TextStyle(
                    color = Midnight_55,
                    fontSize = 10.sp,
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Medium
                )
            )

            HorizontalSpace(width = 6.dp)

            Icon(
                modifier = Modifier
                    .size(2.dp),
                tint = Midnight_55,
                painter = painterResource(id = R.drawable.ic_dot),
                contentDescription = null
            )

            HorizontalSpace(width = 6.dp)

            Text(
                text = stringResource(R.string.following_template, userDetailsData.following),
                style = TextStyle(
                    color = Midnight_55,
                    fontSize = 10.sp,
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserDetailsPreview() {
    GitemTheme {
        MainUserDetails(
            defaultUserDetails().copy(
                bio = "This is a random bio, which will be replace with actual content",
                location = "Lagos, Nigeria",
                name = "Paige Brown",
                username = "DynamicWebPaige",
                blog = "http://www.paige.com"
            )
        )
    }
}