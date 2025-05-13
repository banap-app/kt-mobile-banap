package com.banap.banap.domain.use_case.login

import com.banap.banap.common.Resource
import com.banap.banap.data.model.login.LoginResponse
import com.banap.banap.data.repository.login.LoginRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepositoryImpl
) {
    operator fun invoke(email: String, password: String) : Flow<Resource<LoginResponse>> = flow {
        try {
            emit(Resource.Loading())
            val loginResponse = repository.authenticationUser(email, password)
            emit(Resource.Success(loginResponse))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Um erro inesperado aconteceu"))
        } catch (e: IOException) {
            emit(Resource.Error("Não foi possível acessar o servidor. Verifique sua conexão com a internet"))
        }
    }
}