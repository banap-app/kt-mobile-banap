package com.banap.banap.domain.model.field

import com.banap.banap.data.model.field.FieldResponse

data class FieldState(
    val isLoading: Boolean = false,
    val response: FieldResponse? = null,
    val error: String = ""
)
