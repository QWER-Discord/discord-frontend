package com.example.discord_frontend.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.discord_frontend.ui.theme.AppTypography
import com.example.discord_frontend.ui.theme.DiscordTheme

@Composable
fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier,
    showBorder: Boolean = true,
) {
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    val borderColor = DiscordTheme.colors.unSelectedTextColor.copy(0.35f)

    val borderModifier = if (showBorder) {
        Modifier.border(
            width = 1.dp,
            color = borderColor,
            shape = RoundedCornerShape(8.dp)
        )
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .then(borderModifier)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                if (!isFocused && value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = AppTypography.bodyMedium.copy(
                            color = DiscordTheme.colors.unSelectedTextColor
                        )
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(53.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { isFocused = it.isFocused },
            singleLine = true,
            keyboardOptions = keyboardOptions.copy(keyboardType = keyboardType),
            colors = TextFieldDefaults.colors(
                focusedTextColor = DiscordTheme.colors.textPrimary.copy(0.7f),
                unfocusedTextColor = DiscordTheme.colors.unSelectedTextColor,
                focusedContainerColor = DiscordTheme.colors.inputFieldBackground,
                unfocusedContainerColor = DiscordTheme.colors.inputFieldBackground,
                focusedPlaceholderColor = Color.Transparent,
                unfocusedPlaceholderColor = DiscordTheme.colors.unSelectedTextColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = DiscordTheme.colors.selectedTextColor
            ),
            textStyle = AppTypography.bodyLarge.copy(fontWeight = FontWeight.Light)
        )
    }
}