package com.banap.banap.data.repository.producer

import com.banap.banap.data.model.producer.ProducerResponse
import com.banap.banap.data.remote.producer.ProducerService
import com.banap.banap.domain.model.producer.ProducerRequest
import com.banap.banap.domain.repository.producer.ProducerRepository
import javax.inject.Inject

class ProducerRepositoryImpl @Inject constructor(
    private val service: ProducerService
) : ProducerRepository {
    override suspend fun createProducer(producer: ProducerRequest): ProducerResponse {
        return service.createProducer(producer)
    }

    override suspend fun getProducerById(): ProducerResponse {
        return service.getProducerById()
    }

    override suspend fun updateProducer(producer: ProducerRequest) {
        return service.updateProducer(producer)
    }

    override suspend fun deleteProducer(id: String) {
        return service.deleteProducer(id)
    }
}