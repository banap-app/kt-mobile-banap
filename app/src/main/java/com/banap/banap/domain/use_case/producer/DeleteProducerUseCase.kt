package com.banap.banap.domain.use_case.producer

import com.banap.banap.common.Resource
import com.banap.banap.data.model.producer.WithoutResponse
import com.banap.banap.data.repository.producer.ProducerRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class DeleteProducerUseCase @Inject constructor(
    private val repository: ProducerRepositoryImpl
) {
    operator fun invoke(id: String): Flow<Resource<WithoutResponse>> = flow {
        try {
            emit(Resource.Loading())
            repository.deleteProducer(id)
            emit(
                Resource.Success(
                    WithoutResponse(
                        success = true
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Um erro inesperado aconteceu"))
        } catch (e: IOException) {
            emit(Resource.Error("Não foi possível acessar o servidor. Verifique sua conexão com a internet"))
        }
    }
}