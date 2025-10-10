package com.banap.banap.data.remote.property

import com.banap.banap.data.model.property.CreatePropertyResponse
import com.banap.banap.data.model.property.ListPropertiesResponse
import com.banap.banap.domain.model.property.CreatePropertyRequest
import com.banap.banap.domain.model.property.UpdatePropertyRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface PropertyService {
    @POST("/property")
    suspend fun createProperty(@Body property: CreatePropertyRequest): CreatePropertyResponse
    
    @GET("/property")
    suspend fun listProperties(): List<ListPropertiesResponse>

    @GET("/property/{id}")
    suspend fun getPropertyById(@Path("id") id: String): ListPropertiesResponse

    @PATCH("/property")
    suspend fun updateProperty(@Body property: UpdatePropertyRequest)

    @DELETE("/property/{id}")
    suspend fun deleteProperty(@Path("id") id: String)
}