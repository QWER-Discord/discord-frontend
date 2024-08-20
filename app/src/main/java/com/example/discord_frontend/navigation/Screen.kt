package com.example.discord_frontend.navigation

sealed class Screen(val route: String, val label: String) {
    object ServerList : Screen("serverList", "Server List")
    object ChannelList : Screen("channelList/{serverId}", "Channel List")
    object ChatRoom : Screen("chatRoom/{channelId}", "Chat Room")
    object UserProfile : Screen("userProfile/{userId}", "User Profile")
    object Settings : Screen("settings", "Settings")
}