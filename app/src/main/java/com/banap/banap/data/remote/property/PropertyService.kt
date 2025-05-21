package com.banap.banap.data.remote.property

import com.banap.banap.data.model.property.CreatePropertyResponse
import com.banap.banap.data.model.property.ListPropertiesResponse
import com.banap.banap.domain.model.property.CreatePropertyRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PropertyService {
    @POST("/property")
    suspend fun createProperty(@Body property: CreatePropertyRequest): CreatePropertyResponse
    
    @GET("/property")
    suspend fun listProperties(): List<ListPropertiesResponse>

    @GET("/property")
    suspend fun getPropertyById(@Query("id") id: String): ListPropertiesResponse
}