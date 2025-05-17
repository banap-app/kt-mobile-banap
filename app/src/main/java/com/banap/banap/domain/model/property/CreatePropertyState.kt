package com.banap.banap.domain.model.property

import com.banap.banap.data.model.property.CreatePropertyResponse

data class CreatePropertyState(
    val isLoading: Boolean = false,
    val response: CreatePropertyResponse? = null,
    val error: String = ""
)