package com.banap.banap.core.ui.util

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun ISOConverter (iso: String) : String {
    val instant = Instant.parse(iso)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.systemDefault())
    val formattedDate = formatter.format(instant)

    return formattedDate
}