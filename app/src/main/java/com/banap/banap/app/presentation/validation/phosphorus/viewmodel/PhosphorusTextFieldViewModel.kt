package com.banap.banap.app.presentation.validation.phosphorus.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import com.banap.banap.app.presentation.validation.phosphorus.event.PhosphorusTextFieldFormEvent
import com.banap.banap.domain.use_case.validation.ValidatePhosphorus
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PhosphorusTextFieldViewModel (
    private val validatePhosphorus: ValidatePhosphorus = ValidatePhosphorus()
) : ViewModel() {
    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationPhosphorusTextField>()
    val validationEventPhosphorus = validationEventChannel.receiveAsFlow()

    fun onEvent (event: PhosphorusTextFieldFormEvent) {
        when (event) {
            is PhosphorusTextFieldFormEvent.PhosphorusChanged -> {
                state = state.copy(
                    phosphorus = event.phosphorus
                )
            }

            is PhosphorusTextFieldFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val phosphorusResult = validatePhosphorus.execute(state.phosphorus)

        if (!phosphorusResult.successful) {
            state = state.copy(
                phosphorusError = phosphorusResult.errorMessage
            )
            return
        }

        state = state.copy(
            phosphorusError = null
        )

        viewModelScope.launch {
            validationEventChannel.send(ValidationPhosphorusTextField.Success)
        }
    }

    sealed class ValidationPhosphorusTextField {
        object Success : ValidationPhosphorusTextField()
    }
}