package com.martin.auth


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.martin.core.navigation.ScreenRoutes

const val AUTH_GRAPH_ROUTE = "auth_graph"

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(
        route = AUTH_GRAPH_ROUTE,
        startDestination = ScreenRoutes.Login.route
    ) {
        composable(ScreenRoutes.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    com.martin.core.SessionManager.currentAuthState.value = com.martin.core.AuthStates.AUTHORISED
                },
                onSignupClick = {
                    navController.navigate(ScreenRoutes.Signup.route)
                }
            )
        }

        composable(ScreenRoutes.Signup.route) {
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
