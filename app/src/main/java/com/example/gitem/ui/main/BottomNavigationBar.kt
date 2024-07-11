package com.example.gitem.ui.main

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.gitem.ui.theme.ManropeFontFamily

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        values().forEach { item ->
            val selected = currentRoute == item.route
            val icon =
                painterResource(id = if (selected) item.selectedIcon else item.unSelectedIcon)
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.label),
                        fontFamily = ManropeFontFamily,
                    )
                }
            )
        }
    }
}