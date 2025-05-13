package com.banap.banap.domain.repository.login

import com.banap.banap.data.model.login.LoginResponse
import com.banap.banap.data.model.token.TokenVerificationResponse

interface LoginRepository {
    suspend fun authenticationUser(email: String, password: String): LoginResponse
    suspend fun verifyToken(token: String): TokenVerificationResponse
}