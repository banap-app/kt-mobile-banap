package com.banap.banap.domain.repository.engineer

import com.banap.banap.data.model.engineer.EngineerResponse
import com.banap.banap.domain.model.engineer.AssociateProducerRequest
import com.banap.banap.domain.model.engineer.EngineerRequest
import retrofit2.Response

interface EngineerRepository {
    suspend fun createEngineer(engineer: EngineerRequest): Response<EngineerResponse>
    suspend fun getEngineerById(): Response<EngineerResponse>
    suspend fun updateEngineer(engineer: EngineerRequest): Response<Unit>
    suspend fun deleteEngineer(): Response<Unit>
    suspend fun associateProducer(producerEmail: AssociateProducerRequest): Response<Unit>
}