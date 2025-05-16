package com.banap.banap.data.remote.producer

import com.banap.banap.data.model.producer.ProducerResponse
import com.banap.banap.domain.model.producer.ProducerRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ProducerService {
    @POST("/producer")
    suspend fun createProducer(@Body producer: ProducerRequest): ProducerResponse
}