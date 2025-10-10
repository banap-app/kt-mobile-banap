package com.banap.banap.core.data.local.token

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TokenManager @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val prefs = context.getSharedPreferences("auth_token", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveToken(key: String, token: String) {
        prefs.edit()
            .putString(key, token)
            .apply()
    }

    fun saveTokens(tokens: Map<String, String>) {
        prefs.edit().apply {
            tokens.forEach { (key, value) ->
                putString(key, value)
            }
        }.apply()
    }

    suspend fun saveMarkers(key: String, markers: List<LatLng>) {
        withContext(Dispatchers.IO) {
            val json = gson.toJson(markers)

            prefs.edit()
                .putString(key, json)
                .apply()
        }
    }

    fun getToken(key: String): String? {
        return prefs.getString(key, null)
    }

    fun getMarkers(key: String): List<LatLng> {
        val json = prefs.getString(key, null) ?: return emptyList()
        val type = object : TypeToken<List<LatLng>>() {}.type
        return gson.fromJson(json, type)
    }

    fun clearToken(key: String) {
        prefs.edit()
            .remove(key)
            .apply()
    }

    fun clearAll() {
        prefs.edit().clear().apply()
    }
}