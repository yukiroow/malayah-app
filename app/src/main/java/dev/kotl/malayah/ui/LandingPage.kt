package dev.kotl.malayah.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.kotl.malayah.R
import dev.kotl.malayah.Routes


@Composable
fun LandingPage(navController: NavController) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        MalayahBanner()
        Spacer(modifier = Modifier.height(100.dp))
        Column(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(330.dp)
                .padding(16.dp)
        ) {
            LoginButton(navController)
            Spacer(modifier = Modifier.height(10.dp))
            RegisterButton(navController)
            GuestButton()
        }
    }
}

@Composable
fun MalayahBanner() {
    Image(
        painter = painterResource(id = R.drawable.home_banner),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .size(300.dp),
    )
}

@Composable
fun LoginButton(navController: NavController) {
    Button(
        onClick = { navController.navigate(Routes.Login.route) },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Text(text = "Login")
    }
}

@Composable
fun RegisterButton(navController: NavController) {
    FilledTonalButton(
        onClick = { navController.navigate(Routes.Register.route) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Register")
    }
}

@Composable
fun GuestButton() {
    TextButton(
        onClick = { /*TODO*/ },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Text(text = "Continue as Guest")
    }
}