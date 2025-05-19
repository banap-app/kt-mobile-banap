package com.banap.banap.domain.use_case.property

import com.banap.banap.common.Resource
import com.banap.banap.data.model.property.ListPropertiesResponse
import com.banap.banap.data.repository.property.PropertyRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ListPropertiesUseCase @Inject constructor(
    private val repository: PropertyRepositoryImpl
) {
    operator fun invoke(producerId: String) : Flow<Resource<List<ListPropertiesResponse>>> = flow {
        try {
            emit(Resource.Loading())
            val properties = repository.listProperties(producerId)
            emit(Resource.Success(properties))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Um erro inesperado aconteceu"))
        } catch (e: IOException) {
            emit(Resource.Error("Não foi possível acessar o servidor. Verifique sua conexão com a internet"))
        }
    }
}