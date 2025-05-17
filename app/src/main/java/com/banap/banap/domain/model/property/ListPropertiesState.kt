package com.banap.banap.domain.model.property

import com.banap.banap.data.model.property.ListPropertiesResponse

data class ListPropertiesState (
    val isLoading: Boolean = false,
    val response: List<ListPropertiesResponse>? = null,
    val error: String = ""
)