package com.banap.banap.domain.model.property

data class CreatePropertyRequest(
    val name: String,
    val isActive: Boolean = true
)
