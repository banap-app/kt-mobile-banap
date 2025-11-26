package com.banap.banap.app.presentation.validation.spacing.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import com.banap.banap.app.presentation.validation.spacing.viewmodel.LineSpaceTextFieldViewModel

@Composable
fun validationDataLineSpace(
    context: Context,
    viewModelLineSpace: LineSpaceTextFieldViewModel,
    stateLineSpace: RegistrationFormState
) : Boolean {
    var spacingSuccess by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = context) {
        viewModelLineSpace.validationEventLineSpace.collect { event ->
            when (event) {
                is LineSpaceTextFieldViewModel.ValidationSpaceTextField.Success -> {
                    spacingSuccess = true
                }
            }
        }
    }

    if (stateLineSpace.lineSpaceError != null) {
        spacingSuccess = false
    }

    return spacingSuccess
}