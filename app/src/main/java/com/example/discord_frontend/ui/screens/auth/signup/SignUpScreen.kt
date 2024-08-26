package com.example.discord_frontend.ui.screens.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.discord_frontend.R
import com.example.discord_frontend.navigation.Screen
import com.example.discord_frontend.ui.components.CustomInputField
import com.example.discord_frontend.ui.components.NextButton
import com.example.discord_frontend.ui.components.TopBackButton
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

    LaunchedEffect(uiState.navigateToWelcome) {
        if (uiState.navigateToWelcome) {
            navController.navigate(Screen.Welcome.route) {
                popUpTo(Screen.Welcome.route) { inclusive = true }
            }
            viewModel.onNavigatedToWelcome()
        }
    }

    DiscordTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopBackButton(
                    onBackClick = viewModel::onBackClicked,
                    modifier = Modifier.padding(start = 5.dp)
                )
                SignUpTitle(uiState.currentStep)
                SignUpContent(uiState, viewModel, navController)

                Spacer(modifier = Modifier.height(25.dp))
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
                    enabled = uiState.isNextEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
fun SignUpContent(uiState: SignUpUiState, viewModel: SignUpViewModel, navController: NavController) {
    when (uiState.currentStep) {
        SignUpStep.CONTACT_INFO -> ContactInfoStep(uiState, viewModel, navController)
        SignUpStep.USERNAME -> UsernameStep(uiState, viewModel)
        SignUpStep.PASSWORD -> PasswordStep(uiState, viewModel)
        SignUpStep.BIRTHDATE -> BirthdateStep(uiState, viewModel)
        SignUpStep.COMPLETED -> {}
    }
}

@Composable
fun ContactInfoStep(uiState: SignUpUiState, viewModel: SignUpViewModel, navController: NavController) {
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
            // 전화번호 입력
            CountryCodePhoneInput(
                phoneNumber = uiState.phoneNumber,
                onPhoneNumberChange = viewModel::onPhoneNumberChange,
                navController = navController
            )
        } else {
            // 이메일 입력
            CustomInputField(
                value = uiState.email,
                onValueChange = viewModel::onEmailChange,
                placeholder = stringResource(R.string.email),
                keyboardType = KeyboardType.Email,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            )
        }
        PrivacyPolicyLink()
    }
}

@Composable
fun UsernameStep(uiState: SignUpUiState, viewModel: SignUpViewModel) {
    CustomInputField(
        value = uiState.username,
        onValueChange = viewModel::onUsernameChange,
        placeholder = stringResource(R.string.username),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun PasswordStep(uiState: SignUpUiState, viewModel: SignUpViewModel) {
    CustomInputField(
        value = uiState.password,
        onValueChange = viewModel::onPasswordChange,
        placeholder = stringResource(R.string.password),
        keyboardType = KeyboardType.Password,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun BirthdateStep(uiState: SignUpUiState, viewModel: SignUpViewModel) {
    CustomInputField(
        value = uiState.birthdate,
        onValueChange = viewModel::onBirthdateChange,
        placeholder = stringResource(R.string.birthdate),
        keyboardType = KeyboardType.Number,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        modifier = Modifier
            .fillMaxWidth()
    )
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
    val titleText = stringResource(
        when (currentStep) {
            SignUpStep.CONTACT_INFO -> R.string.enter_phone_or_email
            SignUpStep.USERNAME -> R.string.choose_username
            SignUpStep.PASSWORD -> R.string.create_password
            SignUpStep.BIRTHDATE -> R.string.enter_birthdate
            SignUpStep.COMPLETED -> R.string.signup_completed
        }
    )
    Text(
        text = titleText,
        style = AppTypography.titleLarge,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(top = 17.dp, bottom = 25.dp)
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