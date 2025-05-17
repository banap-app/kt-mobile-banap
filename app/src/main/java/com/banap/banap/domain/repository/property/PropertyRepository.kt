package com.banap.banap.domain.repository.property

import com.banap.banap.data.model.property.CreatePropertyResponse
import com.banap.banap.domain.model.property.CreatePropertyRequest

interface PropertyRepository {
    suspend fun createProperty(property: CreatePropertyRequest): CreatePropertyResponse
}