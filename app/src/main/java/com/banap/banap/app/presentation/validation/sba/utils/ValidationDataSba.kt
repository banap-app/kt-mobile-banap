package com.banap.banap.app.presentation.validation.sba.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import com.banap.banap.app.presentation.validation.sba.viewmodel.SBATextFieldViewModel

@Composable
fun validationDataSba(
    context: Context,
    viewModelSba: SBATextFieldViewModel,
    stateSba: RegistrationFormState
) : Boolean {
    var sbaSuccess by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = context) {
        viewModelSba.validationEventSba.collect { event ->
            when (event) {
                is SBATextFieldViewModel.ValidationSbaTextField.Success -> {
                    sbaSuccess = true
                }
            }
        }
    }

    if (stateSba.sbaError != null) {
        sbaSuccess = false
    }

    return sbaSuccess
}