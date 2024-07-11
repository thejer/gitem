package com.example.gitem.ui.userdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.gitem.R
import com.example.gitem.ui.theme.Black
import com.example.gitem.ui.theme.GitemTheme
import com.example.gitem.ui.theme.ManropeFontFamily
import com.example.gitem.ui.theme.Midnight
import com.example.gitem.ui.theme.Midnight_55
import com.example.gitem.ui.theme.Navy
import com.example.gitem.ui.theme.White
import com.example.gitem.ui.uiutils.HorizontalSpace
import com.example.gitem.ui.uiutils.VerticalSpace

@Composable
fun UserDetails(
    userId: Int,
    viewModel: UserDetailsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(vertical = 31.dp, horizontal = 20.dp)

    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .clickable {
                    // Go back
                },
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

        VerticalSpace(height = 18.dp)

        MainUserDetails(uiState.value.userDetailsData)
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
                Text(
                    text = userDetailsData.name,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Black
                    )
                )

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

        Text(
            text = userDetailsData.bio,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Medium,
                color = Black
            )
        )
        VerticalSpace(height = 10.dp)

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(15.dp),
                tint = Midnight_55,
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = stringResource(R.string.location)
            )

            HorizontalSpace(width = 6.dp)

            Text(
                text = userDetailsData.location,
                style = TextStyle(
                    color = Midnight_55,
                    fontSize = 10.sp,
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Medium
                )
            )
            HorizontalSpace(width = 8.dp)

            Icon(
                modifier = Modifier
                    .size(15.dp),
                tint = Midnight_55,
                painter = painterResource(id = R.drawable.ic_link),
                contentDescription = stringResource(R.string.blog_link)
            )

            HorizontalSpace(width = 6.dp)

            Text(
                text = userDetailsData.blog,
                style = TextStyle(
                    color = Midnight,
                    fontSize = 10.sp,
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            )
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
        UserDetails(0)
    }
}