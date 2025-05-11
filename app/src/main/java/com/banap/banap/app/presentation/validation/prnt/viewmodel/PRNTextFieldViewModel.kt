package com.banap.banap.app.presentation.validation.prnt.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import com.banap.banap.app.presentation.validation.prnt.event.PRNTextFieldFormEvent
import com.banap.banap.domain.use_case.validation.ValidatePrnt
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PRNTextFieldViewModel (
    private val validatePrnt: ValidatePrnt = ValidatePrnt()
) : ViewModel() {
    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationPrntTextField>()
    val validationEventPrnt = validationEventChannel.receiveAsFlow()

    fun onEvent (event: PRNTextFieldFormEvent) {
        when (event) {
            is PRNTextFieldFormEvent.PRNTChanged -> {
                state = state.copy(
                    prnt = event.prnt
                )
            }

            is PRNTextFieldFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val prntResult = validatePrnt.execute(state.prnt)

        if (!prntResult.successful) {
            state = state.copy(
                prntError = prntResult.errorMessage
            )
            return
        }

        state = state.copy(
            prntError = null
        )

        viewModelScope.launch {
            validationEventChannel.send(ValidationPrntTextField.Success)
        }
    }

    sealed class ValidationPrntTextField {
        object Success : ValidationPrntTextField()
    }
}