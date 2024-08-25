package com.example.discord_frontend.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.discord_frontend.R
import com.example.discord_frontend.ui.components.WelcomeButton
import com.example.discord_frontend.ui.theme.DiscordTheme

@Composable
fun WelcomeScreen(navController: NavController) {
    DiscordTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds
                )
                .background(color = DiscordTheme.colors.backgroundOverlay)
        ) {
            WelcomeLogo()
            WelcomeTitle()
            Spacer(modifier = Modifier.padding(top = 50.dp))
            WelcomeButtons(navController)
        }
    }
}

@Composable
private fun WelcomeLogo() {
    Image(
        painter = painterResource(id = R.drawable.welcome_logo),
        contentDescription = stringResource(R.string.welcome_logo_description),
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .padding(top = DiscordTheme.spacing.large)
            .size(width = 88.dp, height = 67.dp)
    )
}

@Composable
private fun WelcomeTitle() {
    Text(
        text = stringResource(R.string.welcome_title),
        style = DiscordTheme.typography.h1,
        modifier = Modifier.padding(DiscordTheme.spacing.medium)
    )
}

@Composable
private fun WelcomeButtons(navController: NavController) {
    Column(
        verticalArrangement = Arrangement.spacedBy(DiscordTheme.spacing.medium),
        modifier = Modifier.padding(horizontal = DiscordTheme.spacing.medium)
    ) {
        WelcomeButton(
            text = stringResource(R.string.sign_up),
            textColor = Color.White,
            onClick = { navController.navigate("signup")},
            containerColor = DiscordTheme.colors.primary
        )
        WelcomeButton(
            text = stringResource(R.string.log_in),
            textColor = Color.Black,
            onClick = { navController.navigate("login") },
            containerColor = DiscordTheme.colors.secondary
        )
        WelcomeButton(
            text = stringResource(R.string.continue_with_github),
            textColor = Color.Black,
            onClick = { navController.navigate("loginViaGithub") },
            containerColor = DiscordTheme.colors.secondary
        )
    }
}