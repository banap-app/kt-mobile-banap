package com.banap.banap.domain.model.field

import com.banap.banap.data.model.field.FieldResponse

data class ListFieldsState (
    val isLoading: Boolean = false,
    val response: List<FieldResponse>? = null,
    val error: String = ""
)