package com.example.gitem.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.gitem.R

sealed class BottomNavItem (val route: String, @DrawableRes val selectedIcon: Int, @DrawableRes val unSelectedIcon: Int, @StringRes val label: Int) {
    data object Home: BottomNavItem("home", R.drawable.ic_home_filled, R.drawable.ic_home, R.string.home)
    data object Repos: BottomNavItem("repositories", R.drawable.ic_search_filled, R.drawable.ic_search, R.string.repositories)
    data object Users: BottomNavItem("users", R.drawable.ic_user_filled, R.drawable.ic_user, R.string.users)
}

fun values() = listOf(
    BottomNavItem.Home,
    BottomNavItem.Repos,
    BottomNavItem.Users
)