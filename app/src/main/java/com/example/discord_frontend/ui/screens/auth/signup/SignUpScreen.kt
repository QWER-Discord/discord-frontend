package com.example.discord_frontend.ui.screens.auth.signup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
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
import com.example.discord_frontend.ui.components.CustomInputDescription
import com.example.discord_frontend.ui.components.CustomInputField
import com.example.discord_frontend.ui.components.EnhancedPasswordInputField
import com.example.discord_frontend.ui.components.NextButton
import com.example.discord_frontend.ui.components.SubtitleLabel
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
            navController.navigate(Screen.Auth.Welcome.route) {
                popUpTo(Screen.Auth.Welcome.route) { inclusive = true }
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

                Spacer(modifier = Modifier.height(30.dp))
                NextButton(
                    text = when (uiState.currentStep) {
                        SignUpStep.CONTACT_INFO -> stringResource(R.string.next)
                        SignUpStep.VERIFICATION -> stringResource(R.string.verify)
                        SignUpStep.USERNAME -> stringResource(R.string.next)
                        SignUpStep.ACCOUNT_CREATE -> stringResource(R.string.next)
                        SignUpStep.BIRTHDATE -> stringResource(R.string.submit)
                        SignUpStep.COMPLETED -> TODO()
                    },

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
                )
            }
        }
    }
}

@Composable
fun SignUpContent(uiState: SignUpUiState, viewModel: SignUpViewModel, navController: NavController) {
    when (uiState.currentStep) {
        SignUpStep.CONTACT_INFO -> ContactInfoStep(uiState, viewModel, navController)
        SignUpStep.VERIFICATION -> VerifyContactinfoStep(uiState, viewModel)
        SignUpStep.USERNAME -> UsernameStep(uiState, viewModel)
        SignUpStep.ACCOUNT_CREATE -> AccountCreationStep(uiState, viewModel)
        SignUpStep.BIRTHDATE -> BirthdateStep(uiState, viewModel)
        SignUpStep.COMPLETED -> {}
    }
}


@Composable
fun ContactInfoStep(uiState: SignUpUiState, viewModel: SignUpViewModel, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SignUpSelector(
            selectedOption = uiState.selectedOption,
            onOptionSelected = viewModel::onOptionSelected,
            modifier = Modifier
                .fillMaxWidth()
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
fun VerifyContactinfoStep(uiState: SignUpUiState, viewModel: SignUpViewModel) {
    CustomInputField(
        value = uiState.verificationCode,
        onValueChange = viewModel::onVerificationCodeChange,
        placeholder = stringResource(R.string.verification_code),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun UsernameStep(uiState: SignUpUiState, viewModel: SignUpViewModel) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(7.5.dp)
    ) {
        // 사용자명
        SubtitleLabel(
            text = stringResource(R.string.nickname)
        )
        CustomInputField(
            value = uiState.username,
            onValueChange = viewModel::onUsernameChange,
            placeholder = "",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier
                .fillMaxWidth()
        )
        CustomInputDescription(stringResource(R.string.username_description))
    }
}

@Composable
fun AccountCreationStep(uiState: SignUpUiState, viewModel: SignUpViewModel) {
    var focusedField by remember { mutableStateOf<String?>(null) }

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(7.5.dp)
    ) {
        // 사용자명
        SubtitleLabel(text = stringResource(R.string.nickname))
        CustomInputField(
            value = uiState.username,
            onValueChange = viewModel::onUsernameChange,
            placeholder = "",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { if (it.isFocused) focusedField = "username" else if (focusedField == "username") focusedField = null }
        )
        AnimatedVisibility(
            visible = focusedField == "username",
            enter = fadeIn(animationSpec = tween(durationMillis = 300, easing = EaseInOutCubic)) +
                    expandVertically(animationSpec = tween(durationMillis = 300, easing = EaseInOutCubic)) +
                    slideInVertically(animationSpec = tween(durationMillis = 300, easing = EaseInOutCubic)) { it / 2 },
            exit = fadeOut(animationSpec = tween(durationMillis = 200, easing = EaseInOutCubic)) +
                    shrinkVertically(animationSpec = tween(durationMillis = 200, easing = EaseInOutCubic)) +
                    slideOutVertically(animationSpec = tween(durationMillis = 200, easing = EaseInOutCubic)) { it / 2 }
        ) {
            CustomInputDescription(stringResource(R.string.username_rule_description))
        }

        Spacer(modifier = Modifier.height(15.dp))

        // 비밀번호
        SubtitleLabel(text = stringResource(R.string.password))
        EnhancedPasswordInputField(
            value = uiState.password,
            onValueChange = viewModel::onPasswordChange,
            placeholder = "",
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { if (it.isFocused) focusedField = "password" else if (focusedField == "password") focusedField = null }
        )
        AnimatedVisibility(
            visible = focusedField == "password",
            enter = fadeIn(animationSpec = tween(durationMillis = 300, delayMillis = 100, easing = EaseInOutCubic)) +
                    expandVertically(animationSpec = tween(durationMillis = 300, delayMillis = 100, easing = EaseInOutCubic)) +
                    slideInVertically(animationSpec = tween(durationMillis = 300, delayMillis = 100, easing = EaseInOutCubic)) { it / 2 },
            exit = fadeOut(animationSpec = tween(durationMillis = 200, easing = EaseInOutCubic)) +
                    shrinkVertically(animationSpec = tween(durationMillis = 200, easing = EaseInOutCubic)) +
                    slideOutVertically(animationSpec = tween(durationMillis = 200, easing = EaseInOutCubic)) { it / 2 }
        ) {
            CustomInputDescription(stringResource(R.string.password_rule_description))
        }
    }
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
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
            SignUpStep.VERIFICATION -> R.string.enter_verification_code
            SignUpStep.USERNAME -> R.string.choose_username
            SignUpStep.ACCOUNT_CREATE -> R.string.create_account
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