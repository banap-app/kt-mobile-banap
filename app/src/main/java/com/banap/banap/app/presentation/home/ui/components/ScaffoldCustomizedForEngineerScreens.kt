package com.banap.banap.app.presentation.home.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.banap.banap.core.ui.components.LoadingScreen
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.VERDE_CLARO

@Composable
fun ScaffoldCustomizedForEngineerScreens(
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState,
    snackBarContainerColor: Color = VERDE_CLARO,
    snackBarContentColor: Color = BRANCO,
    snackBarActionColor: Color = BRANCO,
    hasLoadingScreen: Boolean = false,
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (innerPadding: PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = BRANCO,
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState
            ) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = snackBarContainerColor,
                    contentColor = snackBarContentColor,
                    actionColor = snackBarActionColor
                )
            }
        },
        floatingActionButton = floatingActionButton
    ) {
        if (!hasLoadingScreen) {
            content(it)
        } else {
            LoadingScreen()
        }
    }
}