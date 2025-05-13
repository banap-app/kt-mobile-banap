package com.banap.banap.domain.model.token

import com.banap.banap.data.model.token.TokenVerificationResponse

data class TokenVerificationState(
    val isLoading: Boolean = false,
    val response: TokenVerificationResponse? = null,
    val error: String = ""
)