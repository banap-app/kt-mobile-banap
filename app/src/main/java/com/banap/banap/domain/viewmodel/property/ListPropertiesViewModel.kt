package com.banap.banap.domain.viewmodel.property

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.common.Resource
import com.banap.banap.domain.model.property.ListPropertiesState
import com.banap.banap.domain.use_case.property.ListPropertiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListPropertiesViewModel @Inject constructor(
    private val listPropertiesUseCase: ListPropertiesUseCase
) : ViewModel() {
    private val _state = mutableStateOf(ListPropertiesState())
    val state: State<ListPropertiesState> = _state

    fun listProperties(producerId: String) {
        listPropertiesUseCase(producerId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ListPropertiesState(response = result.data)
                }

                is Resource.Error -> {
                    _state.value = ListPropertiesState(
                        error = result.message ?: "Um erro inesperado aconteceu"
                    )
                }

                is Resource.Loading -> {
                    _state.value = ListPropertiesState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}