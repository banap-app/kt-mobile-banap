package com.banap.banap.app.presentation.validation.password.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import com.banap.banap.app.presentation.validation.password.viewmodel.NewPasswordTextFieldViewModel

@Composable
fun validationDataNewPassword(
    context: Context,
    viewModelPassword: NewPasswordTextFieldViewModel,
    statePassword: RegistrationFormState
): Boolean {
    var passwordSuccess by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = context) {
        viewModelPassword.validatioEventNewPassword.collect { event ->
            when (event) {
                is NewPasswordTextFieldViewModel.ValidationNewPasswordTextField.Success -> {
                    passwordSuccess = true
                }
            }
        }
    }

    if (statePassword.newPasswordError != null) {
        passwordSuccess = false
    }

    return passwordSuccess
}