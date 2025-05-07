package com.martin.features.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.martin.core.navigation.HomeRoutes
import com.martin.features.home.HomeScreen
import com.martin.features.profile.ProfileScreen
import com.martin.features.subscription.SubscriptionScreen
import com.martin.features.tweet.TweetScreen
import com.martin.features.upload.UploadScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun BottomNavigationComponent() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) },
        modifier = Modifier,
        contentColor = Color.White
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeRoutes.Home.route,
            modifier = Modifier
        ) {
            composable(HomeRoutes.Home.route) { HomeScreen(innerPadding) }
            composable(HomeRoutes.Tweet.route) { TweetScreen() }
            composable(HomeRoutes.Upload.route) { UploadScreen() }
            composable(HomeRoutes.Profile.route) { ProfileScreen() }
            composable(HomeRoutes.Subscription.route) { SubscriptionScreen() }
        }
    }
}