package com.banap.banap.data.model.token

data class TokenVerificationResponse (
    val decodedToken: String,
    val success: Boolean
)