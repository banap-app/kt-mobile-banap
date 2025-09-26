package com.banap.banap.domain.viewmodel.analysis

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.common.Resource
import com.banap.banap.domain.model.analysis.AnalysisState
import com.banap.banap.domain.use_case.analysis.GetAnalysisByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetAnalysisByIdViewModel @Inject constructor(
    private val getAnalysisByIdUseCase: GetAnalysisByIdUseCase
) : ViewModel() {
    private val _state = mutableStateOf(AnalysisState())
    val state: State<AnalysisState> = _state

    fun getAnalysisById(id: String) {
        getAnalysisByIdUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = AnalysisState(response = result.data)
                }

                is Resource.Error -> {
                    _state.value = AnalysisState(
                        error = result.message ?: "Um erro inesperado aconteceu"
                    )
                }

                is Resource.Loading -> {
                    _state.value = AnalysisState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}