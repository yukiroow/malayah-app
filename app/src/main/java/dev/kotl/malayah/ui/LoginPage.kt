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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.kotl.malayah.R

@Composable
fun LoginPage() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
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
                .background(Color.White)
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            UsernameField()
            Spacer(modifier = Modifier.height(16.dp))
            PasswordTextField()
            Row( modifier = Modifier.align(Alignment.End) ) { ForgotPasswordButton() }
            Spacer(modifier = Modifier.height(64.dp))
            SubmitButton()
            SignUpNowButton()
        }
    }
}

@Composable
fun UsernameField() {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        placeholder = { Text("Enter username") },
        label = { Text("Username") },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

@Composable
fun PasswordTextField() {
    var password by remember { mutableStateOf("") }

    TextField(
        value = password,
        onValueChange = {
            password = it
        },
        placeholder = { Text(text = "Enter password") },
        label = { Text(text = "Password") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

@Composable
fun ForgotPasswordButton() {
    TextButton(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.secondary
        ),
    ) {
        Text(text = "Forgot Password?")
    }
}

@Composable
fun SubmitButton() {
    Button(
        onClick = { /*TODO*/ },
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
fun SignUpNowButton() {
    TextButton(
        onClick = { /*TODO*/ },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Text(text = "Not a member yet? Sign up now.")
    }
}