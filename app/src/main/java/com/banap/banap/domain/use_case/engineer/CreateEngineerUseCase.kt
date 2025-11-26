package com.banap.banap.domain.use_case.engineer

import com.banap.banap.common.Resource
import com.banap.banap.data.model.engineer.EngineerResponse
import com.banap.banap.data.repository.engineer.EngineerRepositoryImpl
import com.banap.banap.domain.model.engineer.EngineerRequest
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CreateEngineerUseCase @Inject constructor(
    private val repository: EngineerRepositoryImpl
) {
    operator fun invoke(
        name: String,
        email: String,
        password: String,
        crea: String
    ): Flow<Resource<EngineerResponse>> = flow {
        try {
            emit(Resource.Loading())

            val response = repository.createEngineer(
                EngineerRequest(name, email, password, crea)
            )

            if (response.isSuccessful) {
                response.body()?.let { engineerResponse ->
                    emit(Resource.Success(engineerResponse))
                }
            } else {
                val errorResponse = response.errorBody()?.string()?.let { errorBody ->
                    try {
                        Gson().fromJson(errorBody, EngineerResponse::class.java)
                    } catch (e: Exception) {
                        null
                    }
                }

                emit(
                    Resource.Error(
                        message = errorResponse?.message ?: "Erro: ${response.code()}",
                        data = errorResponse
                    )
                )
            }
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Não foi possível acessar o servidor. Verifique sua conexão com a internet",
                    data = EngineerResponse(
                        statusCode = 500,
                        message = "Não foi possível acessar o servidor. Verifique sua conexão com a internet"
                    )
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: "Um erro inesperado aconteceu",
                    data = EngineerResponse(
                        statusCode = 500,
                        message = e.localizedMessage ?: "Um erro inesperado aconteceu"
                    )
                )
            )
        }
    }
}