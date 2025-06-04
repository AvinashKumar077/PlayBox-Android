package com.martin.features.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.martin.core.navigation.Graph
import com.martin.core.navigation.HomeRoutes
import com.martin.core.utils.MoshiEntityConverter
import com.martin.features.home.playerscreen.VideoPlayerScreen
import java.net.URLDecoder

@RequiresApi(Build.VERSION_CODES.S)
fun NavGraphBuilder.homeNavGraph(navController: NavController) {
    composable(Graph.HOME) {
        BottomNavigationComponent(parentNavController = navController)
    }
    composable(
        route = "${HomeRoutes.VideoPlayer.route}/{videoJson}",
        arguments = listOf(
            navArgument("videoJson") {
                nullable = false
            }
        )
    ) { backStackEntry ->
        val json = backStackEntry.arguments?.getString("videoJson")
        val videoModel = MoshiEntityConverter.fromJson(json ?: "")
        if(videoModel!=null){
            VideoPlayerScreen(video = videoModel)
        }
    }

}

