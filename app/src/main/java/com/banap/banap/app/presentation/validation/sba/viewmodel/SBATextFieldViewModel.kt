package com.banap.banap.app.presentation.validation.sba.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import com.banap.banap.app.presentation.validation.sba.event.SBATextFieldFormEvent
import com.banap.banap.domain.use_case.validation.ValidateSba
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SBATextFieldViewModel (
    private val validateSba: ValidateSba = ValidateSba()
) : ViewModel() {
    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationSbaTextField>()
    val validationEventSba = validationEventChannel.receiveAsFlow()

    fun onEvent(event: SBATextFieldFormEvent) {
        when (event) {
            is SBATextFieldFormEvent.SBAChanged -> {
                state = state.copy(
                    sba = event.sba
                )
            }

            is SBATextFieldFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val sbaResult = validateSba.execute(state.sba)

        if (!sbaResult.successful) {
            state = state.copy(
                sbaError = sbaResult.errorMessage
            )
            return
        }

        state = state.copy(
            sbaError = null
        )

        viewModelScope.launch {
            validationEventChannel.send(ValidationSbaTextField.Success)
        }
    }

    sealed class ValidationSbaTextField {
        object Success : ValidationSbaTextField()
    }
}