package com.example.discord_frontend.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.discord_frontend.R

data class DiscordColors(
    val primary: Color = Color(0xFF231E58),
    val secondary: Color = Color(0xF2FFFFFF),
    val backgroundOverlay: Color = Color(0x33000000),
    val textSecondary: Color = Color(0xFF15104D),

    /* form text colors */
    val textPrimary: Color = Color.Black,
    val selectedTextColor: Color = Color(0xFF4F46E5),
    val unSelectedTextColor: Color = Color(0xFF8885AC),
    val outlinedTextFieldColors: Color = Color(0xFF8885AC),

    /* text field colors */
    val inputFieldBackground: Color = Color(0xFFFFFFFFF),
    val unselectedFieldBackground: Color = Color(0xFFEBEBEB),
    val inputBackground: Color = Color.White,
    val inputBorder: Color = Color(0xFFC7C5DE),
    val inputShadow: Color = Color(0x0D000000),

    /* button colors */
    val buttonColor: Color = Color(0xFF5865F2),

    /* text colors */
    val subtitleText: Color = Color(0xFF636363),

    )

data class DiscordShapes(
    val inputShape: RoundedCornerShape = RoundedCornerShape(8.dp)
)

data class DiscordTypography(
    val h1: TextStyle = TextStyle(
        fontSize = 34.sp,
        fontFamily = FontFamily(Font(R.font.inter)),
        lineHeight = 40.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color.White,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
    ),
    val button: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 21.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.25.sp
    ),
    val searchBar: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.25.sp
    ),
    val subtitleText: TextStyle = TextStyle(
        fontSize = 12.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.25.sp
    ),
)

data class DiscordBorder(
    val width: Dp = 1.dp,
    val color: Color = Color.Gray.copy(0.35f),
    val shape: RoundedCornerShape = RoundedCornerShape(8.dp)
)

data class DiscordSpacing(
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp
)

val shapes: DiscordShapes
    @Composable
    get() = LocalDiscordShapes.current

private val LocalDiscordShapes = staticCompositionLocalOf { DiscordShapes() }

object DiscordTheme {
    val colors: DiscordColors
        @Composable
        get() = LocalDiscordColors.current

    val typography: DiscordTypography
        @Composable
        get() = LocalDiscordTypography.current

    val spacing: DiscordSpacing
        @Composable
        get() = LocalDiscordSpacing.current

    val border: DiscordBorder
        @Composable
        get() = LocalDiscordBorder.current

    val shapes: DiscordShapes
        @Composable
        get() = LocalDiscordShapes.current
}

private val LocalDiscordColors = staticCompositionLocalOf { DiscordColors() }
private val LocalDiscordTypography = staticCompositionLocalOf { DiscordTypography() }
private val LocalDiscordSpacing = staticCompositionLocalOf { DiscordSpacing() }
private val LocalDiscordBorder = staticCompositionLocalOf { DiscordBorder() }

@Composable
fun DiscordTheme(
    colors: DiscordColors = DiscordColors(),
    typography: DiscordTypography = DiscordTypography(),
    spacing: DiscordSpacing = DiscordSpacing(),
    border: DiscordBorder = DiscordBorder(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalDiscordColors provides colors,
        LocalDiscordTypography provides typography,
        LocalDiscordSpacing provides spacing,
        LocalDiscordBorder provides border
    ) {
        content()
    }
}