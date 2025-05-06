package com.martin.core.navigation

import androidx.annotation.DrawableRes
import com.martin.core.R

sealed class AuthRoutes(val route: String) {
    data object Login : AuthRoutes("login")
    data object Signup : AuthRoutes("signup")
}

sealed class HomeRoutes(val route: String, val label: String,  @DrawableRes val iconRes: Int) {
    data object Home : HomeRoutes("home", "Home", R.drawable.home)
    data object Tweet : HomeRoutes("tweet", "Tweet", R.drawable.home)
    data object Upload : HomeRoutes("upload", "Upload", R.drawable.home)
    data object Profile : HomeRoutes("profile", "Profile", R.drawable.home)
    data object Subscription : HomeRoutes("subscription", "Subscription", R.drawable.home)

    companion object {
        val bottomNavItems = listOf(Home, Tweet, Upload, Profile, Subscription)
    }
}


