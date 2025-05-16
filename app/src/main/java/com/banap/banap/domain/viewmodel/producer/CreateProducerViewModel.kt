package com.banap.banap.domain.viewmodel.producer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.common.Resource
import com.banap.banap.domain.model.producer.CreateProducerState
import com.banap.banap.domain.use_case.producer.CreateProducerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CreateProducerViewModel @Inject constructor(
    private val createProducerUseCase: CreateProducerUseCase
) : ViewModel() {
    private val _state = mutableStateOf((CreateProducerState()))
    val state: State<CreateProducerState> = _state

    fun createProducer(name: String, email: String, password: String) {
        createProducerUseCase(name, email, password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CreateProducerState(response = result.data)
                }

                is Resource.Error -> {
                    _state.value = CreateProducerState(
                        error = result.message ?: "Um erro inesperado aconteceu"
                    )
                }

                is Resource.Loading -> {
                    _state.value = CreateProducerState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun clearError() {
        _state.value = _state.value.copy(error = "")
    }
}