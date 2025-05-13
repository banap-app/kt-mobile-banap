package com.banap.banap.domain.repository.weather

import com.banap.banap.data.model.weather.WeatherResponse

interface WeatherRepository {
    suspend fun getCurrentWeather(city: String, apiKey: String): WeatherResponse
}