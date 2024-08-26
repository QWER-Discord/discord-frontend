package com.example.discord_frontend.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.discord_frontend.R
import com.example.discord_frontend.ui.theme.DiscordTheme
import kotlin.reflect.KFunction1


@Composable
fun ImageButton(
    imageResId: Int,
    onClick: () -> Unit,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp)
        )
    }
}


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

@Composable
fun NextButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier,

    ) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            DiscordTheme.colors.buttonColor,
        ),
        shape = RoundedCornerShape(100.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(color = Color(0xFF5865F2), shape = RoundedCornerShape(size = 5.dp))
    ) {
        Text(
            text = text,
            color = Color.White,
            style = DiscordTheme.typography.button
        )
    }
}

@Composable
fun BackButton(modifier: Modifier, onClick: () -> Unit) {
    ImageButton(
        imageResId = R.drawable.back_arrow,
        onClick = onClick,
        contentDescription = "Back"
    )
}


