package com.banap.banap.domain.viewmodel.property

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.common.Resource
import com.banap.banap.domain.model.producer.WithoutResponseState
import com.banap.banap.domain.use_case.property.UpdatePropertyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UpdatePropertyViewModel @Inject constructor(
    private val updatePropertyUseCase: UpdatePropertyUseCase
) : ViewModel() {
    private val _state = mutableStateOf((WithoutResponseState()))
    val state: State<WithoutResponseState> = _state

    fun updateProperty(propertyId: String, name: String) {
        updatePropertyUseCase(propertyId, name).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = WithoutResponseState(response = result.data)
                }

                is Resource.Error -> {
                    _state.value = WithoutResponseState(
                        error = result.message ?: "Um erro inesperado aconteceu"
                    )
                }

                is Resource.Loading -> {
                    _state.value = WithoutResponseState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}