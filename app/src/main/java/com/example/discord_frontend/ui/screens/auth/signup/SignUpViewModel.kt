package com.example.discord_frontend.ui.screens.auth.signup

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
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
            SignUpStep.USERNAME -> {
                onUsernameChange(currentState.inputValue)
                moveToNextStep()
            }
            SignUpStep.PASSWORD -> {
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
                    SignUpStep.CONTACT_INFO -> SignUpStep.USERNAME
                    SignUpStep.USERNAME -> SignUpStep.PASSWORD
                    SignUpStep.PASSWORD -> SignUpStep.BIRTHDATE
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

    private fun isValidBirthdate(birthdate: String): Boolean {
        return birthdate.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))
    }

    fun onBackClicked() {
        _uiState.update { currentState ->
            when (currentState.currentStep) {
                SignUpStep.CONTACT_INFO -> currentState.copy(
                    navigateToWelcome = true
                )
                SignUpStep.USERNAME -> currentState.copy(
                    currentStep = SignUpStep.CONTACT_INFO,
                    email = "",
                    phoneNumber = "",
                    inputValue = ""
                )
                SignUpStep.PASSWORD -> currentState.copy(
                    currentStep = SignUpStep.USERNAME,
                    username = "",
                    inputValue = ""
                )
                SignUpStep.BIRTHDATE -> currentState.copy(
                    currentStep = SignUpStep.PASSWORD,
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
            SignUpStep.USERNAME -> _uiState.value.inputValue.length >= 3
            SignUpStep.PASSWORD -> _uiState.value.inputValue.length >= 8
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
    USERNAME,
    PASSWORD,
    BIRTHDATE,
    COMPLETED
}