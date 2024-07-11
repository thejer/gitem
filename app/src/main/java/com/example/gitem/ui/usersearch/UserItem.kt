package com.example.gitem.ui.usersearch

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.gitem.R
import com.example.gitem.ui.theme.Black
import com.example.gitem.ui.theme.GitemTheme
import com.example.gitem.ui.theme.Iceberg
import com.example.gitem.ui.theme.ManropeFontFamily
import com.example.gitem.ui.theme.Smudge
import com.example.gitem.ui.theme.White
import com.example.gitem.ui.uiutils.HorizontalSpace

@Composable
fun UserItem(userItemData: UserItemData) {

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

        Row(
            modifier = Modifier
                .background(White)
                .padding(vertical = 9.dp, horizontal = 12.dp)
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(userItemData.avatarUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = userItemData.username,
                placeholder = painterResource(id = R.drawable.ic_user_filled)
            )

            HorizontalSpace(8.dp)

            Text(
                text = userItemData.username,
                color = Iceberg,
                fontSize = 12.sp,
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.SemiBold,
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun UserItemPreview() {
    GitemTheme {
        UserItem(UserItemData("", "DynamicWebPaige"))
    }
}