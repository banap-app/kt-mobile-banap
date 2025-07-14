package com.banap.banap.domain.use_case.field

import com.banap.banap.common.Resource
import com.banap.banap.data.model.field.FieldResponse
import com.banap.banap.data.repository.field.FieldRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetFieldByIdUseCase @Inject constructor(
    private val repository: FieldRepositoryImpl
) {
    operator fun invoke(id: String): Flow<Resource<FieldResponse>> = flow {
        try {
            emit(Resource.Loading())
            val field = repository.getFieldById(id)
            emit(Resource.Success(field))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Um erro inesperado aconteceu"))
        } catch (e: IOException) {
            emit(Resource.Error("Não foi possível acessar o servidor. Verifique sua conexão com a internet"))
        }
    }
}