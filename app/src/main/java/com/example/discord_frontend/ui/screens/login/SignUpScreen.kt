package com.example.discord_frontend.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.discord_frontend.R
import com.example.discord_frontend.ui.components.NextButton
import com.example.discord_frontend.ui.theme.AppTypography
import com.example.discord_frontend.ui.theme.DiscordTheme
import androidx.compose.runtime.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.text.input.KeyboardType
import com.hbb20.CountryCodePicker



@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    val navController = rememberNavController()
    SignUpScreen(navController = navController)
}

@Composable
fun SignUpScreen(navController: NavController, viewModel: SignUpViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    DiscordTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment     = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(70.dp))
                SignUpTitle()

                Spacer(modifier = Modifier.height(30.dp))
                SignUpSelector(
                    selectedOption = uiState.selectedOption,
                    onOptionSelected = viewModel::onOptionSelected,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (uiState.selectedOption == SignUpOption.PHONE) {
                        PhoneInputWithCCP(
                            phoneNumber = uiState.inputValue,
                            onPhoneNumberChange = viewModel::onInputValueChange
                        )
                    } else {
                        InputForm(
                            selectedOption = uiState.selectedOption,
                            inputValue = uiState.inputValue,
                            onInputValueChange = viewModel::onInputValueChange
                        )
                    }
                    PrivacyPolicyLink()
                }

                Spacer(modifier = Modifier.height(30.dp))
                NextButton(
                    text = stringResource(R.string.next),
                    onClick = viewModel::onNextClicked,
                    enabled = uiState.isNextEnabled
                )
            }
        }
    }
}

@Composable
fun PhoneInputWithCCP(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCountryCode by remember { mutableStateOf("+82") } // 기본값으로 한국 국가 코드 설정

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // CCP (Country Code Picker) - 일단 보류
        /*
        AndroidView(
            factory = { context ->
                CountryCodePicker(context).apply {
                    setOnCountryChangeListener {
                        selectedCountryCode = "+$phoneCode"
                    }
                    setDefaultCountryUsingNameCode("KR")
                    setTextSize(16)
                }
            },
            modifier = Modifier.weight(0.4f)
        )
        */

        // 전화번호 입력 필드
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = onPhoneNumberChange,
            label = { Text(stringResource(R.string.phone_number)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.weight(0.6f),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = DiscordTheme.colors.selectedTextColor,
                unfocusedTextColor = DiscordTheme.colors.unSelectedTextColor.copy(alpha = 0.9f),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = DiscordTheme.colors.unSelectedTextColor.copy(alpha = 0.1f),
                focusedBorderColor = DiscordTheme.colors.selectedTextColor,
                unfocusedBorderColor = DiscordTheme.colors.unSelectedTextColor.copy(alpha = 0.5f),
                focusedLabelColor = DiscordTheme.colors.selectedTextColor,
                unfocusedLabelColor = DiscordTheme.colors.unSelectedTextColor.copy(alpha = 0.7f),
                cursorColor = DiscordTheme.colors.selectedTextColor,
            ),
            textStyle = AppTypography.bodyMedium.copy(color = DiscordTheme.colors.selectedTextColor)
        )
    }
}

@Composable
private fun SignUpSelector(
    selectedOption: SignUpOption,
    onOptionSelected: (SignUpOption) -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center  // Center로 변경하여 옵션들을 더 가깝게 배치
    ) {
        SignUpOption.entries.forEach { option ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = { onOptionSelected(option) },
                    )
            ) {
                Text(
                    text = stringResource(option.textResId),
                    style = AppTypography.bodyMedium,
                    color = if (option == selectedOption) DiscordTheme.colors.selectedTextColor else DiscordTheme.colors.unSelectedTextColor
                )
                Spacer(modifier = Modifier.height(15.dp))  // 텍스트와 구분선 사이 간격 줄임
                HorizontalDivider(
                    modifier = Modifier
                        .width(185.dp),  // 구분선 너비 줄임
                    thickness = 2.dp,
                    color = if (option == selectedOption) DiscordTheme.colors.selectedTextColor else DiscordTheme.colors.unSelectedTextColor
                )
            }
        }
    }
}

@Composable
private fun SignUpTitle() {
    Text(
        text = stringResource(R.string.enter_phone_or_email),
        style = AppTypography.titleLarge,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputForm(
    selectedOption: SignUpOption,
    inputValue: String,
    onInputValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = inputValue,
        onValueChange = onInputValueChange,
        label = { Text(stringResource(selectedOption.hintResId)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = DiscordTheme.colors.selectedTextColor,
            unfocusedTextColor = DiscordTheme.colors.unSelectedTextColor.copy(alpha = 0.9f),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = DiscordTheme.colors.unSelectedTextColor.copy(alpha = 0.1f),
            focusedBorderColor = DiscordTheme.colors.selectedTextColor,
            unfocusedBorderColor = DiscordTheme.colors.unSelectedTextColor.copy(alpha = 0.5f),
            focusedLabelColor = DiscordTheme.colors.selectedTextColor,
            unfocusedLabelColor = DiscordTheme.colors.unSelectedTextColor.copy(alpha = 0.7f),
            cursorColor = DiscordTheme.colors.selectedTextColor,
        ),
        textStyle = AppTypography.bodyMedium.copy(color = DiscordTheme.colors.selectedTextColor)
    )
}

@Composable
private fun PrivacyPolicyLink() {
    Text(
        text = stringResource(R.string.privacy_policy_link),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    )
}