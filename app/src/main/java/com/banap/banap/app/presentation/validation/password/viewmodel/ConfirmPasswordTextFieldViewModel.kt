package com.banap.banap.app.presentation.validation.password.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import com.banap.banap.app.presentation.validation.password.event.PasswordTextFieldFormEvent
import com.banap.banap.domain.use_case.validation.ValidatePassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ConfirmPasswordTextFieldViewModel(
    private val validatePassword: ValidatePassword = ValidatePassword()
) : ViewModel() {
    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationConfirmPasswordTextField>()
    val validatioEventConfirmPassword = validationEventChannel.receiveAsFlow()

    fun onEvent(event: PasswordTextFieldFormEvent) {
        when (event) {
            is PasswordTextFieldFormEvent.PasswordChanged -> {
                state = state.copy(
                    confirmPassword = event.password,
                    confirmPasswordError = null
                )
            }

            is PasswordTextFieldFormEvent.LoadPassword -> {
                state = state.copy(
                    confirmPassword = event.password
                )
            }

            is PasswordTextFieldFormEvent.SetError -> {
                state = state.copy(
                    confirmPasswordError = event.errorMessage
                )
            }

            is PasswordTextFieldFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val passwordResult = validatePassword.execute(state.confirmPassword)

        if (!passwordResult.successful) {
            state = state.copy(
                confirmPasswordError = passwordResult.errorMessage
            )
            return
        }

        state = state.copy(
            confirmPasswordError = null
        )

        viewModelScope.launch {
            validationEventChannel.send(ValidationConfirmPasswordTextField.Success)
        }
    }

    sealed class ValidationConfirmPasswordTextField {
        object Success : ValidationConfirmPasswordTextField()
    }
}