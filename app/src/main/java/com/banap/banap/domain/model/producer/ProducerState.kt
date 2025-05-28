package com.banap.banap.domain.model.producer

import com.banap.banap.data.model.producer.ProducerResponse

data class ProducerState (
    val isLoading: Boolean = false,
    val response: ProducerResponse? = null,
    val error: String = ""
)