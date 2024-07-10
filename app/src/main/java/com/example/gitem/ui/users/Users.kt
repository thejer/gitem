package com.example.gitem.ui.users

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gitem.R
import com.example.gitem.ui.theme.GitemTheme
import com.example.gitem.ui.uiutils.EmptyState
import com.example.gitem.ui.uiutils.Header
import com.example.gitem.ui.uiutils.SearchField

@Composable
fun Users(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 21.dp, top = 40.dp, end = 21.dp)
    ) {

        Header(title = stringResource(R.string.users))

        Spacer(modifier = Modifier.height(31.dp))

        SearchField(R.string.empty_users_search)

        EmptyState(R.string.search_for_users)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GitemTheme {
        Users()
    }
}