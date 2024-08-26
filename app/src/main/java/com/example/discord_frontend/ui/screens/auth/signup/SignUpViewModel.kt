package com.example.discord_frontend.ui.screens.auth.signup

import androidx.lifecycle.ViewModel
import com.example.discord_frontend.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState

    fun onOptionSelected(option: SignUpOption) {
        _uiState.update { it.copy(selectedOption = option, inputValue = "") }
        updateNextButtonState()
    }

    fun onInputValueChange(value: String) {
        _uiState.update { it.copy(inputValue = value) }
        updateNextButtonState()
    }

    fun onNextClicked() {
        val currentState = _uiState.value
        when (currentState.currentStep) {
            SignUpStep.CONTACT_INFO -> {
                if (currentState.selectedOption == SignUpOption.PHONE) {
                    onPhoneNumberChange(currentState.inputValue)
                } else {
                    onEmailChange(currentState.inputValue)
                }
                moveToNextStep()
            }
            SignUpStep.VERIFICATION -> {
                // Here you would typically verify the code
                // For now, we'll just move to the next step
                moveToNextStep()
            }
            SignUpStep.USERNAME -> {
                onUsernameChange(currentState.inputValue)
                moveToNextStep()
            }
            SignUpStep.ACCOUNT_CREATE -> {
                onPasswordChange(currentState.inputValue)
                moveToNextStep()
            }
            SignUpStep.BIRTHDATE -> {
                onBirthdateChange(currentState.inputValue)
                moveToNextStep()
            }
            SignUpStep.COMPLETED -> {
                onSubmit()
            }
        }
    }

    fun onSubmit() {
        TODO("Not yet implemented")
    }

    private fun moveToNextStep() {
        _uiState.update { currentState ->
            currentState.copy(
                currentStep = when (currentState.currentStep) {
                    SignUpStep.CONTACT_INFO -> SignUpStep.VERIFICATION
                    SignUpStep.VERIFICATION -> SignUpStep.USERNAME
                    SignUpStep.USERNAME -> SignUpStep.ACCOUNT_CREATE
                    SignUpStep.ACCOUNT_CREATE -> SignUpStep.BIRTHDATE
                    SignUpStep.BIRTHDATE -> SignUpStep.COMPLETED
                    SignUpStep.COMPLETED -> SignUpStep.COMPLETED
                },
                inputValue = "",
                isNextEnabled = false
            )
        }
    }

    fun onPhoneNumberChange(phoneNumber: String) {
        _uiState.update { it.copy(phoneNumber = phoneNumber) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onUsernameChange(username: String) {
        _uiState.update { it.copy(username = username) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun onBirthdateChange(birthdate: String) {
        _uiState.update { it.copy(birthdate = birthdate) }
    }

    fun onVerificationCodeChange(code: String) {
        _uiState.update { it.copy(verificationCode = code) }
    }

    private fun isValidBirthdate(birthdate: String): Boolean {
        return birthdate.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))
    }

    private fun isValidVerificationCode(code: String): Boolean {
        // This is a simple check. In a real app, you'd want more robust validation.
        return code.length == 6 && code.all { it.isDigit() }
    }

    fun onBackClicked() {
        _uiState.update { currentState ->
            when (currentState.currentStep) {
                SignUpStep.CONTACT_INFO -> currentState.copy(
                    navigateToWelcome = true
                )
                SignUpStep.VERIFICATION -> currentState.copy(
                    currentStep = SignUpStep.CONTACT_INFO,
                    verificationCode = "",
                    inputValue = ""
                )
                SignUpStep.USERNAME -> currentState.copy(
                    currentStep = SignUpStep.VERIFICATION,
                    verificationCode = "",
                    inputValue = ""
                )
                SignUpStep.ACCOUNT_CREATE -> currentState.copy(
                    currentStep = SignUpStep.USERNAME,
                    username = "",
                    inputValue = ""
                )
                SignUpStep.BIRTHDATE -> currentState.copy(
                    currentStep = SignUpStep.ACCOUNT_CREATE,
                    password = "",
                    inputValue = ""
                )
                SignUpStep.COMPLETED -> currentState.copy(
                    currentStep = SignUpStep.BIRTHDATE,
                    birthdate = "",
                    inputValue = ""
                )
            }
        }
        updateNextButtonState()
    }

    fun onNavigatedToWelcome() {
        _uiState.update { it.copy(navigateToWelcome = false) }
    }

    fun updateNextButtonState() {
        val isEnabled = when (_uiState.value.currentStep) {
            SignUpStep.CONTACT_INFO -> {
                if (_uiState.value.selectedOption == SignUpOption.PHONE) {
                    _uiState.value.inputValue.isNotBlank()
                } else {
                    _uiState.value.inputValue.isNotBlank() && _uiState.value.inputValue.contains("@")
                }
            }
            SignUpStep.VERIFICATION -> isValidVerificationCode(_uiState.value.inputValue)
            SignUpStep.USERNAME -> _uiState.value.inputValue.length >= 3
            SignUpStep.ACCOUNT_CREATE -> _uiState.value.inputValue.length >= 8
            SignUpStep.BIRTHDATE -> isValidBirthdate(_uiState.value.inputValue)
            SignUpStep.COMPLETED -> false // Disable 'Next' button on the completed step
        }
        _uiState.update { it.copy(isNextEnabled = isEnabled) }
    }
}

data class SignUpUiState(
    val selectedOption: SignUpOption = SignUpOption.PHONE,
    val inputValue: String = "",
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val birthdate: String = "",
    val phoneNumber: String = "",
    val verificationCode: String = "",
    val isNextEnabled: Boolean = false,
    val currentStep: SignUpStep = SignUpStep.CONTACT_INFO,
    val navigateToWelcome: Boolean = false
)

enum class SignUpOption(val textResId: Int, val hintResId: Int) {
    PHONE(R.string.phone, R.string.enter_phone),
    EMAIL(R.string.email, R.string.enter_email)
}

enum class SignUpStep {
    CONTACT_INFO,
    VERIFICATION,
    USERNAME,
    ACCOUNT_CREATE,
    BIRTHDATE,
    COMPLETED
}