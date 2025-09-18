package com.banap.banap.domain.viewmodel.field

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.banap.banap.common.Resource
import com.banap.banap.domain.model.field.FieldBoundary
import com.banap.banap.domain.model.producer.WithoutResponseState
import com.banap.banap.domain.use_case.field.UpdateFieldUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UpdateFieldViewModel @Inject constructor(
    private val updateFieldUseCase: UpdateFieldUseCase
) : ViewModel() {
    private val _state = mutableStateOf((WithoutResponseState()))
    val state: State<WithoutResponseState> = _state

    fun updateField(
        id: String,
        producerId: String,
        propertyId: String,
        name: String,
        description: String,
        crop: String,
        fieldBoundary: List<FieldBoundary>
    ) {
        updateFieldUseCase(
            id = id,
            producerId = producerId,
            propertyId = propertyId,
            name = name,
            description = description,
            crop = crop,
            fieldBoundary = fieldBoundary
        ).onEach { result ->
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
        }
    }
}