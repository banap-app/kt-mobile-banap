package com.banap.banap.app.presentation.validation.dropdown.utils

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import com.banap.banap.app.presentation.validation.dropdown.viewmodel.DropdownTextFieldViewModel

@Composable
fun validationDataDropdown (
    context: Context,
    viewModelDropdown: DropdownTextFieldViewModel,
    stateDropdown: RegistrationFormState
) : Boolean {
    var dropdownSuccess by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = context) {
        viewModelDropdown.validationEventOption.collect { event ->
            when (event) {
                is DropdownTextFieldViewModel.ValidationOptionTextField.Success -> {
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