package com.banap.banap.app.presentation.validation.spacing.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import com.banap.banap.app.presentation.validation.spacing.viewmodel.PlantSpaceTextFieldViewModel

@Composable
fun validationDataPlantSpace(
    context: Context,
    viewModelPlantSpace: PlantSpaceTextFieldViewModel,
    statePlantSpace: RegistrationFormState
) : Boolean {
    var spacingSuccess by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = context) {
        viewModelPlantSpace.validationEventPlantSpace.collect { event ->
            when (event) {
                is PlantSpaceTextFieldViewModel.ValidationSpaceTextField.Success -> {
                    spacingSuccess = true
                }
            }
        }
    }

    if (statePlantSpace.plantSpaceError != null) {
        spacingSuccess = false
    }

    return spacingSuccess
}