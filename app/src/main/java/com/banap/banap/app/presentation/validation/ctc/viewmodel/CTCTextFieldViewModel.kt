package com.banap.banap.app.presentation.validation.ctc.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.app.presentation.validation.ctc.event.CTCTextFieldFormEvent
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import com.banap.banap.domain.use_case.validation.ValidateCtc
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CTCTextFieldViewModel (
    private val validateCtc: ValidateCtc = ValidateCtc()
) : ViewModel() {
    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationCtcTextField>()
    val validationEventCtc = validationEventChannel.receiveAsFlow()

    fun onEvent(event: CTCTextFieldFormEvent) {
        when (event) {
            is CTCTextFieldFormEvent.CTChanged -> {
                state = state.copy(
                    ctc = event.ctc
                )
            }

            is CTCTextFieldFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val ctcResult = validateCtc.execute(state.ctc)

        if (!ctcResult.successful) {
            state = state.copy(
                ctcError = ctcResult.errorMessage
            )
            return
        }

        state = state.copy(
            ctcError = null
        )

        viewModelScope.launch {
            validationEventChannel.send(ValidationCtcTextField.Success)
        }
    }

    sealed class ValidationCtcTextField {
        object Success : ValidationCtcTextField()
    }
}