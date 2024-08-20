package com.example.discord_frontend.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.discord_frontend.navigation.Screen

@Composable
fun DiscordApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ServerList.route) {
        composable(Screen.ServerList.route) {
            ScreenContent(Screen.ServerList.label, navController)
        }
        composable(Screen.ChannelList.route) { backStackEntry ->
            val serverId = backStackEntry.arguments?.getString("serverId") ?: "0"
            ScreenContent("${Screen.ChannelList.label} (Server $serverId)", navController)
        }
        composable(Screen.ChatRoom.route) { backStackEntry ->
            val channelId = backStackEntry.arguments?.getString("channelId") ?: "0"
            ScreenContent("${Screen.ChatRoom.label} (Channel $channelId)", navController)
        }
        composable(Screen.UserProfile.route) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: "0"
            ScreenContent("${Screen.UserProfile.label} (User $userId)", navController)
        }
        composable(Screen.Settings.route) {
            ScreenContent(Screen.Settings.label, navController)
        }
    }
}