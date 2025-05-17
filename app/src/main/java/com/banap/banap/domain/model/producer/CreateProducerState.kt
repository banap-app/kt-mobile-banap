package com.banap.banap.domain.model.producer

import com.banap.banap.data.model.producer.CreateProducerResponse

data class CreateProducerState (
    val isLoading: Boolean = false,
    val response: CreateProducerResponse? = null,
    val error: String = ""
)