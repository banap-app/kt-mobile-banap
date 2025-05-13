package com.banap.banap.data.remote.weather

import com.banap.banap.data.model.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("lang") language: String = "pt_br",
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String
    ): WeatherResponse
}