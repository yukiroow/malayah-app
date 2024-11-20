package dev.kotl.malayah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.kotl.malayah.ui.theme.AppTheme

/*
 * https://developer.android.com/develop/ui/compose/components
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(
                darkTheme = true
            ) {
                AppController()
            }
        }
    }
}