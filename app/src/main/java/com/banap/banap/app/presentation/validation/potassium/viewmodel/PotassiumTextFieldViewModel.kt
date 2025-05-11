package com.banap.banap.app.presentation.validation.potassium.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import com.banap.banap.app.presentation.validation.potassium.event.PotassiumTextFieldFormEvent
import com.banap.banap.domain.use_case.validation.ValidatePotassium
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PotassiumTextFieldViewModel (
    private val validatePotassium: ValidatePotassium = ValidatePotassium()
) : ViewModel() {
    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationPotassiumTextField>()
    val validationEventPotassium = validationEventChannel.receiveAsFlow()

    fun onEvent (event: PotassiumTextFieldFormEvent) {
        when (event) {
            is PotassiumTextFieldFormEvent.PotassiumChanged -> {
                state = state.copy(
                    potassium = event.potassium
                )
            }

            is PotassiumTextFieldFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val potassiumResult = validatePotassium.execute(state.potassium)

        if (!potassiumResult.successful) {
            state = state.copy(
                potassiumError = potassiumResult.errorMessage
            )
            return
        }

        state = state.copy(
            potassiumError = null
        )

        viewModelScope.launch {
            validationEventChannel.send(ValidationPotassiumTextField.Success)
        }
    }

    sealed class ValidationPotassiumTextField {
        object Success : ValidationPotassiumTextField()
    }
}