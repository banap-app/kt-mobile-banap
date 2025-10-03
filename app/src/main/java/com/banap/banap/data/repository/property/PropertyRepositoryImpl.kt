package com.banap.banap.data.repository.property

import com.banap.banap.data.model.property.CreatePropertyResponse
import com.banap.banap.data.model.property.ListPropertiesResponse
import com.banap.banap.data.remote.property.PropertyService
import com.banap.banap.domain.model.property.CreatePropertyRequest
import com.banap.banap.domain.model.property.UpdatePropertyRequest
import com.banap.banap.domain.repository.property.PropertyRepository
import javax.inject.Inject

class PropertyRepositoryImpl @Inject constructor(
    private val service: PropertyService
) : PropertyRepository {
    override suspend fun createProperty(property: CreatePropertyRequest): CreatePropertyResponse {
        return service.createProperty(property)
    }

    override suspend fun listProperties(): List<ListPropertiesResponse> {
        return service.listProperties()
    }

    override suspend fun getPropertyById(id: String): ListPropertiesResponse {
        return service.getPropertyById(id)
    }

    override suspend fun updateProperty(property: UpdatePropertyRequest) {
        return service.updateProperty(property)
    }

    override suspend fun deleteProperty(id: String) {
        return service.deleteProperty(id)
    }
}