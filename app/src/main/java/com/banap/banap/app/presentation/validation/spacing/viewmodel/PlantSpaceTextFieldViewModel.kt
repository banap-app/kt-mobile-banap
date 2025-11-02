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

class PlantSpaceTextFieldViewModel (
    private val validateSpace: ValidateSpace = ValidateSpace()
) : ViewModel() {
    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationSpaceTextField>()
    val validationEventPlantSpace = validationEventChannel.receiveAsFlow()

    fun onEvent (event: SpaceTextFieldFormEvent) {
        when (event) {
            is SpaceTextFieldFormEvent.SpaceChanged -> {
                state = state.copy(
                    plantSpace = event.space
                )
            }

            is SpaceTextFieldFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val spaceResult = validateSpace.execute(state.plantSpace)

        if (!spaceResult.successful) {
            state = state.copy(
                plantSpaceError = spaceResult.errorMessage
            )
            return
        }

        state = state.copy(
            plantSpaceError = null
        )

        viewModelScope.launch {
            validationEventChannel.send(ValidationSpaceTextField.Success)
        }
    }

    sealed class ValidationSpaceTextField {
        object Success : ValidationSpaceTextField()
    }
}