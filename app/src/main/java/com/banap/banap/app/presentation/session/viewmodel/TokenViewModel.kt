package com.banap.banap.app.presentation.session.viewmodel

import androidx.lifecycle.ViewModel
import com.banap.banap.core.data.local.token.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor (
    private val tokenManager: TokenManager
) : ViewModel() {
    fun saveToken(key: String, token: String) {
        tokenManager.saveToken(key, token)
    }

    fun saveTokens(tokens: Map<String, String>) {
        tokenManager.saveTokens(tokens)
    }

    fun getToken(key: String) : String? {
        return tokenManager.getToken(key)
    }

    fun clearToken(key: String) {
        tokenManager.clearToken(key)
    }

    fun clearAll() {
        tokenManager.clearAll()
    }
}