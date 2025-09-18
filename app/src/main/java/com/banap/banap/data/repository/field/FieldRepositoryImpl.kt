package com.banap.banap.data.repository.field

import com.banap.banap.data.remote.field.FieldService
import com.banap.banap.domain.model.field.CreateFieldRequest
import com.banap.banap.data.model.field.FieldResponse
import com.banap.banap.domain.repository.field.FieldRepository
import javax.inject.Inject

class FieldRepositoryImpl @Inject constructor(
    private val service: FieldService
) : FieldRepository {
    override suspend fun createField(field: CreateFieldRequest): FieldResponse {
        return service.createField(field)
    }

    override suspend fun listFields(id: String): List<FieldResponse> {
        return service.listFields(id)
    }

    override suspend fun getFieldById(id: String): FieldResponse {
        return service.getFieldById(id)
    }

    override suspend fun updateField(id: String, field: CreateFieldRequest) {
        return service.updateField(id, field)
    }

    override suspend fun deleteField(id: String) {
        return service.deleteField(id)
    }
}