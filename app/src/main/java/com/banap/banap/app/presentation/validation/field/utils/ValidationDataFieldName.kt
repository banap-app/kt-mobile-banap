package com.banap.banap.app.presentation.validation.field.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.banap.banap.app.presentation.validation.field.viewmodel.FieldNameTextFieldViewModel
import com.banap.banap.app.presentation.validation.model.RegistrationFormState

@Composable
fun validationDataFieldName (
    context: Context,
    viewModelName: FieldNameTextFieldViewModel,
    stateName: RegistrationFormState
) : Boolean {
    var nameSuccess by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = context) {
        viewModelName.validationEventFieldName.collect { event ->
            when (event) {
                is FieldNameTextFieldViewModel.ValidationEventFieldNameTextField.Success -> {
                    nameSuccess = true
                }
            }
        }
    }

    if (stateName.nameError != null) {
        nameSuccess = false
    }

    return nameSuccess
}