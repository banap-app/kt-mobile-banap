package com.banap.banap.app.presentation.session.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.banap.banap.core.data.local.token.TokenManager
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor (
    private val tokenManager: TokenManager
) : ViewModel() {
    var markers = mutableStateListOf<LatLng>()
        private set

    fun saveToken(key: String, token: String) {
        tokenManager.saveToken(key, token)
    }

    fun saveTokens(tokens: Map<String, String>) {
        tokenManager.saveTokens(tokens)
    }

    suspend fun saveMarkers(key: String, markers: List<LatLng>) {
        tokenManager.saveMarkers(key, markers)
    }

    fun getToken(key: String) : String? {
        return tokenManager.getToken(key)
    }

    fun getMarkers(key: String) : List<LatLng> {
        return tokenManager.getMarkers(key)
    }

    fun loadMarkers(key: String) {
        markers.clear()
        markers.addAll(getMarkers(key))
    }

    fun clearToken(key: String) {
        tokenManager.clearToken(key)
    }

    fun clearTokens(keyList: List<String>) {
        tokenManager.clearTokens(keyList)
    }

    fun clearAll() {
        tokenManager.clearAll()
    }
}