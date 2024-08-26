package com.example.discord_frontend.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.discord_frontend.R
import com.example.discord_frontend.ui.theme.DiscordTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EnhancedPasswordInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "비밀번호 입력",
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var lastChar by remember { mutableStateOf<Char?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val visualTransformation = if (passwordVisible) {
        VisualTransformation.None
    } else {
        VisualTransformation { text ->
            val transformed = buildString {
                text.forEach { char ->
                    append(if (char == lastChar) char else '•')
                }
            }
            TransformedText(AnnotatedString(transformed), OffsetMapping.Identity)
        }
    }

    TextField(
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
            if (newValue.length > value.length) {
                lastChar = newValue.last()
                coroutineScope.launch {
                    delay(100)
                    lastChar = null
                }
            }
        },
        placeholder = { Text(placeholder) },
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = modifier
            .fillMaxWidth()
            .height(53.dp)
            .border(DiscordTheme.border.width, DiscordTheme.border.color, DiscordTheme.border.shape),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    painter = painterResource(
                        id = if (passwordVisible) R.drawable.password_check else R.drawable.password_check
                    ),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = DiscordTheme.colors.inputFieldBackground,
            unfocusedContainerColor = DiscordTheme.colors.inputFieldBackground,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
@Preview
fun EnhancedPasswordInputFieldPreview() {
    var password by remember { mutableStateOf("") }
    DiscordTheme {
        EnhancedPasswordInputField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Enter password"
        )
    }
}