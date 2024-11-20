package dev.kotl.malayah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/*
 * https://developer.android.com/develop/ui/compose/components
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                Spacer(modifier = Modifier.height(100.dp))
                MalayahBanner()
                Spacer(modifier = Modifier.height(100.dp))
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(330.dp)
                        .padding(16.dp)
                ) {
                    LoginButton()
                    Spacer(modifier = Modifier.height(10.dp))
                    RegisterButton()
                    GuestButton()
                }
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
    fun LoginButton() {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
    }

    @Composable
    fun RegisterButton() {
        FilledTonalButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Register")
        }
    }

    @Composable
    fun GuestButton() {
        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Continue as Guest")
        }
    }
}