package com.martin.core.navigation

sealed class AuthRoutes(val route: String) {
    data object Login : AuthRoutes("login")
    data object Signup : AuthRoutes("signup")
}

sealed class HomeRoutes(val route: String) {
    data object Home : HomeRoutes("home")
    data object Profile : HomeRoutes("profile")
}
