package com.banap.banap.data.model.analysis

data class AnalysisResponse(
    val id: String,
    val fieldId: String,
    val typeAnalysis: TypeAnalysisResponse,
    val isActive: Boolean = true,
    val createdAt: String,
    val updatedAt: String
)