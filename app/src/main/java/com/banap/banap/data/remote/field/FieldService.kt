package com.banap.banap.data.remote.field

import com.banap.banap.domain.model.field.CreateFieldRequest
import com.banap.banap.data.model.field.FieldResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FieldService {
    @POST("/field")
    suspend fun createField(@Body field: CreateFieldRequest): FieldResponse

    @GET("/field")
    suspend fun listFields(@Query("id") id: String): List<FieldResponse>

    @GET("/field/{id}")
    suspend fun getFieldById(@Path("id") id: String): FieldResponse
}