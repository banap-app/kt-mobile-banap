package com.banap.banap.domain.viewmodel.engineer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.common.Resource
import com.banap.banap.domain.model.engineer.EngineerState
import com.banap.banap.domain.use_case.engineer.GetEngineerByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetEngineerByIdViewModel @Inject constructor(
    private val getEngineerByIdUseCase: GetEngineerByIdUseCase
) : ViewModel() {
    private val _state = mutableStateOf((EngineerState()))
    val state: State<EngineerState> = _state

    fun getEngineerById() {
        getEngineerByIdUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = EngineerState(response = result.data)
                }

                is Resource.Error -> {
                    _state.value =
                        EngineerState(
                            errors = result.data,
                            errorMessage = result.message
                        )
                }

                is Resource.Loading -> {
                    _state.value = EngineerState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}