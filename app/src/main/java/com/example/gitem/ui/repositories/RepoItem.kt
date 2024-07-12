package com.example.gitem.ui.repositories

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.gitem.R
import com.example.gitem.ui.theme.BabyBlue
import com.example.gitem.ui.theme.Black
import com.example.gitem.ui.theme.Burgundy
import com.example.gitem.ui.theme.GitemTheme
import com.example.gitem.ui.theme.GoGreen
import com.example.gitem.ui.theme.Iceberg
import com.example.gitem.ui.theme.ManropeFontFamily
import com.example.gitem.ui.theme.Navy
import com.example.gitem.ui.theme.Smudge
import com.example.gitem.ui.theme.White
import com.example.gitem.ui.uiutils.HorizontalSpace
import com.example.gitem.ui.uiutils.VerticalSpace

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RepoItem(repoItemData: RepoItemData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .border(.4.dp, Smudge, shape = RoundedCornerShape(2.dp))
            .clip(RoundedCornerShape(2.dp))
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(2.dp),
                spotColor = Black.copy(.05f),
                ambientColor = Black.copy(.05f)
            )
            .background(White),
    ) {
        Column(
            modifier = Modifier
                .background(White)
                .padding(12.dp)
                .fillMaxWidth()
                .wrapContentHeight()
            ) {

            Row (Modifier, verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(repoItemData.owner.avatarUrl)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = repoItemData.owner.ownerName,
                    placeholder = painterResource(id = R.drawable.placeholder_avatar)
                )

                HorizontalSpace(8.dp)
                val repoName = if (repoItemData.name.length > 10) "${repoItemData.name.take(10)}…" else repoItemData.name
                Text(
                    text = "${repoItemData.owner.ownerName}/",
                    color = Burgundy,
                    fontSize = 12.sp,
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.Normal,
                )

                Text(
                    text = repoName,
                    color = Black,
                    fontSize = 12.sp,
                    fontFamily = ManropeFontFamily,
                    fontWeight = FontWeight.SemiBold
                )

                HorizontalSpace(modifier = Modifier
                    .weight(1f), width = 0.dp)

                Icon(
                    modifier = Modifier.size(12.dp),
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = stringResource(R.string.star),
                    tint = Navy
                )

                HorizontalSpace(width = 3.dp)
                Text(
                    text = repoItemData.stars.toString(),
                    style = TextStyle(
                        color = Black,
                        fontSize = 10.sp,
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.Normal
                    )
                )

                repoItemData.language?.let {
                    HorizontalSpace(width = 12.dp)

                    Spacer(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(GoGreen)
                    )

                    HorizontalSpace(width = 3.dp)

                    Text(
                        text = it,
                        style = TextStyle(
                            color = Black,
                            fontSize = 10.sp,
                            fontFamily = ManropeFontFamily,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }
            repoItemData.description?.let {
                VerticalSpace(height = 12.dp)
                
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    text = it,
                    style = TextStyle(
                        color = Black,
                        fontSize = 12.sp,
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
            if (repoItemData.topics.isNotEmpty()) {
                VerticalSpace(height = 16.dp)
                FlowRow (
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),

                ) {
                    repoItemData.topics.forEach {
                        TopicPill(topic = it)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepoItemPreview() {
    GitemTheme {
        RepoItem(defaultRepoItemData)
    }
}

@Composable
fun TopicPill(topic: String) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(6.dp))
            .background(BabyBlue)
    ) {
        Text(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 7.dp),
            text = topic,
            style = TextStyle(
                color = Iceberg,
                fontSize = 10.sp,
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TopicPreview() {
    GitemTheme {
        TopicPill("Design System")
    }
}
