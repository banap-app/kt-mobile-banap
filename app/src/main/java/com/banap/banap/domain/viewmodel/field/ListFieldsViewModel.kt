package com.banap.banap.domain.viewmodel.field

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.common.Resource
import com.banap.banap.domain.model.field.ListFieldsState
import com.banap.banap.domain.use_case.field.ListFieldsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListFieldsViewModel @Inject constructor(
    private val listFieldsUseCase: ListFieldsUseCase
) : ViewModel() {
    private val _state = mutableStateOf(ListFieldsState())
    val state: State<ListFieldsState> = _state

    fun listFields(id: String) {
        listFieldsUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ListFieldsState(response = result.data)
                }

                is Resource.Error -> {
                    _state.value = ListFieldsState(
                        error = result.message ?: "Um erro inesperado aconteceu"
                    )
                }

                is Resource.Loading -> {
                    _state.value = ListFieldsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}