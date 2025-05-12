package com.banap.banap.app.presentation.validation.scheduling.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.banap.banap.core.ui.util.isValidHourMinute

@Composable
fun validationDataScheduling(
    startTime: String,
    endTime: String
) : Boolean {
    var schedulingSuccess by remember {
        mutableStateOf(false)
    }

    if (startTime.isNotEmpty() && endTime.isNotEmpty() && startTime.isValidHourMinute() && endTime.isValidHourMinute()) {
        schedulingSuccess = true
    }

    return schedulingSuccess
}