package com.martin.playbox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.martin.auth.authNavGraph
import com.martin.core.AuthStates
import com.martin.core.SessionManager
import com.martin.core.helper.getPopSlideFadeOutTransition
import com.martin.core.helper.getPopSlideFadeTransition
import com.martin.core.helper.getSlideFadeOutTransition
import com.martin.core.helper.getSlideFadeTransition
import com.martin.core.navigation.Graph
import com.martin.features.navigation.homeNavGraph

@Composable
fun PlayBoxApp() {
    val navController = rememberNavController()
    val authState by SessionManager.currentAuthState.observeAsState(AuthStates.DEFAULT)

    NavHost(
        navController = navController,
        startDestination = when (authState) {
            AuthStates.AUTHORISED -> Graph.HOME
            AuthStates.UNAUTHORISED -> Graph.AUTH
            else -> Graph.AUTH // fallback
        },
        enterTransition = { getSlideFadeTransition() },
        exitTransition = { getSlideFadeOutTransition() },
        popEnterTransition = { getPopSlideFadeTransition() },
        popExitTransition = { getPopSlideFadeOutTransition() }
    ) {
        authNavGraph(navController)
        homeNavGraph(navController)
    }
}


