package com.banap.banap.domain.model.login

data class LoginRequest(
    val email: String,
    val password: String,
    val typeUser: Int
)