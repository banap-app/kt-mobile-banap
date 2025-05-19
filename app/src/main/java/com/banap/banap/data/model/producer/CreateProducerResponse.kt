package com.banap.banap.data.model.producer

data class CreateProducerResponse(
    val id: String,
    val name: String,
    val email: String,
    val isActive: Boolean
)
