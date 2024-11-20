package dev.kotl.malayah.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@Composable
fun ChatAppProper(
    navController: NavController,
    user: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable { navController.navigate("landing") }
        ) {
            Text(
                text = "Welcome, ",
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = FontWeight.Thin,
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
            Text(
                text = "$user!",
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )
        }
    }
}
