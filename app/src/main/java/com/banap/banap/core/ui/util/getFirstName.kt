package com.banap.banap.core.ui.util

import androidx.compose.runtime.Composable

@Composable
fun getFirstName(name: String): String  {
    return name.trim().split(" ").first()
}