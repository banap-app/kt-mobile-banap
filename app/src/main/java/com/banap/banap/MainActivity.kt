package com.banap.banap

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.banap.banap.app.navigation.Navigation
import com.banap.banap.core.ui.theme.BanapTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Banap)

        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = true

        window.isNavigationBarContrastEnforced = false

        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = resources.getColor(R.color.white),
                darkScrim = resources.getColor(R.color.white)
            ),
            navigationBarStyle = SystemBarStyle.light(
                scrim = resources.getColor(R.color.white),
                darkScrim = resources.getColor(R.color.white)
            )
        )
        setContent {
            BanapTheme {
                Navigation()
            }
        }
    }
}