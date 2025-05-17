package com.banap.banap.domain.use_case.property

import com.banap.banap.common.Resource
import com.banap.banap.data.model.property.ListPropertiesResponse
import com.banap.banap.data.repository.property.PropertyRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetPropertyByIdUseCase @Inject constructor(
    private val repository: PropertyRepositoryImpl
) {
    operator fun invoke(id: String) : Flow<Resource<ListPropertiesResponse>> = flow {
        try {
            emit(Resource.Loading())
            val property = repository.getPropertyById(id)
            emit(Resource.Success(property))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Um erro inesperado aconteceu"))
        } catch (e: IOException) {
            emit(Resource.Error("Não foi possível acessar o servidor. Verifique sua conexão com a internet"))
        }
    }
}