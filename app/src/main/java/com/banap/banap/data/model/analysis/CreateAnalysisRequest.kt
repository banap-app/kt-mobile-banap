package com.banap.banap.data.model.analysis

data class CreateAnalysisRequest(
    val fieldId: String,
    val isActive: Boolean = true,
    val typeAnalysis: TypeAnalysis
)