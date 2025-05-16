package com.banap.banap.core.data.local.token

import android.content.Context
import javax.inject.Inject

class TokenManager @Inject constructor (
    context: Context,
) {
    private val prefs = context.getSharedPreferences("auth_token", Context.MODE_PRIVATE)

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

    fun getToken(key: String) : String? {
        return prefs.getString(key, null)
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