package com.example.discord_frontend.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.discord_frontend.navigation.Screen
import com.example.discord_frontend.ui.screens.login.LoginScreen
import com.example.discord_frontend.ui.screens.login.SignUpScreen
import com.example.discord_frontend.ui.screens.login.WelcomeScreen

// MainActivity에서 호출하는 함수.
@Composable
fun NavigateScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(navController)
        }
        composable(Screen.ServerList.route) {
            ScreenContent(Screen.ServerList.label, navController)
        }
        // 이하는 이후에 개발하면서 실제 ID로 교체할 예정
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