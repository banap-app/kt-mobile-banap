package com.banap.banap.app.presentation.task.ui.registration.components

import androidx.compose.foundation.Image
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.banap.banap.R
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowTimePickerDialog(
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true
    )

    var showDial by remember { mutableStateOf(true) }

    val toggleIcon = if (showDial) {
        R.drawable.baseline_edit_calendar_24
    } else {
        R.drawable.baseline_access_time_24
    }

    TimePickerDialog(
        onDismiss = {
            onDismiss()
        },
        onConfirm = {
            onConfirm(timePickerState)
        },
        toggle = {
            IconButton(
                onClick = {
                    showDial = !showDial
                }
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = toggleIcon),
                    contentDescription = "Toggle time picker"
                )
            }
        }
    ) {
        if (showDial) {
            TimePicker(
                state = timePickerState
            )
        } else {
            TimeInput(
                state = timePickerState
            )
        }
    }
}