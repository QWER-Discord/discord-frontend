package com.example.discord_frontend.navigation

// 화면 각 부분을 정의. route : 네비게이션 경로 / label : 화면 이름
sealed class Screen(val route: String, val label: String) {
    object Welcome : Screen("welcome", "Welcome")
    object Login : Screen("login", "Login")
    object SignUp : Screen("signup", "Sign Up")
    object ServerList : Screen("serverList", "Server List")
    object ChannelList : Screen("channelList/{serverId}", "Channel List")
    object ChatRoom : Screen("chatRoom/{channelId}", "Chat Room")
    object UserProfile : Screen("userProfile/{userId}", "User Profile")
    object Settings : Screen("settings", "Settings")
}