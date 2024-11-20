package dev.kotl.malayah

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.kotl.malayah.ui.ChatAppProper
import dev.kotl.malayah.ui.LandingPage
import dev.kotl.malayah.ui.LoginPage
import dev.kotl.malayah.ui.RegisterPage


lateinit var users: Users

sealed class Routes(val route: String) {
    object Landing : Routes("landing")
    object Login : Routes("login")
    object Register : Routes("register")
}

class Users {
    val loggedUsers: MutableList<User> = mutableListOf()

    init {
        loggedUsers.add(
            User("yukiro", "123qweasd", "yukiro@mail.co")
        )
    }

    fun register(user: User) {
        println("""
            New User Added!
            Username: ${user.username}
            Email: ${user.email}
        """.trimIndent())
        loggedUsers.add(user)
    }

    fun validate(username: String, password: String): Boolean {
        println("""
            New User Login!
            Username: $username
        """.trimIndent())
        return loggedUsers.any { it.username == username && it.password == password }
    }
}

class User(
    val username: String,
    val password: String,
    val email: String
)

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
        composable(
            route = "chat/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")
            ChatAppProper(navController, username!!)
        }
    }
}