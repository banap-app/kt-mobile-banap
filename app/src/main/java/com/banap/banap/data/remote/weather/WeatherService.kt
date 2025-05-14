package com.banap.banap.data.remote.weather

import com.banap.banap.data.model.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("lang") language: String = "pt_br",
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String
    ): WeatherResponse
}