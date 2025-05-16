package com.banap.banap.domain.use_case.producer

import com.banap.banap.common.Resource
import com.banap.banap.data.model.producer.ProducerResponse
import com.banap.banap.data.repository.producer.ProducerRepositoryImpl
import com.banap.banap.domain.model.producer.ProducerRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CreateProducerUseCase @Inject constructor(
    private val repository: ProducerRepositoryImpl
) {
    operator fun invoke(name: String, email: String, password: String) : Flow<Resource<ProducerResponse>> = flow {
        try {
            emit(Resource.Loading())
            val producerResponse = repository.createProducer(
                ProducerRequest(
                    name,
                    email,
                    password
                )
            )
            emit(Resource.Success(producerResponse))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Um erro inesperado aconteceu"))
        } catch (e: IOException) {
            emit(Resource.Error("Não foi possível acessar o servidor. Verifique sua conexão com a internet"))
        }
    }
}