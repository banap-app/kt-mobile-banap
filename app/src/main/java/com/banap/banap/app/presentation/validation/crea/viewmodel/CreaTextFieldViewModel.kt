package com.banap.banap.app.presentation.validation.crea.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.domain.use_case.validation.ValidateCrea
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import com.banap.banap.app.presentation.validation.crea.event.CreaTextFieldFormEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CreaTextFieldViewModel (
    private val validateCrea: ValidateCrea = ValidateCrea()
) : ViewModel() {
    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationCreaTextField>()
    val validationEventCrea = validationEventChannel.receiveAsFlow()

    fun onEvent (event: CreaTextFieldFormEvent) {
        when (event) {
            is CreaTextFieldFormEvent.CreaChanged -> {
                state = state.copy(
                    crea = event.crea,
                    creaError = null
                )
            }

            is CreaTextFieldFormEvent.SetError -> {
                state = state.copy(
                    creaError = event.errorMessage
                )
            }

            is CreaTextFieldFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val creaResult = validateCrea.execute(state.crea)

        if (!creaResult.successful) {
            state = state.copy(
                creaError = creaResult.errorMessage
            )
            return
        }

        state = state.copy(
            creaError = null
        )

        viewModelScope.launch {
            validationEventChannel.send(ValidationCreaTextField.Success)
        }
    }

    sealed class ValidationCreaTextField {
        object Success : ValidationCreaTextField()
    }
}