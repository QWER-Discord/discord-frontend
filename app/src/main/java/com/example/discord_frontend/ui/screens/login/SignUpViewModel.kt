package com.example.discord_frontend.ui.screens.login

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
    }

    fun onInputValueChange(value: String) {
        _uiState.update { it.copy(inputValue = value, isNextEnabled = value.isNotBlank()) }
    }

    fun onNextClicked() {
        // TODO: Implement sign up logic
    }
}

data class SignUpUiState(
    val selectedOption: SignUpOption = SignUpOption.PHONE,
    val inputValue: String = "",
    val isNextEnabled: Boolean = false
)

enum class SignUpOption(val textResId: Int, val hintResId: Int) {
    PHONE(R.string.phone, R.string.enter_phone),
    EMAIL(R.string.email, R.string.enter_email)
}