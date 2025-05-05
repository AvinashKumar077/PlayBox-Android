package com.martin.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.martin.core.navigation.Graph
import com.martin.core.navigation.HomeRoutes

fun NavGraphBuilder.homeNavGraph(navController: NavController) {
    navigation(
        startDestination = HomeRoutes.Home.route,
        route = Graph.HOME
    )
    {
        composable(HomeRoutes.Home.route) {
            HomeScreen(navController)
        }
        composable(HomeRoutes.Profile.route) {
            ProfileScreen(navController)
        }
    }
}
