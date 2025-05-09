package com.banap.banap.app.presentation.validation.phosphorus.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import com.banap.banap.app.presentation.validation.phosphorus.viewmodel.PhosphorusTextFieldViewModel

@Composable
fun validationDataPhosphorus(
    context: Context,
    viewModelPhosphorus: PhosphorusTextFieldViewModel,
    statePhosphorus: RegistrationFormState
) : Boolean {
    var phosphorusSuccess by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = context) {
        viewModelPhosphorus.validationEventPhosphorus.collect { event ->
            when (event) {
                is PhosphorusTextFieldViewModel.ValidationPhosphorusTextField.Success -> {
                    phosphorusSuccess = true
                }
            }
        }
    }

    if (statePhosphorus.phosphorusError != null) {
        phosphorusSuccess = false
    }

    return phosphorusSuccess
}