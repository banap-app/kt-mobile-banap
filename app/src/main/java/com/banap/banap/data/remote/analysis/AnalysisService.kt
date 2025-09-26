package com.banap.banap.data.remote.analysis

import com.banap.banap.data.model.analysis.AnalysisResponse
import com.banap.banap.data.model.analysis.CreateAnalysisRequest
import com.banap.banap.data.model.analysis.ListAnalysisResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AnalysisService {
    @POST("/analysis")
    suspend fun createAnalysis(@Body analysis: CreateAnalysisRequest): AnalysisResponse

    @GET("/analysis/{id}")
    suspend fun listAnalysis(@Path("id") id: String): ListAnalysisResponse

    @GET("/analysis/by_id/{id}")
    suspend fun getAnalysisById(@Path("id") id: String): AnalysisResponse
}