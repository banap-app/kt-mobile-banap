package com.banap.banap.app.presentation.validation.spacing.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import com.banap.banap.app.presentation.validation.spacing.event.SpaceTextFieldFormEvent
import com.banap.banap.domain.use_case.validation.ValidateSpace
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LineSpaceTextFieldViewModel (
    private val validateSpace: ValidateSpace = ValidateSpace()
) : ViewModel() {
    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationSpaceTextField>()
    val validationEventLineSpace = validationEventChannel.receiveAsFlow()

    fun onEvent(event: SpaceTextFieldFormEvent) {
        when (event) {
            is SpaceTextFieldFormEvent.SpaceChanged -> {
                state = state.copy(
                    lineSpace = event.space
                )
            }

            is SpaceTextFieldFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val spaceResult = validateSpace.execute(state.lineSpace)

        if (!spaceResult.successful) {
            state = state.copy(
                lineSpaceError = spaceResult.errorMessage
            )
            return
        }

        state = state.copy(
            lineSpaceError = null
        )

        viewModelScope.launch {
            validationEventChannel.send(ValidationSpaceTextField.Success)
        }
    }

    sealed class ValidationSpaceTextField {
        object Success : ValidationSpaceTextField()
    }
}