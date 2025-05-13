package com.banap.banap.data.model.weather

data class WeatherResponse (
    val main: Main,
    val weather: List<Weather>
)
