package com.banap.banap.data.repository.producer

import com.banap.banap.data.model.producer.CreateProducerResponse
import com.banap.banap.data.remote.producer.ProducerService
import com.banap.banap.domain.model.producer.CreateProducerRequest
import com.banap.banap.domain.repository.producer.ProducerRepository
import javax.inject.Inject

class ProducerRepositoryImpl @Inject constructor(
    private val service: ProducerService
) : ProducerRepository {
    override suspend fun createProducer(producer: CreateProducerRequest): CreateProducerResponse {
        return service.createProducer(producer)
    }
}