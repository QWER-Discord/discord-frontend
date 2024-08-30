package com.example.discord_frontend.navigation

// 화면 각 부분을 정의. route : 네비게이션 경로 / label : 화면 이름
sealed class Screen(val route: String, val label: String) {
    sealed class Auth(route: String, label: String) : Screen(route, label) {
        data object Welcome : Auth("welcome", "Welcome")
        data object Login : Auth("login", "Login")
        data object SignUp : Auth("signup", "Sign Up")
        data object CountryCodePicker : Auth("countryCodePicker", "Country Code Selection")
    }

    sealed class Main(route: String, label: String) : Screen(route, label) {
        data object Home : Main("home", "Home")
        data object Notification : Main("notification", "Notification")
        data object Profile : Main("profile", "Profile")
    }

    sealed class Server(route: String, label: String) : Screen(route, label) {
        data object ChannelList : Server("channelList", "Channel List")
        data object TextChannel : Server("textChannel", "Text Channel")
        data object VoiceChannel : Server("voiceChannel", "Voice Channel")
        data object MemberList : Server("memberList", "Member List")
    }

    sealed class Messaging(route: String, label: String) : Screen(route, label) {
        data object ChatRoom : Messaging("chatRoom", "Chat Room")
        data object DirectMessage : Messaging("directMessage", "Direct Message")
        data object GroupDM : Messaging("groupDM", "Group DM")
    }

    sealed class VoiceVideo(route: String, label: String) : Screen(route, label) {
        data object VoiceCall : VoiceVideo("voiceCall", "Voice Call")
        data object VideoCall : VoiceVideo("videoCall", "Video Call")
        data object ScreenShare : VoiceVideo("screenShare", "Screen Share")
    }

    sealed class User(route: String, label: String) : Screen(route, label) {
        data object UserProfile : User("userProfile", "User Profile")
        data object EditProfile : User("editProfile", "Edit Profile")
        data object FriendList : User("friendList", "Friend List")
    }

    sealed class Search(route: String, label: String) : Screen(route, label) {
        data object IntegratedDiscovery : Search("integratedDiscovery", "Integrated Discovery")
        data object AddFriend : Search("addFriend", "Add Friend")
        data object GlobalSearch : Search("globalSearch", "Global Search")
    }

    sealed class Settings(route: String, label: String) : Screen(route, label) {
        data object AppSettings : Settings("appSettings", "App Settings")
        data object PrivacySettings : Settings("privacySettings", "Privacy Settings")
        data object SecuritySettings : Settings("securitySettings", "Security Settings")
    }

    sealed class Misc(route: String, label: String) : Screen(route, label) {
        data object PinnedMessages : Misc("pinnedMessages", "Pinned Messages")
        data object FileMediaGallery : Misc("fileMediaGallery", "File & Media Gallery")
        data object NitroSubscriptions : Misc("nitroSubscriptions", "Nitro & Subscriptions")
    }
}