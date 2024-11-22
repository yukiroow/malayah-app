package dev.kotl.malayah.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.kotl.malayah.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatAppProper(
    navController: NavController,
    user: String
) {
    var logoutIsOpen by remember { mutableStateOf(false) }

    if (logoutIsOpen) {
        LogoutDialog(
            onDismissRequest = { logoutIsOpen = false },
            onConfirmation = { navController.navigate("landing") }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.logo_with_title),
                        contentDescription = null,
                        modifier = Modifier
                            .width(200.dp)
                            .height(200.dp),
                    )
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    IconButton(onClick = { logoutIsOpen = true }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {

        }
    ) { innerPadding ->
        ChatGreeting(user, innerPadding)
    }
}

@Composable
fun ChatGreeting(user: String, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
    ) {
        Text(
            text = "HELLO, ${user.uppercase()}",
            fontWeight = FontWeight.W900,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            color = Color.DarkGray,
            modifier = Modifier.absolutePadding(
                left = 16.dp,
                right = 16.dp,
                top = 16.dp,
                bottom = 5.dp
            )
        )
        Text(
            text = "How can I help you today?",
            fontWeight = FontWeight.Thin,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = Color.DarkGray,
            modifier = Modifier.absolutePadding(
                left = 16.dp,
                right = 16.dp,
                top = 0.dp,
                bottom = 16.dp
            )
        )
    }
}

@Composable
fun ChatBubble(message: Message, user: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .align(if (message.author == user) Alignment.End else Alignment.Start)
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (message.author == user) 48f else 0f,
                        bottomEnd = if (message.author == user) 0f else 48f
                    )
                )
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            Text(text = message.text)
        }
    }
}

@Composable
fun LogoutDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = null
            )
        },
        title = {
            Text(text = "Logout")
        },
        text = {
            Text(text = "Are your sure you want to logout?")
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

data class Message(
    val text: String,
    val author: String,
)