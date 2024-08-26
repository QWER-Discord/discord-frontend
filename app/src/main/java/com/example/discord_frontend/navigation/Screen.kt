package com.example.discord_frontend.navigation

// 화면 각 부분을 정의. route : 네비게이션 경로 / label : 화면 이름
sealed class Screen(val route: String, val label: String) {
    data object Welcome : Screen("welcome", "Welcome")
    data object Login : Screen("login", "Login")
    data object SignUp : Screen("signup", "Sign Up")
    data object CountryCodePicker : Screen("CountryCodePicker", "Country Code Selection")
    data object LoginViaGithub : Screen("loginViaGithub", "LoginViaGithub")
    data object ServerList : Screen("serverList", "Server List")
    data object ChannelList : Screen("channelList/{serverId}", "Channel List")
    data object ChatRoom : Screen("chatRoom/{channelId}", "Chat Room")
    data object UserProfile : Screen("userProfile/{userId}", "User Profile")
    data object Settings : Screen("settings", "Settings")
}

