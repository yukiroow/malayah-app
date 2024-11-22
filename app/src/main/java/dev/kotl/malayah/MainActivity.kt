package dev.kotl.malayah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dev.kotl.malayah.ui.ChatViewModel
import dev.kotl.malayah.ui.theme.AppTheme

/*
 * https://developer.android.com/develop/ui/compose/components
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(
                darkTheme = false
            ) {
                val viewModel: ChatViewModel by viewModels()
                users = Users()
                AppController(viewModel)
            }
        }
    }
}