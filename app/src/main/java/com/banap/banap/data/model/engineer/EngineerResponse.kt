package com.banap.banap.data.model.engineer

data class EngineerResponse (
    val statusCode: Int,
    val message: String,
    val data: Data? = null,
    val errors: List<Errors>? = null
)