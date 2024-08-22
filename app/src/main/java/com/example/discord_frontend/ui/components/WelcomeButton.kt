package com.example.discord_frontend.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.discord_frontend.ui.theme.DiscordTheme

@Composable
fun WelcomeButton(
    text: String,
    onClick: () -> Unit,
    containerColor: Color,
    textColor: Color
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        shape = RoundedCornerShape(100.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            style = DiscordTheme.typography.button
        )
    }
}