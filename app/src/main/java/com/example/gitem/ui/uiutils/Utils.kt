package com.example.gitem.ui.uiutils

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gitem.R
import com.example.gitem.ui.theme.LightGrey
import com.example.gitem.ui.theme.MidGrey
import com.example.gitem.ui.theme.Navy
import com.example.gitem.ui.theme.RainGrey
import com.example.gitem.ui.theme.White

@Composable
fun Header(title: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = title,
        color = Color.Black,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}


@Composable
fun EmptyState(@StringRes title: Int) {
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
                painter = painterResource(id = R.drawable.ic_empty),
                contentDescription = stringResource(title)
            )

            VerticalSpace(28.dp)

            Text(
                modifier = Modifier
                    .wrapContentHeight()
                    .width(242.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = title),
                textAlign = TextAlign.Center,
                color = MidGrey,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun SearchField(
    @StringRes hint: Int,
    onTextChange: (String) -> Unit = {},
    onSearchClicked: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .border(
                width = .5.dp,
                color = LightGrey,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(start = 13.dp)
            .fillMaxWidth()
            .height(41.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_repo_search),
            tint = RainGrey,
            contentDescription = "Repository search"
        )

        HorizontalSpace(width = 10.dp)

        var text by remember { mutableStateOf("") }

        BasicTextField(
            modifier = Modifier.weight(1f),
            value = "",
            textStyle = LocalTextStyle.current.copy(
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = Navy
            ),
            decorationBox = { innerTextField ->
                if (text.isEmpty()) {
                    Text(
                        text = stringResource(id = hint),
                        color = RainGrey,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
                innerTextField()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchClicked()
            }),
            singleLine = true,
            onValueChange = {
                text = it
                onTextChange(it)
            },

            )

        Box(
            modifier = Modifier
                .width(84.dp)
                .align(Alignment.CenterVertically)
                .fillMaxHeight()
                .padding(vertical = 4.dp, horizontal = 5.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(Navy),
            Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.search),
                color = White,
                fontSize = 10.sp,
            )
        }
    }
}

@Composable
fun HorizontalSpace(width: Dp, modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.width(width))
}


@Composable
fun VerticalSpace(height: Dp, modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(height))
}
