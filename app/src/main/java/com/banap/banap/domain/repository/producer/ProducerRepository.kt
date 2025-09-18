package com.banap.banap.domain.repository.producer

import com.banap.banap.data.model.producer.ProducerResponse
import com.banap.banap.domain.model.producer.ProducerRequest

interface ProducerRepository {
    suspend fun createProducer(producer: ProducerRequest): ProducerResponse
    suspend fun getProducerById(): ProducerResponse
    suspend fun updateProducer(producer: ProducerRequest)
    suspend fun deleteProducer(id: String)
}