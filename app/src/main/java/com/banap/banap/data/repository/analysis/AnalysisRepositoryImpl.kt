package com.banap.banap.data.repository.analysis

import com.banap.banap.data.model.analysis.AnalysisResponse
import com.banap.banap.data.model.analysis.CreateAnalysisRequest
import com.banap.banap.data.model.analysis.ListAnalysisResponse
import com.banap.banap.data.remote.analysis.AnalysisService
import com.banap.banap.domain.repository.analysis.AnalysisRepository
import javax.inject.Inject

class AnalysisRepositoryImpl @Inject constructor(
    private val service: AnalysisService
) : AnalysisRepository {
    override suspend fun createAnalysis(analysis: CreateAnalysisRequest): AnalysisResponse {
        return service.createAnalysis(analysis)
    }

    override suspend fun listAnalysis(id: String): ListAnalysisResponse {
        return service.listAnalysis(id)
    }

    override suspend fun getAnalysisById(id: String): AnalysisResponse {
        return service.getAnalysisById(id)
    }

    override suspend fun deleteAnalysis(id: String) {
        return service.deleteAnalysis(id)
    }
}