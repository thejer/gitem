package com.example.gitem.ui.nav

import kotlinx.serialization.Serializable

@Serializable
object LandingPageDestination

@Serializable
object ReposDestination

@Serializable
object UsersDestination

@Serializable
data class UserDetailsDestination (
    val userId: Int
)