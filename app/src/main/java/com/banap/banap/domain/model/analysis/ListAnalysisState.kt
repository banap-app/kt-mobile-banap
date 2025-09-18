package com.banap.banap.domain.model.analysis

import com.banap.banap.data.model.analysis.AnalysisResponse

data class ListAnalysisState(
    val isLoading: Boolean = false,
    val response: List<AnalysisResponse>? = null,
    val error: String = ""
)
