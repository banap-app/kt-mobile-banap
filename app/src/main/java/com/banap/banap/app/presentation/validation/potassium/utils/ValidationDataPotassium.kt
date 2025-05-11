package com.banap.banap.app.presentation.validation.potassium.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import com.banap.banap.app.presentation.validation.potassium.viewmodel.PotassiumTextFieldViewModel

@Composable
fun validationDataPotassium(
    context: Context,
    viewModelPotassium: PotassiumTextFieldViewModel,
    statePotassium: RegistrationFormState
) : Boolean {
    var potassiumSuccess by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = context) {
        viewModelPotassium.validationEventPotassium.collect { event ->
            when (event) {
                is PotassiumTextFieldViewModel.ValidationPotassiumTextField.Success -> {
                    potassiumSuccess = true
                }
            }
        }
    }

    if (statePotassium.potassiumError != null) {
        potassiumSuccess = false
    }

    return potassiumSuccess
}