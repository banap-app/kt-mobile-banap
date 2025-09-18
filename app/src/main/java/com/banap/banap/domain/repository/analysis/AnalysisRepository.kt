package com.banap.banap.domain.repository.analysis

import com.banap.banap.data.model.analysis.AnalysisResponse
import com.banap.banap.data.model.analysis.CreateAnalysisRequest

interface AnalysisRepository {
    suspend fun createAnalysis(analysis: CreateAnalysisRequest): AnalysisResponse
    suspend fun listAnalysis(id: String): List<AnalysisResponse>
}