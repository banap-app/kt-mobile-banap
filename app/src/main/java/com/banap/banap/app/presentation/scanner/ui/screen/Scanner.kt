package com.banap.banap.app.presentation.scanner.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.client.ui.listing.components.HandlingAllStates
import com.banap.banap.app.presentation.scanner.ui.components.ScannerPreview
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.core.ui.components.Button
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO

@Composable
fun Scanner(
    navigationController: NavController,
    tokenViewModel: TokenViewModel
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var showCamera by remember { mutableStateOf(false) }
    var finalPhoto by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(hasCameraPermission) {
        if (hasCameraPermission) {
            showCamera = true
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
            if (granted) {
                showCamera = true
            }
        }
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            finalPhoto != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 60.dp,
                        alignment = Alignment.CenterVertically
                    )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            space = 40.dp,
                            alignment = Alignment.CenterVertically
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = "Icone de adicionar nova propriedade",
                            tint = VERDE_ESCURO,
                            modifier = Modifier
                                .scale(2F)
                        )

                        Text(
                            text = "Análise realizada\ncom sucesso!",
                            textAlign = TextAlign.Center,
                            style = Typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = VERDE_CLARO
                        )
                    }

                    Button(
                        texto = "Registrar análise",
                        modifier = Modifier
                            .padding(vertical = 18.dp, horizontal = 15.dp),
                        hasIcon = false,
                        hasLeftIcon = true,
                        isAllRotated = true,
                        icon = ImageVector.vectorResource(R.drawable.arrow_left),
                        shape = ShapeProperty.small,
                        onClick = {
                            navigationController.navigate("EngineerHome")
                        },
                        backgroundColor = VERDE_CLARO,
                        contentColor = BRANCO,
                        defaultElevetion = 3.dp
                    )
                }
            }

            showCamera && hasCameraPermission -> {
                ScannerPreview(
                    lifecycleOwner = lifecycleOwner,
                    onPhotoConfirmed = { bitmap ->
                        finalPhoto = bitmap
                        showCamera = false
                    },
                    onCancel = {
                        showCamera = false
                    }
                )
            }

            else -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (!hasCameraPermission || !showCamera) {
                        HandlingAllStates(
                            text = "Precisamos de sua permissão\npara acessar a sua câmera!",
                            buttonText = "Permitir Acesso",
                            icon = Icons.Outlined.Check,
                            onClick = {
                                launcher.launch(Manifest.permission.CAMERA)
                            }
                        )
                    }
                }
            }
        }
    }
}