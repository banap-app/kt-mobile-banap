package com.banap.banap.domain.model.login

import com.banap.banap.data.model.login.LoginResponse

data class LoginState(
    val isLoading: Boolean = false,
    val response: LoginResponse? = null,
    val error: String = ""
)