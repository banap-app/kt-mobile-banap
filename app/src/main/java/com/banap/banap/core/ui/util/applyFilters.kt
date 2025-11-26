package com.banap.banap.core.ui.util

import com.banap.banap.data.model.analysis.AnalysisResponse
import com.banap.banap.data.model.analysis.FilterOptions

fun applyFilters(list: List<AnalysisResponse>, filters: FilterOptions): List<AnalysisResponse> {
    return list.filter { analysis ->
        (filters.type == null || analysis.typeAnalysis.run {
            (filters.type == "LIMING" && analysisLimingId != null) ||
                    (filters.type == "NPK" && analysisNpkId != null)
        }) &&
                (filters.minPotassium == null || (analysis.typeAnalysis.potassium ?: 0.0) >= filters.minPotassium) &&
                (filters.maxPotassium == null || (analysis.typeAnalysis.potassium ?: 0.0) <= filters.maxPotassium)
    }
}