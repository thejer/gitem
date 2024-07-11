package com.example.gitem.ui.landing

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gitem.R
import com.example.gitem.ui.theme.GitemTheme
import com.example.gitem.ui.theme.LightPink
import com.example.gitem.ui.theme.ManropeFontFamily
import com.example.gitem.ui.theme.Smudge
import com.example.gitem.ui.theme.TealGreen
import com.example.gitem.ui.theme.White
import com.example.gitem.ui.theme.White_60
import com.example.gitem.ui.uiutils.Header
import com.example.gitem.ui.uiutils.VerticalSpace

@Composable
fun LandingPage(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .background(White)
            .padding(start = 20.dp, top = 40.dp, end = 20.dp)
    ){
        Header(title = stringResource(R.string.home))

        VerticalSpace(31.dp)

        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            LandingCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                icon = R.drawable.ic_user_landing,
                title = stringResource(R.string.users),
                backgroundColor = TealGreen
            )

            LandingCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                icon = R.drawable.ic_repo_landing,
                title = stringResource(R.string.repositories),
                backgroundColor = LightPink
            )
        }
    }

}

@Composable
private fun LandingCard(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    title: String,
    backgroundColor: Color,
    onCardClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .height(118.dp)
            .fillMaxWidth()
            .border(.4.dp, Smudge, shape = RoundedCornerShape(2.dp))
            .clip(RoundedCornerShape(2.dp))
            .clickable {
                onCardClick()
            },
        shape = RoundedCornerShape(2.dp)
    ) {
        Column(
            modifier = Modifier
                .background(backgroundColor)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(2.dp))
                    .padding(start = 12.dp, top = 10.dp)
                    .background(White_60)
            ) {
                Image(
                    modifier = Modifier.padding(7.dp),
                    painter = painterResource(id = icon),
                    contentDescription = title
                )
            }

            VerticalSpace(41.dp)

            Text(
                modifier = Modifier
                    .padding(start = 14.dp, bottom = 10.dp),
                text = title,
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LandingCardPreview() {
    GitemTheme {
        LandingCard(
            icon = R.drawable.ic_repo_landing,
            title = stringResource(R.string.repositories),
            backgroundColor = LightPink
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LandingPreview() {
    GitemTheme {
        LandingPage()
    }
}