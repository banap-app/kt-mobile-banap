package com.banap.banap.app.presentation.field.ui.registration.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import com.banap.banap.R
import com.banap.banap.app.util.enumerator.ToggleResult
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.core.ui.util.toggleMarker
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PinConfig
import com.google.android.gms.maps.model.PinConfig.Glyph
import com.google.maps.android.compose.AdvancedMarker
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberUpdatedMarkerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Composable
fun MapView(
    isExpanded: Boolean,
    mapModifier: Modifier,
    onClick: () -> Unit,
    marcadores: SnapshotStateList<LatLng>,
    cameraPositionState: CameraPositionState,
    maxMarkers: Int = 6,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState
) {
    Box(
        modifier = mapModifier
            .clip(
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPositionState,
            googleMapOptionsFactory = {
                GoogleMapOptions().mapId("DEMO_MAP_ID")
            },
            properties = MapProperties(isMyLocationEnabled = true),
            uiSettings = MapUiSettings(myLocationButtonEnabled = true),
            onMapClick = { latLng ->
                when (marcadores.toggleMarker(latLng, maxMarkers)) {
                    ToggleResult.ADDED -> {
                        Log.d("MAP", "Ponto adicionado: $latLng")
                    }
                    ToggleResult.REMOVED -> {
                        Log.d("MAP", "Ponto removido: $latLng")
                    }
                    ToggleResult.LIMIT_REACHED -> {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = "Você atingiu o limite de pontos!",
                                actionLabel = "Entendi",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
            }
        ) {
            marcadores.forEachIndexed { index, posicao ->
                val markerState = rememberUpdatedMarkerState(position = posicao)

                val pingConfig = PinConfig.builder()
                    .setBackgroundColor(VERDE_ESCURO.toArgb())
                    .setBorderColor(VERDE_ESCURO.toArgb())
                    .setGlyph(Glyph(BRANCO.toArgb()))
                    .build()

                LaunchedEffect(markerState) {
                    snapshotFlow { markerState.position }
                        .collect { newPosition ->
                            marcadores[index] = newPosition
                        }
                }

                AdvancedMarker(
                    state = markerState,
                    title = "Ponto ${index + 1}",
                    draggable = true,
                    pinConfig = pingConfig,
                    onClick = {
                        marcadores.toggleMarker(posicao, maxMarkers = maxMarkers)
                        true
                    }
                )
            }

            if (marcadores.size >= 2) {
                Polyline(
                    points = marcadores.toList(),
                    color = VERDE_ESCURO,
                    width = 5f
                )
            }

            if (marcadores.size >= 3) {
                Polyline(
                    points = listOf(
                        marcadores.first(),
                        marcadores.last()
                    ),
                    color = VERDE_ESCURO,
                    width = 5f
                )

                Polygon(
                    points = marcadores.toList(),
                    fillColor = VERDE_CLARO.copy(alpha = 0.5f),
                    strokeColor = VERDE_CLARO,
                    strokeWidth = 1f
                )
            }
        }

        Card (
            onClick = onClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(
                    top = 46.dp,
                    end = 7.dp
                ),
            colors = CardDefaults.cardColors(
                containerColor = BRANCO.copy(alpha = 0.8f)
            ),
            shape = RoundedCornerShape(min(0.dp, 0.dp)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = if (isExpanded) R.drawable.baseline_close_fullscreen_24 else R.drawable.baseline_open_in_full_24),
                contentDescription = "Fechar",
                modifier = Modifier
                    .padding(7.dp)
                    .scale(0.9f)
                    .background(BRANCO.copy(alpha = 0.5f))
            )
        }
    }
}