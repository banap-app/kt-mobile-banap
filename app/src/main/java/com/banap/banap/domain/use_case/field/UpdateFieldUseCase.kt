package com.banap.banap.domain.use_case.field

import com.banap.banap.common.Resource
import com.banap.banap.data.model.producer.WithoutResponse
import com.banap.banap.data.repository.field.FieldRepositoryImpl
import com.banap.banap.domain.model.field.CreateFieldRequest
import com.banap.banap.domain.model.field.FieldBoundary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class UpdateFieldUseCase @Inject constructor(
    private val repository: FieldRepositoryImpl
) {
    operator fun invoke(
        id: String,
        producerId: String,
        propertyId: String,
        name: String,
        description: String,
        crop: String,
        fieldBoundary: List<FieldBoundary>
    ): Flow<Resource<WithoutResponse>> = flow {
        try {
            emit(Resource.Loading())
            repository.updateField(
                id,
                CreateFieldRequest(
                    producerId = producerId,
                    propertyId = propertyId,
                    name = name,
                    description = description,
                    crop = crop,
                    fieldBoundary = fieldBoundary
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Um erro inesperado aconteceu"))
        } catch (e: IOException) {
            emit(Resource.Error("Não foi possível acessar o servidor. Verifique sua conexão com a internet"))
        }
    }
}