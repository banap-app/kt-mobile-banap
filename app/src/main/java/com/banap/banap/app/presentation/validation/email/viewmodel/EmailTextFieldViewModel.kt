package com.banap.banap.app.presentation.validation.email.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.app.presentation.validation.email.event.EmailTextFieldFormEvent
import com.banap.banap.domain.use_case.validation.ValidateEmail
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class EmailTextFieldViewModel (
    private val validateEmail: ValidateEmail = ValidateEmail()
) : ViewModel() {
    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEmailTextField>()
    val validationEventEmail = validationEventChannel.receiveAsFlow()

    fun onEvent (event: EmailTextFieldFormEvent) {
        when (event) {
            is EmailTextFieldFormEvent.EmailChanged -> {
                state = state.copy(
                    email = event.email,
                    emailError = null
                )
            }

            is EmailTextFieldFormEvent.LoadEmail -> {
                state = state.copy(
                    email = event.email
                )
            }

            is EmailTextFieldFormEvent.SetError -> {
                state = state.copy(
                    emailError = event.errorMessage
                )
            }

            is EmailTextFieldFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email)

        if (!emailResult.successful) {
            state = state.copy(
                emailError = emailResult.errorMessage
            )
            return
        }

        state = state.copy(
            emailError = null
        )

        viewModelScope.launch {
            validationEventChannel.send(ValidationEmailTextField.Success)
        }
    }

    sealed class ValidationEmailTextField {
        object Success : ValidationEmailTextField()
    }
}