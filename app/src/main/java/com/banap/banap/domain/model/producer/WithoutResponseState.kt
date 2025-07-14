package com.banap.banap.domain.model.producer

import com.banap.banap.data.model.producer.WithoutResponse

data class WithoutResponseState (
    val isLoading: Boolean = false,
    val response: WithoutResponse? = null,
    val error: String = ""
)