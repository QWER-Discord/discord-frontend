package com.example.discord_frontend.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.discord_frontend.navigation.Screen

@Composable
fun ScreenContent(screenName: String, navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "You are on the $screenName", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            when (screenName) {
                Screen.ServerList.label -> navController.navigate(Screen.ChannelList.route.replace("{serverId}", "1"))
                Screen.ChannelList.label -> navController.navigate(Screen.ChatRoom.route.replace("{channelId}", "1"))
                Screen.ChatRoom.label -> navController.navigate(Screen.UserProfile.route.replace("{userId}", "1"))
                Screen.UserProfile.label -> navController.navigate(Screen.Settings.route)
                else -> navController.navigate(Screen.ServerList.route)
            }
        }) {
            Text("Go to Next Screen")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Go Back")
        }
    }
}