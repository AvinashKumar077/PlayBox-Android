package com.martin.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.martin.core.navigation.Graph
import com.martin.home.HomeScreenContent

fun NavGraphBuilder.homeNavGraph(navController: NavController) {
    composable(Graph.HOME) {
        HomeScreenContent()
    }
}

