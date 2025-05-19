package com.banap.banap.domain.viewmodel.property

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.common.Resource
import com.banap.banap.domain.model.property.GetPropertyByIdState
import com.banap.banap.domain.use_case.property.GetPropertyByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetPropertyByIdViewModel @Inject constructor(
    private val getPropertyByIdUseCase: GetPropertyByIdUseCase
) : ViewModel() {
    private val _state = mutableStateOf(GetPropertyByIdState())
    val state: State<GetPropertyByIdState> = _state

    fun getPropertyById(id: String) {
        getPropertyByIdUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = GetPropertyByIdState(response = result.data)
                }

                is Resource.Error -> {
                    _state.value = GetPropertyByIdState(
                        error = result.message ?: "Um erro inesperado aconteceu"
                    )
                }

                is Resource.Loading -> {
                    _state.value = GetPropertyByIdState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}