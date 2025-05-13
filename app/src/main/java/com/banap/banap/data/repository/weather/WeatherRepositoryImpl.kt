package com.banap.banap.data.repository.weather

import com.banap.banap.data.model.weather.WeatherResponse
import com.banap.banap.data.remote.weather.WeatherService
import com.banap.banap.domain.repository.weather.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val service: WeatherService
) : WeatherRepository {
    override suspend fun getCurrentWeather(city: String, apiKey: String): WeatherResponse {
        return service.getCurrentWeather(
            city = city,
            apiKey = apiKey
        )
    }
}