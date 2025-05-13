package com.banap.banap.domain.viewmodel.token

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.common.Resource
import com.banap.banap.domain.model.token.TokenVerificationState
import com.banap.banap.domain.use_case.login.TokenVerificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TokenVerificationViewModel @Inject constructor(
    private val tokenVerificationUseCase: TokenVerificationUseCase
) : ViewModel() {
    private val _state = mutableStateOf(TokenVerificationState())
    val state: State<TokenVerificationState> = _state

    fun verifyToken(
        token: String
    ) {
        tokenVerificationUseCase(token).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = TokenVerificationState(response = result.data)
                }

                is Resource.Error -> {
                    _state.value = TokenVerificationState(
                        error = result.message ?: "Um erro inexperado aconteceu"
                    )
                }

                is Resource.Loading -> {
                    _state.value = TokenVerificationState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun clearError() {
        _state.value = _state.value.copy(error = "")
    }
}