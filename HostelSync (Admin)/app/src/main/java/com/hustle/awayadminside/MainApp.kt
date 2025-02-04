package com.hustle.awayadminside

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.hustle.awayadminside.auth.SignInScreen
import com.hustle.awayadminside.auth.SignUpScreen
import com.hustle.awayadminside.auth.SignUpState
import com.hustle.awayadminside.screens.CreateUser
import com.hustle.awayadminside.screens.HomeScreen
import com.hustle.awayadminside.screens.Notice
import com.hustle.awayadminside.viewmodels.NoticeViewModel

@Composable
fun MainApp(){
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
                    onSignUpClick = { navController.navigate(Screen.SignUp.route) }
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
            composable(Screen.Home.route) {
                HomeScreen(navController = navController) // Pass navController here
            }
            composable(Screen.CreateUser.route){
                CreateUser(
                    navController = navController,
                    viewModel = viewModel(),
//                    onSignUpClick = SignUpState.Success
                )
            }
            composable(Screen.Notice.route){
                Notice(navController = navController,vm= NoticeViewModel())
            }
        }
    }
}
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Home : Screen("home")
    object Notice : Screen("notice")
    object CreateUser : Screen("createUser")
}