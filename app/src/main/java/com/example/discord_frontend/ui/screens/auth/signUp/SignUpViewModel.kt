package com.example.discord_frontend.ui.screens.auth.signUp

import androidx.lifecycle.ViewModel
import com.example.discord_frontend.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

// UI 관련 데이터 저장 및 관리, 비즈니스 로직 처리
class SignUpViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState

    fun onOptionSelected(option: SignUpOption) {
        _uiState.update { it.copy(selectedOption = option, inputValue = "") }
    }

    fun onInputValueChange(value: String) {
        _uiState.update { it.copy(inputValue = value, isNextEnabled = value.isNotBlank()) }
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
                inputValue = "",
                isNextEnabled = false
            )
        }
    }

    fun onPhoneNumberChange(phoneNumber: String) {
        _uiState.update {
            it.copy(
                phoneNumber = phoneNumber,
                inputValue = phoneNumber,
                isNextEnabled = phoneNumber.isNotBlank()
            )
        }
    }

    fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                inputValue = email,
                isNextEnabled = email.isNotBlank() && email.contains("@")
            )
        }
    }

    fun onUsernameChange(username: String) {
        _uiState.update {
            it.copy(
                username = username,
                inputValue = username,
                isNextEnabled = username.length >= 3
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                inputValue = password,
                isNextEnabled = password.length >= 8
            )
        }
    }

    fun onBirthdateChange(birthdate: String) {
        _uiState.update {
            it.copy(
                birthdate = birthdate,
                inputValue = birthdate,
                isNextEnabled = isValidBirthdate(birthdate)
            )
        }
    }

    private fun isValidBirthdate(birthdate: String): Boolean {
        // 간단한 유효성 검사 예시. 실제 구현에서는 더 복잡한 로직이 필요할 수 있습니다.
        return birthdate.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))
    }

    fun onBackClicked() {
        _uiState.update { currentState ->
            when (currentState.currentStep) {
                SignUpStep.CONTACT_INFO -> currentState // 첫 단계에서는 뒤로 갈 수 없음
                SignUpStep.USERNAME -> currentState.copy(
                    email = "",
                    phoneNumber = "",
                    inputValue = "" // CONTACT_INFO 단계의 입력값 초기화
                )
                SignUpStep.PASSWORD -> currentState.copy(
                    username = "",
                    inputValue = "" // USERNAME 단계의 입력값 초기화
                )
                SignUpStep.BIRTHDATE -> currentState.copy(
                    password = "",
                    inputValue = "" // PASSWORD 단계의 입력값 초기화
                )
                SignUpStep.COMPLETED -> currentState.copy(
                    birthdate = "",
                    inputValue = "" // BIRTHDATE 단계의 입력값 초기화
                )
            }
        }
        updateNextButtonState()
    }

    private fun updateNextButtonState() {
        val isEnabled = when (_uiState.value.currentStep) {
            SignUpStep.CONTACT_INFO -> {
                if (_uiState.value.selectedOption == SignUpOption.PHONE) {
                    _uiState.value.phoneNumber.isNotBlank()
                } else {
                    _uiState.value.email.isNotBlank() && _uiState.value.email.contains("@")
                }
            }
            SignUpStep.USERNAME -> _uiState.value.username.isNotBlank()
            SignUpStep.PASSWORD -> _uiState.value.password.length >= 8
            SignUpStep.BIRTHDATE -> _uiState.value.birthdate.isNotBlank()
            SignUpStep.COMPLETED -> false // 완료 단계에서는 '다음' 버튼을 비활성화
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
    val isNextEnabled: Boolean = false
) {
    val currentStep: SignUpStep
        get() = when {
            email.isEmpty() && phoneNumber.isEmpty() -> SignUpStep.CONTACT_INFO
            username.isEmpty() -> SignUpStep.USERNAME
            password.isEmpty() -> SignUpStep.PASSWORD
            birthdate.isEmpty() -> SignUpStep.BIRTHDATE
            else -> SignUpStep.COMPLETED
        }
}

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