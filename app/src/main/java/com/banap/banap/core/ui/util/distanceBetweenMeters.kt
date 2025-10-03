package com.banap.banap.core.ui.util

import com.google.android.gms.maps.model.LatLng
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun distanceBetweenMeters(a: LatLng, b: LatLng): Double {
    val R = 6371000.0
    val dLat = Math.toRadians(b.latitude - a.latitude)
    val dLon = Math.toRadians(b.longitude - a.longitude)
    val lat1 = Math.toRadians(a.latitude)
    val lat2 = Math.toRadians(b.latitude)

    val sinDLat = sin(dLat / 2)
    val sinDLon = sin(dLon / 2)
    val aVal = sinDLat * sinDLat + cos(lat1) * cos(lat2) * sinDLon * sinDLon
    val c = 2 * atan2(sqrt(aVal), sqrt(1 - aVal))
    return R * c
}