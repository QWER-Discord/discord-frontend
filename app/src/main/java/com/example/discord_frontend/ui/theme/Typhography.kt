package com.example.discord_frontend.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.discord_frontend.R

val InterFont = FontFamily(
    Font(R.font.inter, FontWeight.Normal),
    Font(R.font.inter_semibold, FontWeight.SemiBold)
)

/* Typography 지정 시 폰트 색상, 정렬에 대해서는 매개변수로 처리할 것 */
val AppTypography = Typography(
    // 회원가입
    titleLarge = TextStyle(
        fontSize = 28.sp,
        lineHeight = 43.sp,
        fontFamily = InterFont,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.01.sp
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 17.sp,
        fontFamily = InterFont,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.25.sp
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontFamily = InterFont,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.25.sp
    ),

    // 다른 스타일들도 여기에 정의할 수 있습니다.
)