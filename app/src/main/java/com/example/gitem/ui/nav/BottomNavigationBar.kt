package com.example.gitem.ui.nav

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.gitem.R
import com.example.gitem.ui.theme.ManropeFontFamily

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        NavigationBarItem(
            selected = currentRoute == LandingPageDestination::class.qualifiedName,
            onClick = {
                navController.navigate(LandingPageDestination) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                val selected = currentRoute == LandingPageDestination::class.qualifiedName
                val icon = if (selected) R.drawable.ic_home_filled else R.drawable.ic_home
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = stringResource(id = R.string.home),
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.home),
                    fontFamily = ManropeFontFamily,
                )
            }
        )

        NavigationBarItem(
            selected = currentRoute == ReposDestination::class.qualifiedName,
            onClick = {
                navController.navigate(ReposDestination) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                val selected = currentRoute == ReposDestination::class.qualifiedName
                val icon = if (selected) R.drawable.ic_search_filled else R.drawable.ic_search
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = stringResource(id = R.string.repositories),
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.repositories),
                    fontFamily = ManropeFontFamily,
                )
            }
        )

        NavigationBarItem(
            selected = currentRoute == UsersDestination::class.qualifiedName,
            onClick = {
                navController.navigate(UsersDestination) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                val selected = currentRoute in listOf(UsersDestination::class.qualifiedName, UserDetailsDestination::class.qualifiedName)
                val icon = if (selected) R.drawable.ic_user_filled else R.drawable.ic_user
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = stringResource(id = R.string.users),
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.users),
                    fontFamily = ManropeFontFamily,
                )
            }
        )
    }
}