package com.example.discord_frontend.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    var loginMethod by remember { mutableStateOf(LoginMethod.EMAIL) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Login method selection
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { loginMethod = LoginMethod.EMAIL },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (loginMethod == LoginMethod.EMAIL) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Email/Phone")
            }
            Button(
                onClick = { loginMethod = LoginMethod.GITHUB },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (loginMethod == LoginMethod.GITHUB) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("GitHub")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Login form
        when (loginMethod) {
            LoginMethod.EMAIL -> EmailPhoneLoginForm(navController)
            LoginMethod.GITHUB -> GithubLoginButton(navController)
        }
    }
}

enum class LoginMethod { EMAIL, GITHUB }

@Composable
fun EmailPhoneLoginForm(navController: NavController) {
    var identifier by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    OutlinedTextField(
        value = identifier,
        onValueChange = { identifier = it },
        label = { Text("Email or Phone Number") },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(32.dp))
    Button(
        onClick = { /* Implement login logic */ },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Login")
    }
}

@Composable
fun GithubLoginButton(navController: NavController) {
    Button(
        onClick = { /* Implement GitHub OAuth login */ },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Login with GitHub")
    }
}