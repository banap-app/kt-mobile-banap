package com.banap.banap.data.model.property

data class ListPropertiesResponse (
    val id: String,
    val producerId: ProducerId,
    val name: String,
    val isActive: Boolean
)