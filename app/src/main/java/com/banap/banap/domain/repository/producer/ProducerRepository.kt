package com.banap.banap.domain.repository.producer

import com.banap.banap.data.model.producer.CreateProducerResponse
import com.banap.banap.domain.model.producer.CreateProducerRequest

interface ProducerRepository {
    suspend fun createProducer(producer: CreateProducerRequest): CreateProducerResponse
}