package com.banap.banap.domain.use_case.property

import com.banap.banap.common.Resource
import com.banap.banap.data.model.property.CreatePropertyResponse
import com.banap.banap.data.repository.property.PropertyRepositoryImpl
import com.banap.banap.domain.model.property.CreatePropertyRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CreatePropertyUseCase @Inject constructor(
    private val repository: PropertyRepositoryImpl
) {
    operator fun invoke(producerId: String, name: String) : Flow<Resource<CreatePropertyResponse>> = flow {
        try {
            emit(Resource.Loading())
            val propertyResponse = repository.createProperty(
                CreatePropertyRequest(
                    producerId = producerId,
                    name = name
                )
            )
            emit(Resource.Success(propertyResponse))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Um erro inesperado aconteceu"))
        } catch (e: IOException) {
            emit(Resource.Error("Não foi possível acessar o servidor. Verifique sua conexão com a internet"))
        }
    }
}