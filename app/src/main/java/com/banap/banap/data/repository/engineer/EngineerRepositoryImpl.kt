package com.banap.banap.data.repository.engineer

import com.banap.banap.data.model.engineer.EngineerResponse
import com.banap.banap.data.remote.engineer.EngineerService
import com.banap.banap.domain.model.engineer.AssociateProducerRequest
import com.banap.banap.domain.model.engineer.EngineerRequest
import com.banap.banap.domain.repository.engineer.EngineerRepository
import retrofit2.Response
import javax.inject.Inject

class EngineerRepositoryImpl @Inject constructor(
    private val service: EngineerService
) : EngineerRepository {
    override suspend fun createEngineer(engineer: EngineerRequest): Response<EngineerResponse> {
        return service.createEngineer(engineer)
    }

    override suspend fun getEngineerById(): Response<EngineerResponse> {
        return service.getEngineerById()
    }

    override suspend fun updateEngineer(engineer: EngineerRequest): Response<Unit> {
        return service.updateEngineer(engineer)
    }

    override suspend fun deleteEngineer(): Response<Unit> {
        return service.deleteEngineer()
    }

    override suspend fun associateProducer(producerEmail: AssociateProducerRequest): Response<Unit> {
        return service.associateProducer(producerEmail)
    }
}