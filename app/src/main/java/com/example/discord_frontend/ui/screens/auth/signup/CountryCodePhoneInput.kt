package com.example.discord_frontend.ui.screens.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.discord_frontend.R
import com.example.discord_frontend.ui.components.CustomInputField
import com.example.discord_frontend.ui.theme.AppTypography
import com.example.discord_frontend.ui.theme.DiscordTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun CountryCodePhoneInput(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .shadow(
                elevation = 2.dp,
                spotColor = DiscordTheme.colors.inputShadow,
                ambientColor = DiscordTheme.colors.inputShadow
            )
            .border(1.dp, DiscordTheme.colors.inputBorder, DiscordTheme.shapes.inputShape)
            .background(DiscordTheme.colors.inputBackground, DiscordTheme.shapes.inputShape)
            .height(53.dp)
    ) {
        CountryCodeSelector(navController)
        PhoneNumberInput(phoneNumber, onPhoneNumberChange)
    }
}

@Composable
private fun CountryCodeSelector(navController: NavController) {
    var selectedCountryCode by remember { mutableStateOf("KR +82") }
    val borderColor = DiscordTheme.colors.unSelectedTextColor.copy(0.35f)

    // 네비게이션 결과 처리
    val backStackEntry by navController.currentBackStackEntryAsState()
    LaunchedEffect(backStackEntry) {
        val result = backStackEntry?.savedStateHandle?.get<String>("selectedCountryCode")
        if (result != null) {
            val (name, code) = result.split("|")
            selectedCountryCode = "$name $code"
            // 결과를 사용한 후 삭제
            backStackEntry?.savedStateHandle?.remove<String>("selectedCountryCode")
        }
    }

    Row(
        modifier = Modifier
            .width(110.dp)
            .fillMaxHeight()
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable {
                    navController.navigate("CountryCodePicker")
                }
                .padding(start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = selectedCountryCode,
                style = AppTypography.labelMedium,
                color = DiscordTheme.colors.unSelectedTextColor,
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
            )
            Image(
                modifier = Modifier
                    .padding(top = 0.6.dp, start = 4.5.dp),
                painter = painterResource(id = R.drawable.arrow_down),
                contentDescription = "Expand country code picker",
                contentScale = ContentScale.None,

            )
        }

        // Right border
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(borderColor)
        )
    }
}

@Composable
fun PhoneNumberInput(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    CustomInputField(
        value = phoneNumber,
        onValueChange = onPhoneNumberChange,
        placeholder = stringResource(R.string.phone_number),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        showBorder = false
    )
}