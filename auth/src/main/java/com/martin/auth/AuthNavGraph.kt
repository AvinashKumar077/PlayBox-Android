package com.martin.auth


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.martin.core.navigation.AuthRoutes
import com.martin.core.navigation.Graph


fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(
        route = Graph.AUTH,
        startDestination = AuthRoutes.Login.route
    ) {
        composable(AuthRoutes.Login.route) {
            LoginScreen(
                onSignupClick = {
                    navController.navigate(AuthRoutes.Signup.route)
                }
            )
        }

        composable(AuthRoutes.Signup.route) {
            SignupScreen(
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }
    }
}
