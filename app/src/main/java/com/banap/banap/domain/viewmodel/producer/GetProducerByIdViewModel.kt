package com.banap.banap.domain.viewmodel.producer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.common.Resource
import com.banap.banap.domain.model.producer.ProducerState
import com.banap.banap.domain.use_case.producer.GetProducerByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetProducerByIdViewModel @Inject constructor(
    private val getProducerByIdUseCase: GetProducerByIdUseCase
) : ViewModel() {
    private val _state = mutableStateOf((ProducerState()))
    val state: State<ProducerState> = _state

    fun getProducerById(id: String) {
        getProducerByIdUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ProducerState(response = result.data)
                }

                is Resource.Error -> {
                    _state.value = ProducerState(
                        error = result.message ?: "Um erro inesperado aconteceu"
                    )
                }

                is Resource.Loading -> {
                    _state.value = ProducerState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}