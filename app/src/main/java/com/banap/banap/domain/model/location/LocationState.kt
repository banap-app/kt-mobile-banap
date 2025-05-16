package com.banap.banap.domain.model.location

import com.banap.banap.data.model.location.Location

data class LocationState (
    val isLoading: Boolean = false,
    val response: Location? = null,
    val error: String = ""
)