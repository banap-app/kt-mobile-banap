package com.banap.banap.domain.model.field

data class CreateFieldRequest(
    val producerId: String,
    val propertyId: String,
    val name: String,
    val description: String,
    val crop: String,
    val isActive: Boolean = true,
    val fieldBoundary: List<FieldBoundary>
)