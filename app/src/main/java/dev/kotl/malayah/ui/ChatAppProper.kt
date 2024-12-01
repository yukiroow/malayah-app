package dev.kotl.malayah.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import dev.kotl.malayah.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatAppProper(
    navController: NavController,
    user: String,
    model: ChatUiModel,
    onSend: (String) -> Unit,
    clear: () -> Unit,
) {
    var logoutIsOpen by remember { mutableStateOf(false) }

    if (logoutIsOpen) {
        LogoutDialog(
            onDismissRequest = { logoutIsOpen = false },
            onConfirmation = {
                clear()
                navController.navigate("landing")
            }
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
        }
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val (messages, chatBox) = createRefs()

            val listState = rememberLazyListState()

            LaunchedEffect(model.messages.size) {
                listState.animateScrollToItem(model.messages.size)
            }
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(messages) {
                        top.linkTo(parent.top)
                        bottom.linkTo(chatBox.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        height = Dimension.fillToConstraints
                    },
                contentPadding = PaddingValues(16.dp)
            ) {
                items(model.messages) { item ->
                    ChatBubble(item, user)
                }
            }
            ChatInput(
                onSend,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(chatBox) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
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
fun ChatBubble(message: ChatUiModel.Message, user: String) {
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
                .background(
                    if (message.author == user)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.secondaryContainer
                )
                .padding(16.dp)
        ) {
            Text(text = message.text)
        }
    }
}

@Composable
fun ChatInput(
    onSend: (String) -> Unit,
    modifier: Modifier
) {
    var message by remember { mutableStateOf("") }
    Row(modifier = modifier.padding(16.dp)) {
        TextField(
            value = message,
            onValueChange = { newText ->
                message = newText
            },
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(text = "Share your thoughts")
            }
        )
        IconButton(
            onClick = {
                val msg = message
                if (msg.isBlank()) return@IconButton
                onSend(message)
                message = ""
            },
            modifier = Modifier
                .align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
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

class ChatViewModel : ViewModel() {
    val config = generationConfig { temperature = 0.7f }
    val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash-latest",
            apiKey = "AIzaSyCIcr2dPkfuXtwFJfCRD4iNL3GTlYG0YWE",
            generationConfig = config
        )
    private val chat = generativeModel.startChat(
        history = listOf()
    )
    val conversation: StateFlow<List<ChatUiModel.Message>>
        get() = _conversation
    private val _conversation = MutableStateFlow(
        listOf(ChatUiModel.Message("Greetings. I'm Malayah Bot!", "MalayahBot"))
    )

    fun clear() {
        viewModelScope.launch {
            _conversation.emit(listOf())
        }
    }

    fun send(message: ChatUiModel.Message) {
        viewModelScope.launch {
            _conversation.emit(_conversation.value + message)

            val response = chat.sendMessage("My name is ${message.author}. ${message.text}")
            response.text?.let { modelResponse ->
                _conversation.emit(
                    _conversation.value + ChatUiModel.Message(
                        modelResponse,
                        "Malayah Bot"
                    )
                )
            }
        }
    }
}

data class ChatUiModel(
    val messages: List<Message>,
    val addressee: String,
) {
    data class Message(
        val text: String,
        val author: String,
    )
}