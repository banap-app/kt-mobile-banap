package com.banap.banap.domain.model.field

data class CreateFieldResponse(
    val id: String,
    val propertyId: String,
    val producerId: String,
    val name: String,
    val description: String,
    val fieldBoundary: Points,
    val crop: String,
    val isActive: Boolean
)