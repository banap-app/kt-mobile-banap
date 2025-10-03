package com.banap.banap.core.ui.util

import com.banap.banap.domain.model.field.FieldBoundary
import com.google.android.gms.maps.model.LatLng

fun LatLng.convertLatLngToFieldBoundary(): FieldBoundary {
    return FieldBoundary(lat = this.latitude, lng = this.longitude)
}