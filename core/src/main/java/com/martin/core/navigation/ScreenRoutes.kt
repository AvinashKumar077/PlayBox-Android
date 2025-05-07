package com.martin.core.navigation

import androidx.annotation.DrawableRes
import com.martin.core.R

sealed class AuthRoutes(val route: String) {
    data object Login : AuthRoutes("login")
    data object Signup : AuthRoutes("signup")
}

sealed class HomeRoutes(val route: String, val label: String,  @DrawableRes val iconUnSelected: Int, @DrawableRes val iconSelected: Int) {
    data object Home : HomeRoutes("home", "Home", R.drawable.home, R.drawable.homefilled)
    data object Tweet : HomeRoutes("tweet", "Tweet", R.drawable.twitter, R.drawable.twitterfilled)
    data object Upload : HomeRoutes("upload", "Upload", R.drawable.plus, R.drawable.plus)
    data object Subscription : HomeRoutes("subscription", "Subscription", R.drawable.subscribe, R.drawable.subscribefilled)
    data object Profile : HomeRoutes("profile", "Profile", R.drawable.home, R.drawable.home)

    companion object {
        val bottomNavItems = listOf(Home, Tweet, Upload, Subscription,Profile)
    }
}


