package com.martin.features.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.martin.core.navigation.Graph

fun NavGraphBuilder.homeNavGraph(navController: NavController) {
    composable(Graph.HOME) {
        BottomNavigationComponent()
    }
}

