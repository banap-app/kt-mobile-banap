package com.banap.banap.domain.model.producer

data class CreateProducerRequest(
    val name: String,
    val email: String,
    val password: String,
    val isActive: Boolean = true
)
