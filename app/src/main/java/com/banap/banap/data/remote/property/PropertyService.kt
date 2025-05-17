package com.banap.banap.data.remote.property

import com.banap.banap.data.model.property.CreatePropertyResponse
import com.banap.banap.domain.model.property.CreatePropertyRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface PropertyService {
    @POST("/property")
    suspend fun createProperty(@Body property: CreatePropertyRequest): CreatePropertyResponse
}