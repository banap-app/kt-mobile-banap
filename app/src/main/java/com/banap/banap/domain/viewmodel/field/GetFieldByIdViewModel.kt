package com.banap.banap.domain.viewmodel.field

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.common.Resource
import com.banap.banap.domain.model.field.FieldState
import com.banap.banap.domain.use_case.field.GetFieldByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetFieldByIdViewModel @Inject constructor(
    private val getFieldByIdUseCase: GetFieldByIdUseCase
) : ViewModel() {
    private val _state = mutableStateOf(FieldState())
    val state: State<FieldState> = _state

    fun getFieldById(id: String) {
        getFieldByIdUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = FieldState(response = result.data)
                }

                is Resource.Error -> {
                    _state.value = FieldState(
                        error = result.message ?: "Um erro inesperado aconteceu"
                    )
                }

                is Resource.Loading -> {
                    _state.value = FieldState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}