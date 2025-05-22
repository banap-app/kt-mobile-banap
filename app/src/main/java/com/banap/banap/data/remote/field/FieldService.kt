package com.banap.banap.data.remote.field

import com.banap.banap.domain.model.field.CreateFieldRequest
import com.banap.banap.domain.model.field.CreateFieldResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface FieldService {
    @POST("/field")
    suspend fun createField(@Body field: CreateFieldRequest): CreateFieldResponse
}