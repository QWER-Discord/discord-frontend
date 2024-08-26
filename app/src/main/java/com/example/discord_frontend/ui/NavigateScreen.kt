package com.example.discord_frontend.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.discord_frontend.navigation.Screen
import com.example.discord_frontend.ui.screens.auth.login.LoginScreen
import com.example.discord_frontend.ui.screens.auth.WelcomeScreen
import com.example.discord_frontend.ui.screens.auth.signup.CountryCodePicker
import com.example.discord_frontend.ui.screens.auth.signup.SignUpScreen

// 여기서 경로를 관리. 시작 경로를 startDestination 으로 지정.
@Preview
@Composable
fun NavigateScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(navController)
        }

        /* 로그인 루트 */
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        /* 회원가입 루트 */
        composable(Screen.SignUp.route) {
            SignUpScreen(navController)
        }
        composable(Screen.CountryCodePicker.route) {
            CountryCodePicker(navController)
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