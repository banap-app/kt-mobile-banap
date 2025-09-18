package com.banap.banap.domain.viewmodel.analysis

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.common.Resource
import com.banap.banap.domain.model.analysis.ListAnalysisState
import com.banap.banap.domain.use_case.analysis.ListAnalysisUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListAnalysisViewModel @Inject constructor(
    private val listAnalysisUseCase: ListAnalysisUseCase
) : ViewModel() {
    private val _state = mutableStateOf(ListAnalysisState())
    val state: State<ListAnalysisState> = _state

    fun listAnalysis(id: String) {
        listAnalysisUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ListAnalysisState(response = result.data)
                }

                is Resource.Error -> {
                    _state.value = ListAnalysisState(
                        error = result.message ?: "Um erro inesperado aconteceu"
                    )
                }

                is Resource.Loading -> {
                    _state.value = ListAnalysisState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}