package com.banap.banap.core.ui.components

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.navigation.viewmodel.NavigationViewModel
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.domain.viewmodel.token.TokenVerificationViewModel

@Composable
fun SplashScreen(
    navigationController: NavController,
    tokenViewModel: TokenViewModel,
    tokenVerificationViewModel: TokenVerificationViewModel,
    navigationViewModel: NavigationViewModel
) {
    val scale = remember { Animatable(0.8f) }
    val scaleBanapLogo = remember { Animatable(0.7f) }
    val alpha = remember { Animatable(0f) }

    val tokenVerificationState = tokenVerificationViewModel.state.value
    val lastRoute by navigationViewModel.lastScreen.collectAsState()

    LaunchedEffect(key1 = true) {
        val token = tokenViewModel.getToken("token")
        Log.d("token", "token: $token")

        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            )
        )

        scaleBanapLogo.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            )
        )

        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 600
            )
        )

        if (token.isNullOrEmpty()) {
            navigationController.navigate("Tutorial") {
                popUpTo("SplashScreen") {
                    inclusive = true
                }
            }
        } else {
            tokenVerificationViewModel.verifyToken(token)
        }
    }

    LaunchedEffect(tokenVerificationState.response) {
        tokenVerificationState.response?.success?.let {
            tokenViewModel.saveToken("verifiedToken", it.toString())
            val target = when {
                lastRoute.isBlank() || lastRoute.contains("SplashScreen") -> "Home"
                else -> lastRoute
            }

            navigationController.navigate(target) {
                popUpTo("SplashScreen") {
                    inclusive = true
                }
            }
        }
    }

    LaunchedEffect(tokenVerificationState.error) {
        if (tokenVerificationState.error.isNotEmpty()) {
            Log.d("Error", tokenVerificationState.error)
            navigationController.navigate("Login") {
                popUpTo("SplashScreen") {
                    inclusive = true
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .background(BRANCO)
            .fillMaxSize(),
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.desenho_de_cima),
            contentDescription = "Vetor de linhas",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .scale(scale.value)
                .alpha(alpha.value)
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(
                space = 20.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.banap_vector),
                contentDescription = "Logo do Banap",
                modifier = Modifier
                    .scale(scaleBanapLogo.value)
                    .alpha(alpha.value)
            )

            CircularProgressIndicator(
                strokeWidth = 4.dp,
                modifier = Modifier
                    .scale(scale.value)
                    .alpha(alpha.value)
                    .size(48.dp),
                color = VERDE_CLARO
            )
        }

        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.desenho_de_baixo),
            contentDescription = "Vetor de linhas",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .scale(scale.value)
                .alpha(alpha.value)
        )
    }
}