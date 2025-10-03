package com.banap.banap.app.presentation.field.ui.registration.components

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.banap.banap.R
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.util.enumerator.FieldPage
import com.banap.banap.core.ui.components.ButtonRegistration
import com.banap.banap.core.ui.components.TitleRegistration
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.core.ui.util.isLocationEnabled
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SecondPage(
    currentPage: MutableState<FieldPage>,
    snackBarHostState: SnackbarHostState,
    context: Context,
    scope: CoroutineScope,
    markers: SnapshotStateList<LatLng>,
    tokenViewModel: TokenViewModel,
    isValidationSuccessful: MutableState<Boolean>,
    innerPadding: PaddingValues
) {
    val isLocationOn = isLocationEnabled(context)

    var backgroundColorButton by remember {
        mutableStateOf(CINZA_CLARO)
    }

    var contentColorButton by remember {
        mutableStateOf(CINZA_ESCURO)
    }

    var isExpanded = remember { mutableStateOf(false) }

    var fallback by remember {
        mutableStateOf(LatLng(-23.5489, -46.6388))
    }

    var targetLatLng =
        if (tokenViewModel.getToken("latitude") != null && tokenViewModel.getToken("longitude") != null) {
            var latitude = -24.714174
            var longitude = -47.8870154

            tokenViewModel.getToken("latitude")?.let {
                Log.d("LATITUDE", "latitude não nula - $it")
                latitude = it.toDouble()
            }

            tokenViewModel.getToken("longitude")?.let {
                Log.d("LONGITUDE", "longitude não nula - $it")
                longitude = it.toDouble()
            }

            LatLng(latitude, longitude)
        } else {
            fallback
        }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(fallback, 17f)
    }

    LaunchedEffect(targetLatLng) {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(targetLatLng, 17f),
            durationMs = 1_000
        )
    }

    isValidationSuccessful.value = markers.size >= 3

    val backgroundColor by animateColorAsState(
        targetValue = backgroundColorButton,
        label = "Button Background color",
        animationSpec = tween(
            durationMillis = 200,
            easing = LinearEasing
        )
    )

    val contentColor by animateColorAsState(
        targetValue = contentColorButton,
        label = "Button Content Color",
        animationSpec = tween(
            durationMillis = 200,
            easing = LinearEasing
        )
    )

    backgroundColorButton = when {
        isValidationSuccessful.value -> {
            VERDE_CLARO
        }

        else -> {
            CINZA_CLARO
        }
    }

    contentColorButton = when {
        isValidationSuccessful.value -> {
            BRANCO
        }

        else -> {
            CINZA_ESCURO
        }
    }

    LaunchedEffect(Unit) {
        if (!isLocationOn) {
            scope.launch {
                val result = snackBarHostState.showSnackbar(
                    message = "Ative a sua localização para continuar",
                    actionLabel = "Entendi",
                    duration = SnackbarDuration.Indefinite
                )

                if (result == SnackbarResult.ActionPerformed) {
                    context.startActivity(
                        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                    )
                }
            }
        }
    }

    if (!isExpanded.value) {
        Column (
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            Box {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.linhas_propriedade),
                    contentDescription = "Vetor de linhas",
                    modifier = Modifier
                        .padding(
                            top = 17.dp
                        )
                        .fillMaxWidth()
                        .scale(1.2F)
                )

                IconButton(
                    onClick = {
                        currentPage.value = FieldPage.FIRST
                    },
                    modifier = Modifier
                        .padding(
                            top = 40.dp,
                            start = 20.dp,
                            bottom = 40.dp
                        )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
                        contentDescription = "Icone de voltar",
                        modifier = Modifier
                            .scale(1.2F)
                    )
                }
            }

            TitleRegistration(
                texto = "Cadastrando seu ",
                textoASerDestacado = "Talhão...",
                corEmDestaque = VERDE_ESCURO,
                subTexto = "",
                tamanhoTextoDestacado = 36.sp,
                paginaUsuario = false,
                subtituloDestacado = "",
                subtitulo = "Você deve clicar em pelo menos 3 pontos do mapa para que uma área seja delimitada, demonstrando assim, a localização do talhão."
            )

            MapView(
                isExpanded = isExpanded.value,
                mapModifier = Modifier
                    .height(350.dp)
                    .padding(
                        start = 30.dp,
                        end = 30.dp,
                        bottom = 60.dp
                    )
                    .fillMaxWidth(),
                onClick = {
                    isExpanded.value = true
                },
                marcadores = markers,
                cameraPositionState = cameraPositionState,
                scope = scope,
                snackBarHostState = snackBarHostState
            )

            Spacer(modifier = Modifier.weight(1f))

            ButtonRegistration(
                onClick = {
                    if (isValidationSuccessful.value) {
                        currentPage.value = FieldPage.THIRD
                    }
                },
                buttonValue = "Continuar",
                backgroundColor = backgroundColor,
                contentColor = contentColor
            )
        }
    } else {
        MapView(
            isExpanded = isExpanded.value,
            mapModifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
                .fillMaxSize(),
            onClick = {
                isExpanded.value = false
            },
            marcadores = markers,
            cameraPositionState = cameraPositionState,
            scope = scope,
            snackBarHostState = snackBarHostState
        )
    }
}