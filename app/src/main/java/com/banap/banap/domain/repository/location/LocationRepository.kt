package com.banap.banap.domain.repository.location

import com.banap.banap.data.model.location.Location

interface LocationRepository {
    suspend fun getCurrentLocation(): Location
}