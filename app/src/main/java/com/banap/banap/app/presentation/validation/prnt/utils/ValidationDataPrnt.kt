package com.banap.banap.app.presentation.validation.prnt.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import com.banap.banap.app.presentation.validation.prnt.viewmodel.PRNTextFieldViewModel

@Composable
fun validationDataPrnt(
    context: Context,
    viewModelPrnt: PRNTextFieldViewModel,
    statePrnt: RegistrationFormState
) : Boolean {
    var prntSuccess by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = context) {
        viewModelPrnt.validationEventPrnt.collect { event ->
            when (event) {
                is PRNTextFieldViewModel.ValidationPrntTextField.Success -> {
                    prntSuccess = true
                }
            }
        }
    }

    if (statePrnt.prntError != null) {
        prntSuccess = false
    }

    return prntSuccess
}