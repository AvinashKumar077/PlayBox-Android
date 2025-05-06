package com.martin.home


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.martin.core.navigation.HomeRoutes
import com.martin.home.navigation.BottomNavBar
import com.martin.home.ui.HomeScreen
import com.martin.home.ui.ProfileScreen
import com.martin.home.ui.SubscriptionScreen
import com.martin.home.ui.TweetScreen
import com.martin.home.ui.UploadScreen


@Composable
fun HomeScreenContent() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeRoutes.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(HomeRoutes.Home.route) { HomeScreen() }
            composable(HomeRoutes.Tweet.route) { TweetScreen() }
            composable(HomeRoutes.Upload.route) { UploadScreen() }
            composable(HomeRoutes.Profile.route) { ProfileScreen() }
            composable(HomeRoutes.Subscription.route) { SubscriptionScreen() }
        }
    }
}




