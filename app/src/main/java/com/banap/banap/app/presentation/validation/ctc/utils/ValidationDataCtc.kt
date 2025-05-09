package com.banap.banap.app.presentation.validation.ctc.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.banap.banap.app.presentation.validation.ctc.viewmodel.CTCTextFieldViewModel
import com.banap.banap.app.presentation.validation.model.RegistrationFormState

@Composable
fun validationDataCtc(
    context: Context,
    viewModelCtc: CTCTextFieldViewModel,
    stateCtc: RegistrationFormState
) : Boolean {
    var ctcSuccess by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = context) {
        viewModelCtc.validationEventCtc.collect { event ->
            when (event) {
                is CTCTextFieldViewModel.ValidationCtcTextField.Success -> {
                    ctcSuccess = true
                }
            }
        }
    }

    if (stateCtc.ctcError != null) {
        ctcSuccess = false
    }

    return ctcSuccess
}