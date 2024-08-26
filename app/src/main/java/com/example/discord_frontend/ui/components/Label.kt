package com.example.discord_frontend.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.discord_frontend.ui.theme.AppTypography
import com.example.discord_frontend.ui.theme.DiscordTheme

@Composable
fun SubtitleLabel(
    text: String,
    isRequired: Boolean = false,
    modifier: Modifier = Modifier
) {
    val labelText = if (isRequired) "$text *" else text

    Text(
        text = labelText,
        style = DiscordTheme.typography.subtitleText,
        color = DiscordTheme.colors.subtitleText,
    )
}


@Composable
fun CustomInputDescription(
    text: String,
    modifier: Modifier = Modifier
)   {

    Text(
        text = text,
        style = AppTypography.labelSmall,
        color = DiscordTheme.colors.subtitleText.copy(0.8f),
    )
}
