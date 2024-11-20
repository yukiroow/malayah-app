package dev.kotl.malayah.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ChatAppProper(
    navController: NavController,
    user: String
) {
    Text(
        text = "Hello $user",
    )
}
