package com.banap.banap.data.remote.field

import com.banap.banap.domain.model.field.CreateFieldRequest
import com.banap.banap.data.model.field.FieldResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FieldService {
    @POST("/field")
    suspend fun createField(@Body field: CreateFieldRequest): FieldResponse

    @GET("/field")
    suspend fun listFields(@Query("propertyId") id: String): List<FieldResponse>

    @GET("/field/{id}")
    suspend fun getFieldById(@Path("id") id: String): FieldResponse

    @PATCH("/field/{id}")
    suspend fun updateField(@Path("id") id: String, @Body field: CreateFieldRequest)

    @DELETE("/field/{id}")
    suspend fun deleteField(@Path("id") id: String)
}