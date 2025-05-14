package com.banap.banap.domain.viewmodel.location

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.common.Resource
import com.banap.banap.domain.model.location.LocationState
import com.banap.banap.domain.use_case.location.LocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationUseCase: LocationUseCase
) : ViewModel() {
    private val _state = mutableStateOf(LocationState())
    val state: State<LocationState> = _state

    fun getCurrentLocation() {
        locationUseCase.invoke().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = LocationState(response = result.data)
                }

                is Resource.Error -> {
                    _state.value = LocationState(
                        error = result.message ?: "Um erro inesperado aconteceu"
                    )
                }

                is Resource.Loading -> {
                    _state.value = LocationState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}