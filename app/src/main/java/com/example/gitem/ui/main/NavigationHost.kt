package com.example.gitem.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gitem.ui.landing.LandingPage
import com.example.gitem.ui.repositories.SearchRepositories
import com.example.gitem.ui.usersearch.SearchUsers

@Composable
fun NavigationHost(modifier: Modifier, navController: NavHostController) {
    NavHost(modifier = modifier, navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route){ LandingPage(modifier) }
        composable(BottomNavItem.Repos.route){ SearchRepositories(modifier) }
        composable(BottomNavItem.Users.route){ SearchUsers(modifier) }
    }
}