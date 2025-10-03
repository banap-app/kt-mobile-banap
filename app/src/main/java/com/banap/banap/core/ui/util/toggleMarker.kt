package com.banap.banap.core.ui.util

import com.banap.banap.app.util.enumerator.ToggleResult
import com.google.android.gms.maps.model.LatLng

fun MutableList<LatLng>.toggleMarker(
    newPoint: LatLng,
    maxMarkers: Int,
    toleranceMeters: Double = 2.0
): ToggleResult {
    val idx = indexOfFirst { distanceBetweenMeters(it, newPoint) <= toleranceMeters }

    return when {
        idx >= 0 -> {
            removeAt(idx)
            ToggleResult.REMOVED
        }

        this.size < maxMarkers -> {
            add(newPoint)
            ToggleResult.ADDED
        }

        else -> ToggleResult.LIMIT_REACHED
    }
}