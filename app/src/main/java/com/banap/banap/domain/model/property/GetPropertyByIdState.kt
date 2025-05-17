package com.banap.banap.domain.model.property

import com.banap.banap.data.model.property.ListPropertiesResponse

data class GetPropertyByIdState (
    val isLoading: Boolean = false,
    val response: ListPropertiesResponse? = null,
    val error: String = ""
)