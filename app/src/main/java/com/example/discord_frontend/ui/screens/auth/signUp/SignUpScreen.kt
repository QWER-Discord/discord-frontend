package com.example.discord_frontend.ui.screens.auth.signUp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.discord_frontend.R
import com.example.discord_frontend.ui.components.NextButton
import com.example.discord_frontend.ui.theme.AppTypography
import com.example.discord_frontend.ui.theme.DiscordTheme

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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(70.dp))
                SignUpTitle(uiState.currentStep)

                Spacer(modifier = Modifier.height(30.dp))

                // Step에 따라 실제 컴포저블을 렌더링
                when (uiState.currentStep) {
                    SignUpStep.CONTACT_INFO -> ContactInfoStep(uiState, viewModel)
                    SignUpStep.USERNAME -> UsernameStep(uiState, viewModel)
                    SignUpStep.PASSWORD -> PasswordStep(uiState, viewModel)
                    SignUpStep.BIRTHDATE -> BirthdateStep(uiState, viewModel)
                    SignUpStep.COMPLETED -> {}
                }

                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (uiState.currentStep != SignUpStep.CONTACT_INFO) {
                        BackButton(onClick = viewModel::onBackClicked)
                    }
                    NextButton(
                        text = if (uiState.currentStep == SignUpStep.BIRTHDATE) stringResource(R.string.submit) else stringResource(R.string.next),
                        onClick = {
                            if (uiState.currentStep == SignUpStep.BIRTHDATE) {
                                viewModel.onSubmit()
                                navController.navigate("main_screen")
                            } else {
                                viewModel.onNextClicked()
                            }
                        },
                        enabled = uiState.isNextEnabled
                    )
                }
            }
        }
    }
}


@Composable
fun ContactInfoStep(uiState: SignUpUiState, viewModel: SignUpViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SignUpSelector(
            selectedOption = uiState.selectedOption,
            onOptionSelected = viewModel::onOptionSelected,
            modifier = Modifier.fillMaxWidth()
        )

        if (uiState.selectedOption == SignUpOption.PHONE) {
            PhoneInputWithCCP(
                phoneNumber = uiState.phoneNumber,
                onPhoneNumberChange = viewModel::onPhoneNumberChange
            )
        } else {
            InputForm(
                value = uiState.email,
                onValueChange = viewModel::onEmailChange,
                label = stringResource(R.string.email),
                keyboardType = KeyboardType.Email
            )
        }
        PrivacyPolicyLink()
    }
}

@Composable
fun UsernameStep(uiState: SignUpUiState, viewModel: SignUpViewModel) {
    InputForm(
        value = uiState.username,
        onValueChange = viewModel::onUsernameChange,
        label = stringResource(R.string.username)
    )
}

@Composable
fun PasswordStep(uiState: SignUpUiState, viewModel: SignUpViewModel) {
    InputForm(
        value = uiState.password,
        onValueChange = viewModel::onPasswordChange,
        label = stringResource(R.string.password),
        keyboardType = KeyboardType.Password
    )
}

@Composable
fun BirthdateStep(uiState: SignUpUiState, viewModel: SignUpViewModel) {
    InputForm(
        value = uiState.birthdate,
        onValueChange = viewModel::onBirthdateChange,
        label = stringResource(R.string.birthdate),
        keyboardType = KeyboardType.Number
    )
}

@Composable
fun PhoneInputWithCCP(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCountryCode by remember { mutableStateOf("+82") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // CCP 구현은 보류 (추후 구현 예정)

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
        horizontalArrangement = Arrangement.Center
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
                Spacer(modifier = Modifier.height(15.dp))
                HorizontalDivider(
                    modifier = Modifier.width(185.dp),
                    thickness = 2.dp,
                    color = if (option == selectedOption) DiscordTheme.colors.selectedTextColor else DiscordTheme.colors.unSelectedTextColor
                )
            }
        }
    }
}

@Composable
private fun SignUpTitle(currentStep: SignUpStep) {
    val titleText = when (currentStep) {
        SignUpStep.CONTACT_INFO -> stringResource(R.string.enter_phone_or_email)
        SignUpStep.USERNAME -> stringResource(R.string.choose_username)
        SignUpStep.PASSWORD -> stringResource(R.string.create_password)
        SignUpStep.BIRTHDATE -> stringResource(R.string.enter_birthdate)
        SignUpStep.COMPLETED -> TODO()
    }
    Text(
        text = titleText,
        style = AppTypography.titleLarge,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun InputForm(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
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

@Composable
private fun BackButton(onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(text = stringResource(R.string.back))
    }
}