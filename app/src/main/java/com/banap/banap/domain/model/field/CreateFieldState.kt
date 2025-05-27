package com.banap.banap.domain.model.field

data class CreateFieldState(
    val isLoading: Boolean = false,
    val response: CreateFieldResponse? = null,
    val error: String = ""
)
