package com.banap.banap.domain.viewmodel.property

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.common.Resource
import com.banap.banap.domain.model.property.CreatePropertyState
import com.banap.banap.domain.use_case.property.CreatePropertyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CreatePropertyViewModel @Inject constructor(
    private val createPropertyUseCase: CreatePropertyUseCase
) : ViewModel() {
    private val _state = mutableStateOf(CreatePropertyState())
    val state: State<CreatePropertyState> = _state

    fun createProperty(producerId: String, name: String) {
        createPropertyUseCase(producerId, name).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CreatePropertyState(response = result.data)
                }

                is Resource.Error -> {
                    _state.value = CreatePropertyState(
                        error = result.message ?: "Um erro inesperado aconteceu"
                    )
                }

                is Resource.Loading -> {
                    _state.value = CreatePropertyState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}