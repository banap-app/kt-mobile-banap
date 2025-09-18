package com.banap.banap.domain.model.analysis

import com.banap.banap.data.model.analysis.AnalysisResponse

data class AnalysisState (
    val isLoading: Boolean = false,
    val response: AnalysisResponse? = null,
    val error: String = ""
)