package com.banap.banap.data.remote.engineer

import com.banap.banap.data.model.engineer.EngineerResponse
import com.banap.banap.domain.model.engineer.AssociateProducerRequest
import com.banap.banap.domain.model.engineer.EngineerRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface EngineerService {
    @POST("api/engineer")
    suspend fun createEngineer(@Body engineer: EngineerRequest): Response<EngineerResponse>

    @GET("api/engineer")
    suspend fun getEngineerById(): Response<EngineerResponse>

    @PATCH("api/engineer")
    suspend fun updateEngineer(@Body engineer: EngineerRequest): Response<Unit>

    @DELETE("api/engineer")
    suspend fun deleteEngineer(): Response<Unit>

    @POST("api/engineer/associate-producer")
    suspend fun associateProducer(@Body producerEmail: AssociateProducerRequest): Response<Unit>
}