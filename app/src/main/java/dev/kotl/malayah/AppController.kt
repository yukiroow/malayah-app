package dev.kotl.malayah

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.kotl.malayah.ui.LandingPage
import dev.kotl.malayah.ui.LoginPage
import dev.kotl.malayah.ui.RegisterPage
import dev.kotl.malayah.ui.Routes

@Composable
fun AppController() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Landing.route) {
        composable(Routes.Landing.route) {
            LandingPage(navController)
        }
        composable(Routes.Login.route) {
            LoginPage(navController)
        }
        composable(Routes.Register.route) {
            RegisterPage(navController)
        }
    }
}