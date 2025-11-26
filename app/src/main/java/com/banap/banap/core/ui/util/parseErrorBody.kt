package com.banap.banap.core.ui.util

import com.banap.banap.data.model.engineer.EngineerResponse
import com.google.gson.Gson

fun parseErrorBody(errorBody: String?): EngineerResponse? {
    return try {
        errorBody?.let {
            Gson().fromJson(it, EngineerResponse::class.java)
        }
    } catch (e: Exception) {
        null
    }
}