package com.banap.banap.domain.model.weather

import com.banap.banap.data.model.weather.WeatherResponse

data class WeatherState (
    val isLoading: Boolean = false,
    val response: WeatherResponse? = null,
    val error: String = ""
)