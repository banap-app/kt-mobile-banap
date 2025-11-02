package com.banap.banap.domain.model.engineer

import com.banap.banap.data.model.engineer.EngineerResponse

data class EngineerState(
    val isLoading: Boolean = false,
    val response: EngineerResponse? = null,
    val errors: EngineerResponse? = null,
    val errorMessage: String? = null
)