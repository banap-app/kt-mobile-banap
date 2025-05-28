package com.banap.banap.domain.model.producer

data class ProducerRequest(
    val name: String,
    val email: String,
    val password: String,
    val isActive: Boolean = true,
    val typeUser: Int = 2
)
