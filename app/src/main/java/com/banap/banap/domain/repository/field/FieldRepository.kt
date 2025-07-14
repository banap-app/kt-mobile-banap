package com.banap.banap.domain.repository.field

import com.banap.banap.domain.model.field.CreateFieldRequest
import com.banap.banap.data.model.field.FieldResponse

interface FieldRepository {
    suspend fun createField(field: CreateFieldRequest): FieldResponse
    suspend fun listFields(id: String): List<FieldResponse>
    suspend fun getFieldById(id: String): FieldResponse
}