package com.banap.banap.data.remote.login

import com.banap.banap.domain.model.login.LoginRequest
import com.banap.banap.data.model.login.LoginResponse
import com.banap.banap.data.model.token.TokenVerificationResponse
import com.banap.banap.domain.model.token.TokenVerificationRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
     @POST("/api/auth/user/login")
     suspend fun authenticationUser(@Body loginRequest: LoginRequest): LoginResponse

     @POST("/api/auth/verify")
     suspend fun verifyToken(@Body verifyRequest: TokenVerificationRequest): TokenVerificationResponse
}