package com.banap.banap.domain.use_case.analysis

import com.banap.banap.common.Resource
import com.banap.banap.data.model.analysis.AnalysisResponse
import com.banap.banap.data.model.analysis.CreateAnalysisRequest
import com.banap.banap.data.model.analysis.TypeAnalysis
import com.banap.banap.data.repository.analysis.AnalysisRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class CreateAnalysisUseCase @Inject constructor(
    private val repository: AnalysisRepositoryImpl
) {
    operator fun invoke (
        fieldId: String,
        typeAnalysis: TypeAnalysis
    ) : Flow<Resource<AnalysisResponse>> = flow {
        try {
            emit(Resource.Loading())
            val analysisResponse = repository.createAnalysis(
                CreateAnalysisRequest(
                    fieldId = fieldId,
                    typeAnalysis = typeAnalysis
                )
            )
            emit(Resource.Success(analysisResponse))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Um erro inesperado aconteceu"))
        } catch (e: IOException) {
            emit(Resource.Error("Não foi possível acessar o servidor. Verifique sua conexão com a internet"))
        }
    }
}