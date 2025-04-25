package com.martin.auth


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

const val AUTH_GRAPH_ROUTE = "auth_graph"

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(
        route = AUTH_GRAPH_ROUTE,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    // Update session state
                    com.martin.core.SessionManager.currentAuthState.value = com.martin.core.AuthStates.AUTHORISED
                },
                onSignupClick = {
                    navController.navigate("signup")
                }
            )
        }

        composable("signup") {
            SignupScreen(
                onSignupSuccess = {
                    com.martin.core.SessionManager.currentAuthState.value = com.martin.core.AuthStates.AUTHORISED
                },
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }
    }
}
