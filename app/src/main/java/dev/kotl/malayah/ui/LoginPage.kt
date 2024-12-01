package dev.kotl.malayah.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.kotl.malayah.R
import dev.kotl.malayah.Routes
import dev.kotl.malayah.users
import kotlinx.coroutines.launch

@Composable
fun LoginPage(navController: NavController) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(18.dp)
    ) {
        var credentials by remember { mutableStateOf(LoginCredentials()) }
        Spacer(modifier = Modifier.height(32.dp))
        Image(
            painter = painterResource(id = R.drawable.home_banner),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .size(240.dp),
        )
        Spacer(modifier = Modifier.height(32.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            LoginUsernameField(credentials) { newUsername ->
                credentials = credentials.copy(username = newUsername)
            }
            Spacer(modifier = Modifier.height(16.dp))
            LoginPasswordTextField(credentials) { newPassword ->
                credentials = credentials.copy(password = newPassword)
            }
            Row(modifier = Modifier.align(Alignment.End)) { ForgotPasswordButton() }
            Spacer(modifier = Modifier.height(64.dp))
            LoginSubmitButton(navController, credentials)
            SignUpNowButton(navController)
        }
    }
}

@Composable
fun LoginUsernameField(credentials: LoginCredentials, onUsernameChange: (String) -> Unit) {
    TextField(
        value = credentials.username,
        onValueChange = onUsernameChange,
        placeholder = { Text("Enter username") },
        label = { Text("Username") },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun LoginPasswordTextField(credentials: LoginCredentials, onPasswordChange: (String) -> Unit) {
    TextField(
        value = credentials.password,
        onValueChange = onPasswordChange,
        placeholder = { Text(text = "Enter password") },
        label = { Text(text = "Password") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background
        ),
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun ForgotPasswordButton() {
    TextButton(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.secondary
        ),
        enabled = false
    ) {
        Text(text = "Forgot Password?")
    }
}

@Composable
fun LoginSubmitButton(
    navController: NavController,
    credentials: LoginCredentials
) {
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    if (showError) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 8.dp
            )
        )
    }
    Button(
        onClick = {
            if (credentials.isEmpty()) {
                errorMessage = "Please fill in all the fields"
                showError = true
            }
            users.validate(credentials.username, credentials.password,
                onSuccess = { response ->
                    run {
                        if(response.status == "success") {
                            showError = false
                            navController.navigate("chat/${credentials.username}")
                        }
                    }
                },
                onFailure = { err ->
                    run {
                        println(err)
                        errorMessage = "Please try again"
                        showError = true
                    }
                }
            )
        },
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
fun SignUpNowButton(navController: NavController) {
    TextButton(
        onClick = { navController.navigate(Routes.Register.route) },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Text(text = "Not a member yet? Sign up now.")
    }
}

data class LoginCredentials(
    var username: String = "",
    var password: String = ""
) {
    fun isEmpty(): Boolean {
        return username.isEmpty() && password.isEmpty()
    }
}