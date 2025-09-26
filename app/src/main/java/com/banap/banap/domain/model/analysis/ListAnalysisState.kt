package com.banap.banap.domain.model.analysis

import com.banap.banap.data.model.analysis.ListAnalysisResponse

data class ListAnalysisState(
    val isLoading: Boolean = false,
    val response: ListAnalysisResponse? = null,
    val error: String = ""
)
