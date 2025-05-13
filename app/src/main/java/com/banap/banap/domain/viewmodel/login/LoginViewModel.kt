package com.banap.banap.domain.viewmodel.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banap.banap.common.Resource
import com.banap.banap.domain.model.login.LoginState
import com.banap.banap.domain.use_case.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    fun authenticateUser (
        email: String,
        password: String
    ) {
        loginUseCase(email, password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = LoginState(response = result.data)
                }

                is Resource.Error -> {
                    _state.value = LoginState(
                        error = result.message ?: "Um erro inesperado aconteceu"
                    )
                }

                is Resource.Loading -> {
                    _state.value = LoginState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}