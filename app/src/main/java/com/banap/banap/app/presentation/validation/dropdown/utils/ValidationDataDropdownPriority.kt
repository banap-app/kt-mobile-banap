package com.banap.banap.app.presentation.validation.dropdown.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.banap.banap.app.presentation.validation.dropdown.viewmodel.DropdownPriorityTextFieldViewModel
import com.banap.banap.app.presentation.validation.model.RegistrationFormState

@Composable
fun validationDataDropdownPriority (
    context: Context,
    viewModelDropdown: DropdownPriorityTextFieldViewModel,
    stateDropdown: RegistrationFormState
) : Boolean {
    var dropdownSuccess by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = context) {
        viewModelDropdown.validationEventPriorityOption.collect { event ->
            when (event) {
                is DropdownPriorityTextFieldViewModel.ValidationOptionPriorityTextField.Success -> {
                    dropdownSuccess = true
                }
            }
        }
    }

    if (stateDropdown.optionError != null) {
        dropdownSuccess = false
    }

    return dropdownSuccess
}