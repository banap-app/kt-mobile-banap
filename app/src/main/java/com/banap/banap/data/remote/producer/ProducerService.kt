package com.banap.banap.data.remote.producer

import com.banap.banap.data.model.producer.CreateProducerResponse
import com.banap.banap.domain.model.producer.CreateProducerRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ProducerService {
    @POST("/producer")
    suspend fun createProducer(@Body producer: CreateProducerRequest): CreateProducerResponse
}