package com.banap.banap.domain.repository.field

import com.banap.banap.domain.model.field.CreateFieldRequest
import com.banap.banap.domain.model.field.CreateFieldResponse

interface FieldRepository {
    suspend fun createField(field: CreateFieldRequest): CreateFieldResponse
}