package com.example.gitem.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gitem.ui.landing.LandingPage
import com.example.gitem.ui.repositories.SearchRepositories
import com.example.gitem.ui.userdetails.UserDetails
import com.example.gitem.ui.usersearch.SearchUsers

@Composable
fun NavigationHost(modifier: Modifier, navController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = LandingPageDestination
    ) {
        composable<LandingPageDestination> {
            LandingPage(
                modifier = modifier,
                onReposClicked = { navController.navigate(ReposDestination){
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                } },
                onUsersClicked = { navController.navigate(UsersDestination){
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                } }
            )
        }
        composable<ReposDestination> { SearchRepositories(modifier) }
        composable<UsersDestination> {
            SearchUsers(
                modifier = modifier,
                onUserClicked = { navController.navigate(UserDetailsDestination(it)) }
            )
        }
        composable<UserDetailsDestination> {
            UserDetails(modifier = modifier, onBackPressed = navController::navigateUp)
        }
    }
}