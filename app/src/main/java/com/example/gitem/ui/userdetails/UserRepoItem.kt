package com.example.gitem.ui.userdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gitem.R
import com.example.gitem.ui.repositories.RepoItemData
import com.example.gitem.ui.repositories.defaultRepoItemData
import com.example.gitem.ui.theme.Black
import com.example.gitem.ui.theme.Burgundy
import com.example.gitem.ui.theme.GitemTheme
import com.example.gitem.ui.theme.GoGreen
import com.example.gitem.ui.theme.LightGrey
import com.example.gitem.ui.theme.ManropeFontFamily
import com.example.gitem.ui.theme.Midnight_55
import com.example.gitem.ui.theme.Navy
import com.example.gitem.ui.theme.Smudge
import com.example.gitem.ui.theme.White
import com.example.gitem.ui.uiutils.HorizontalSpace
import com.example.gitem.ui.uiutils.VerticalSpace
import com.example.gitem.ui.uiutils.getElapsedPeriod

@Composable
fun UserRepoItem(repoItemData: RepoItemData) {
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

                HorizontalSpace(width = 9.dp)

                PrivatePill(isPrivate = repoItemData.private)

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

            VerticalSpace(height = 7.dp)

            Row {
                val forkedText = if (repoItemData.isForked) R.string.forked else R.string.not_forked
                Text(
                    text = stringResource(forkedText),
                    style = TextStyle(
                        fontSize = 10.sp,
                        color = Midnight_55,
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.Normal
                    )
                )

                HorizontalSpace(width = 17.dp)

                Text(
                    text = getElapsedPeriod(
                        context = LocalContext.current,
                        pastDateString = repoItemData.updatedDate
                    ),
                    style = TextStyle(
                        fontSize = 10.sp,
                        color = Midnight_55,
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}

@Composable
fun PrivatePill(isPrivate: Boolean) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .border(.5.dp, LightGrey, shape = RoundedCornerShape(5.dp))
            .clip(RoundedCornerShape(5.dp))
            .background(White)
    ) {
        val visibilityText = if (isPrivate) R.string.is_private else R.string.is_public
        Text(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 7.dp),
            text = stringResource(visibilityText),
            style = TextStyle(
                color = Black,
                fontSize = 8.sp,
                fontWeight = FontWeight.Normal
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrivatePillPreview() {
    GitemTheme {
        PrivatePill(true)
    }
}


@Preview(showBackground = true)
@Composable
fun UserRepoItemPreview() {
    GitemTheme {
        UserRepoItem(defaultRepoItemData)
    }
}
