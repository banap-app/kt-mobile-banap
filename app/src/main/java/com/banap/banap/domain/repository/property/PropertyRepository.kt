package com.banap.banap.domain.repository.property

import com.banap.banap.data.model.property.CreatePropertyResponse
import com.banap.banap.data.model.property.ListPropertiesResponse
import com.banap.banap.domain.model.property.CreatePropertyRequest
import com.banap.banap.domain.model.property.UpdatePropertyRequest

interface PropertyRepository {
    suspend fun createProperty(property: CreatePropertyRequest): CreatePropertyResponse
    suspend fun listProperties(): List<ListPropertiesResponse>
    suspend fun getPropertyById(id: String): ListPropertiesResponse
    suspend fun updateProperty(property: UpdatePropertyRequest)
    suspend fun deleteProperty(id: String)
}