package com.banap.banap.data.model.engineer

data class EngineerErrors(
    val statusCode: Int,
    val message: String,
    val errors: Errors? = null
)