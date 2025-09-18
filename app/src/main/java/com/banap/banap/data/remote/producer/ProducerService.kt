package com.banap.banap.data.remote.producer

import com.banap.banap.data.model.producer.ProducerResponse
import com.banap.banap.domain.model.producer.ProducerRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ProducerService {
    @POST("/producer")
    suspend fun createProducer(@Body producer: ProducerRequest): ProducerResponse

    @GET("/producer")
    suspend fun getProducerById(): ProducerResponse

    @PATCH("/producer/{id}")
    suspend fun updateProducer(@Body producer: ProducerRequest)

    @DELETE("/producer/{id}")
    suspend fun deleteProducer(@Path("id") id: String)
}