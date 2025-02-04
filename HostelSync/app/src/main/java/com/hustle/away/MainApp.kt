package com.hustle.away

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.hustle.away.auth.SignInScreen
import com.hustle.away.auth.SignUpScreen
import com.hustle.away.screens.HomeScreen
import com.hustle.away.screens.PaymentScreen
import com.hustle.away.screens.SupportScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Home : Screen("home")
    object ForgotPassword : Screen("forgotPassword")
    object Payment : Screen("payment")
    object Support: Screen("support")
}
@Composable
fun MainApp() {
    Surface(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController() // Create the navController here

        val currentUser = FirebaseAuth.getInstance().currentUser
        val startDestination = if (currentUser != null) Screen.Home.route else Screen.Login.route

        NavHost(navController = navController, startDestination = startDestination) {
            composable(Screen.Login.route) {
                SignInScreen(
                    onSignInClick = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
//                    onSignUpClick = { navController.navigate(Screen.SignUp.route) },
                    onForgotPasswordClick = { navController.navigate(Screen.ForgotPassword.route) }
                )
            }

            composable(Screen.SignUp.route) {
                SignUpScreen(
                    onSignUpClick = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                    onSignInClick = { navController.navigate(Screen.Login.route) }
                )
            }
            composable(Screen.Payment.route) {
                PaymentScreen(navController = navController)
            }
            composable(Screen.Home.route) {
                HomeScreen(navController = navController) // Pass navController here
            }
            composable(Screen.Support.route) {
                SupportScreen(navController = navController)
            }
        }
    }
}
