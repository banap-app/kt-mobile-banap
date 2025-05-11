package com.banap.banap.app.presentation.validation.dropdown.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.app.presentation.validation.dropdown.event.DropdownTextFieldFormEvent
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import com.banap.banap.domain.use_case.validation.ValidateOption
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DropdownPriorityTextFieldViewModel (
    private val validateOption: ValidateOption = ValidateOption()
) : ViewModel() {
    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationOptionPriorityTextField>()
    val validationEventPriorityOption = validationEventChannel.receiveAsFlow()

    fun onEvent (event: DropdownTextFieldFormEvent) {
        when (event) {
            is DropdownTextFieldFormEvent.OptionChanged -> {
                state = state.copy(
                    optionPriority = event.option
                )
            }

            is DropdownTextFieldFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val dropdownResult = validateOption.execute(state.optionPriority)

        if (!dropdownResult.successful) {
            state = state.copy(
                optionPriorityError = dropdownResult.errorMessage
            )
            return
        }

        state = state.copy(
            optionPriorityError = null
        )

        viewModelScope.launch {
            validationEventChannel.send(ValidationOptionPriorityTextField.Success)
        }
    }

    sealed class ValidationOptionPriorityTextField {
        object Success : ValidationOptionPriorityTextField()
    }
}