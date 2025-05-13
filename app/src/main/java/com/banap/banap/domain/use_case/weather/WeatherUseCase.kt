package com.banap.banap.domain.use_case.weather

import com.banap.banap.common.Resource
import com.banap.banap.data.model.weather.WeatherResponse
import com.banap.banap.data.repository.weather.WeatherRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class WeatherUseCase @Inject constructor(
    private val repository: WeatherRepositoryImpl
) {
    operator fun invoke(city: String, apiKey: String) : Flow<Resource<WeatherResponse>> = flow {
        try {
            emit(Resource.Loading())
            val weatherResponse = repository.getCurrentWeather(city, apiKey)
            emit(Resource.Success(weatherResponse))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Um erro inesperado aconteceu"))
        } catch (e: IOException) {
            emit(Resource.Error("Não foi possível acessar o servidor. Verifique sua conexão com a internet"))
        }
    }
}