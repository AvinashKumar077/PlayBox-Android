package com.martin.playbox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.martin.auth.authNavGraph
import com.martin.core.AuthStates
import com.martin.core.SessionManager

@Composable
fun PlayBoxApp(){
  val navController = rememberNavController()
  val authState by SessionManager.currentAuthState.observeAsState(AuthStates.DEFAULT)

  NavHost(
    navController = navController,
    startDestination = when (authState) {
      AuthStates.AUTHORISED -> "home_graph"
      AuthStates.UNAUTHORISED -> "auth_graph"
      else -> "auth_graph" // fallback
    }
  ) {
    authNavGraph(navController)
//    homeNavGraph(navController)
  }
}