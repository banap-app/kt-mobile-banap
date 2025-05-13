package com.banap.banap.data.repository.login

import com.banap.banap.domain.model.login.LoginRequest
import com.banap.banap.data.model.login.LoginResponse
import com.banap.banap.data.model.token.TokenVerificationResponse
import com.banap.banap.data.remote.login.LoginService
import com.banap.banap.domain.model.token.TokenVerificationRequest
import com.banap.banap.domain.repository.login.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val service: LoginService
) : LoginRepository {
    override suspend fun authenticationUser(email: String, password: String): LoginResponse {
        return service.authenticationUser(
            LoginRequest(email, password, 1)
        )
    }

    override suspend fun verifyToken(token: String): TokenVerificationResponse {
        return service.verifyToken(
            TokenVerificationRequest(token)
        )
    }
}