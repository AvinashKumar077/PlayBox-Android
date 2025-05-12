package com.martin.features.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.martin.core.navigation.Graph
import com.martin.core.navigation.HomeRoutes
import com.martin.features.home.VideoPlayerScreen
import java.net.URLDecoder

@RequiresApi(Build.VERSION_CODES.S)
fun NavGraphBuilder.homeNavGraph(navController: NavController) {
    composable(Graph.HOME) {
        BottomNavigationComponent(parentNavController = navController)
    }
    composable(HomeRoutes.VideoPlayer.route) { backStackEntry ->
        val rawUrl = backStackEntry.arguments?.getString("videoUrl") ?: return@composable
        val decodedUrl = URLDecoder.decode(rawUrl, "UTF-8")
        VideoPlayerScreen(decodedUrl)
    }
}

