package com.banap.banap.domain.model.producer

data class ProducerRequest(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val isActive: Boolean = true,
    val typeUser: Int = 2
)
