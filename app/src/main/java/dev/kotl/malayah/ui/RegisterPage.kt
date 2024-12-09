package dev.kotl.malayah.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import dev.kotl.malayah.User
import dev.kotl.malayah.users

@Composable
fun RegisterPage(navController: NavController) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(18.dp)
    ) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
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
            RegisterUsernameField(username, onUsernameChange = { newUsername ->
                if(newUsername.length <= 15) {
                    username = newUsername.replace(Regex("\\s"), "")
                }
            })
            Spacer(modifier = Modifier.height(12.dp))
            RegisterEmailField(email, onEmailChange = { newEmail ->
                if(newEmail.length <= 30) {
                    email = newEmail.replace(Regex("\\s"), "")
                }
            })
            Spacer(modifier = Modifier.height(12.dp))
            RegisterPasswordTextField(password, onPasswordChange = { newPassword ->
                if(newPassword.length <= 20) {
                    password = newPassword.replace(Regex("\\s"), "")
                }
            })
            Spacer(modifier = Modifier.height(12.dp))
            RegisterConfirmPasswordTextField(
                confirmPassword,
                onConfirmPasswordChange = { newConfirmPassword ->
                    if(newConfirmPassword.length <= 20) {
                        confirmPassword = newConfirmPassword.replace(Regex("\\s"), "")
                    }
                })
            Spacer(modifier = Modifier.height(12.dp))
            RegisterSubmitButton(navController, username, password, email, confirmPassword)
            LoginNowButton(navController)
        }
    }
}

@Composable
fun RegisterUsernameField(username: String, onUsernameChange: (String) -> Unit) {
    TextField(
        value = username,
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
fun RegisterEmailField(email: String, onEmailChange: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = onEmailChange,
        placeholder = { Text("Enter email") },
        label = { Text("Email") },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun RegisterPasswordTextField(password: String, onPasswordChange: (String) -> Unit) {
    TextField(
        value = password,
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
fun RegisterConfirmPasswordTextField(
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit
) {
    TextField(
        value = confirmPassword,
        onValueChange = onConfirmPasswordChange,
        placeholder = { Text(text = "Re-enter your password") },
        label = { Text(text = "Confirm Password") },
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
fun RegisterSubmitButton(
    navController: NavController,
    username: String,
    password: String,
    email: String,
    confirmPassword: String
) {
    var errorMessage by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
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
                onClick = {
            var success = false;
            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || confirmPassword.isEmpty()) {
                errorMessage = "Please complete all the fields."
                showError = true
            } else if (password != confirmPassword) {
                errorMessage = "Passwords do not match."
                showError = true
            } else if (username.length < 4) {
                errorMessage = "Username must be at least 4 characters"
                showError = true
            } else if (!Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$").matches(email)) {
                errorMessage = "Please enter a valid email address"
                showError = true
            } else if (password.length < 8) {
                errorMessage = "Password must be at least 8 characters"
                showError = true
            } else {
                users.register(
                    User(username = username, password = password, email = email),
                    onSuccess = { _, code ->
                        run {
                            if (code == 200) {
                                success = true
                                showError = false
                                navController.navigate("login")
                            } else {
                                success = true
                                errorMessage = "Please try again"
                                showError = true
                            }
                        }
                    },
                    onFailure = { err ->
                        run {
                            success = false
                            errorMessage = "Please try again"
                            showError = true
                        }
                    }
                )
                if (!success) {
                    errorMessage = "Please try again"
                    showError = true
                }
            }
        }

        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Text(text = "Register")
    }
}

@Composable
fun LoginNowButton(navController: NavController) {
    TextButton(
        onClick = { navController.navigate(Routes.Login.route) },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Text(text = "Already have an account? Login now.")
    }
}
