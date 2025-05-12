package com.banap.banap.core.ui.util

import android.annotation.SuppressLint
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@SuppressLint("NewApi")
fun String.isValidHourMinute() : Boolean {
    return try {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        LocalTime.parse(this, formatter)
        true
    } catch (e: DateTimeParseException) {
        false
    }
}