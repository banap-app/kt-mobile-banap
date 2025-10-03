package com.banap.banap.domain.use_case.property

import com.banap.banap.common.Resource
import com.banap.banap.data.model.producer.WithoutResponse
import com.banap.banap.data.repository.property.PropertyRepositoryImpl
import com.banap.banap.domain.model.property.UpdatePropertyRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class UpdatePropertyUseCase @Inject constructor(
    private val repository: PropertyRepositoryImpl
) {
    operator fun invoke(propertyId: String, name: String): Flow<Resource<WithoutResponse>> = flow {
        try {
            emit(Resource.Loading())
            repository.updateProperty(UpdatePropertyRequest(propertyId, name))
            emit(Resource.Success(WithoutResponse(success = true)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Um erro inesperado aconteceu"))
        } catch (e: IOException) {
            emit(Resource.Error("Não foi possível acessar o servidor. Verifique sua conexão com a internet"))
        }
    }
}