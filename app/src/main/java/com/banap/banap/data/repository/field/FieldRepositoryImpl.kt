package com.banap.banap.data.repository.field

import com.banap.banap.data.remote.field.FieldService
import com.banap.banap.domain.model.field.CreateFieldRequest
import com.banap.banap.domain.model.field.CreateFieldResponse
import com.banap.banap.domain.repository.field.FieldRepository
import javax.inject.Inject

class FieldRepositoryImpl @Inject constructor(
    private val service: FieldService
) : FieldRepository {
    override suspend fun createField(field: CreateFieldRequest): CreateFieldResponse {
        return service.createField(field)
    }
}