package com.banap.banap.data.repository.property

import com.banap.banap.data.model.property.CreatePropertyResponse
import com.banap.banap.data.remote.property.PropertyService
import com.banap.banap.domain.model.property.CreatePropertyRequest
import com.banap.banap.domain.repository.property.PropertyRepository
import javax.inject.Inject

class PropertyRepositoryImpl @Inject constructor(
    private val service: PropertyService
) : PropertyRepository {
    override suspend fun createProperty(property: CreatePropertyRequest): CreatePropertyResponse {
        return service.createProperty(property)
    }
}