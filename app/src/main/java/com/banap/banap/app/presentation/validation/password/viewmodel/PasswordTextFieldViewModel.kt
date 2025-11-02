package com.banap.banap.app.presentation.validation.password.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.app.presentation.validation.password.event.PasswordTextFieldFormEvent
import com.banap.banap.domain.use_case.validation.ValidatePassword
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PasswordTextFieldViewModel (
    private val validatePassword: ValidatePassword = ValidatePassword()
) : ViewModel() {
    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationPasswordTextField>()
    val validatioEventPassword = validationEventChannel.receiveAsFlow()

    fun onEvent (event: PasswordTextFieldFormEvent) {
        when (event) {
            is PasswordTextFieldFormEvent.PasswordChanged -> {
                state = state.copy(
                    password = event.password,
                    passwordError = null
                )
            }

            is PasswordTextFieldFormEvent.LoadPassword -> {
                state = state.copy(
                    password = event.password
                )
            }

            is PasswordTextFieldFormEvent.SetError -> {
                state = state.copy(
                    passwordError = event.errorMessage
                )
            }

            is PasswordTextFieldFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val passwordResult = validatePassword.execute(state.password)

        if (!passwordResult.successful) {
            state = state.copy(
                passwordError = passwordResult.errorMessage
            )
            return
        }

        state = state.copy(
            passwordError = null
        )

        viewModelScope.launch {
            validationEventChannel.send(ValidationPasswordTextField.Success)
        }
    }

    sealed class ValidationPasswordTextField {
        object Success : ValidationPasswordTextField()
    }
}