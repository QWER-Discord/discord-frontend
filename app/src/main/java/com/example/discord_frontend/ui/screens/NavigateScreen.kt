package com.example.discord_frontend.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.discord_frontend.navigation.Screen
import com.example.discord_frontend.ui.screens.*
import com.example.discord_frontend.ui.screens.auth.WelcomeScreen
import com.example.discord_frontend.ui.screens.auth.login.LoginScreen
import com.example.discord_frontend.ui.screens.auth.signup.SignUpScreen

@Composable
fun DiscordNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Auth.Welcome.route) {
        // Auth screens
        composable(Screen.Auth.Welcome.route) {
            WelcomeScreen(navController)
        }
        composable(Screen.Auth.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Auth.SignUp.route) {
            SignUpScreen(navController)
        }

        /*
        composable(Screen.Auth.CountryCodePicker.route) {
            CountryCodePickerScreen(navController)
        }

        // Main screens
        composable(Screen.Main.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.Main.Notification.route) {
            NotificationScreen(navController)
        }
        composable(Screen.Main.Profile.route) {
            ProfileScreen(navController)
        }

        // Server screens
        composable(Screen.Server.ChannelList.route) {
            ChannelListScreen(navController)
        }
        composable(Screen.Server.TextChannel.route) {
            TextChannelScreen(navController)
        }
        composable(Screen.Server.VoiceChannel.route) {
            VoiceChannelScreen(navController)
        }
        composable(Screen.Server.MemberList.route) {
            MemberListScreen(navController)
        }

        // Messaging screens
        composable(Screen.Messaging.ChatRoom.route) {
            ChatRoomScreen(navController)
        }
        composable(Screen.Messaging.DirectMessage.route) {
            DirectMessageScreen(navController)
        }
        composable(Screen.Messaging.GroupDM.route) {
            GroupDMScreen(navController)
        }

        // VoiceVideo screens
        composable(Screen.VoiceVideo.VoiceCall.route) {
            VoiceCallScreen(navController)
        }
        composable(Screen.VoiceVideo.VideoCall.route) {
            VideoCallScreen(navController)
        }
        composable(Screen.VoiceVideo.ScreenShare.route) {
            ScreenShareScreen(navController)
        }

        // User screens
        composable(Screen.User.UserProfile.route) {
            UserProfileScreen(navController)
        }
        composable(Screen.User.EditProfile.route) {
            EditProfileScreen(navController)
        }
        composable(Screen.User.FriendList.route) {
            FriendListScreen(navController)
        }

        // Search screens
        composable(Screen.Search.IntegratedDiscovery.route) {
            IntegratedDiscoveryScreen(navController)
        }
        composable(Screen.Search.AddFriend.route) {
            AddFriendScreen(navController)
        }
        composable(Screen.Search.GlobalSearch.route) {
            GlobalSearchScreen(navController)
        }

        // Settings screens
        composable(Screen.Settings.AppSettings.route) {
            AppSettingsScreen(navController)
        }
        composable(Screen.Settings.PrivacySettings.route) {
            PrivacySettingsScreen(navController)
        }
        composable(Screen.Settings.SecuritySettings.route) {
            SecuritySettingsScreen(navController)
        }

        // Misc screens
        composable(Screen.Misc.PinnedMessages.route) {
            PinnedMessagesScreen(navController)
        }
        composable(Screen.Misc.FileMediaGallery.route) {
            FileMediaGalleryScreen(navController)
        }
        composable(Screen.Misc.NitroSubscriptions.route) {
            NitroSubscriptionsScreen(navController)
        }

         */
    }
}

