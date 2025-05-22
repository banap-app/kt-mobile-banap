package com.banap.banap.domain.viewmodel.field

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.common.Resource
import com.banap.banap.domain.model.field.CreateFieldState
import com.banap.banap.domain.model.field.FieldBoundary
import com.banap.banap.domain.use_case.field.CreateFieldUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CreateFieldViewModel @Inject constructor(
    private val createFieldUseCase: CreateFieldUseCase
) : ViewModel() {
    private val _state = mutableStateOf(CreateFieldState())
    val state: State<CreateFieldState> = _state

    fun createField(
        producerId: String,
        propertyId: String,
        name: String,
        description: String,
        crop: String,
        fieldBoundary: List<FieldBoundary>
    ) {
        createFieldUseCase(
            producerId = producerId,
            propertyId = propertyId,
            name = name,
            description = description,
            crop = crop,
            fieldBoundary = fieldBoundary
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CreateFieldState(response = result.data)
                }
                is Resource.Error -> {
                    _state.value = CreateFieldState(
                        error = result.message ?: "Um erro inesperado aconteceu"
                    )
                }
                is Resource.Loading -> {
                    _state.value = CreateFieldState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}