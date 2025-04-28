package com.martin.core.navigation

sealed class ScreenRoutes(val route: String) {
    data object Login : ScreenRoutes("login")
    data object Signup : ScreenRoutes("signup")
}