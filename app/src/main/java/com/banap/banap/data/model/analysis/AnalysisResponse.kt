package com.banap.banap.data.model.analysis

data class AnalysisResponse(
    val id: String,
    val fieldId: String,
    val isActive: Boolean = true,
    val typeAnalysis: TypeAnalysis
)