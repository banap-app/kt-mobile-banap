package com.banap.banap.domain.viewmodel.weather

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.common.Resource
import com.banap.banap.domain.model.weather.WeatherState
import com.banap.banap.domain.use_case.weather.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase
) : ViewModel() {
    private val _state = mutableStateOf(WeatherState())
    val state: State<WeatherState> = _state

    fun getCurrentWeather(city: String = "Pariquera-Açu", apiKey: String = "4ec81c4f20b26d6d6cdaeb8177fc81a1") {
        weatherUseCase(city, apiKey).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = WeatherState(response = result.data)
                }

                is Resource.Error -> {
                    _state.value = WeatherState(
                        error = result.message ?: "Um erro inesperado aconteceu"
                    )
                }
                is Resource.Loading -> {
                    _state.value = WeatherState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}