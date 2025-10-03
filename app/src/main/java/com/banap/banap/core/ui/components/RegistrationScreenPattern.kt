package com.banap.banap.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO

@Composable
fun RegistrationScreenPattern(
    navigationController: NavController,
    fieldId: String? = null,
    userName: String? = null,
    fallbackRoute: String,
    texto: String,
    textoASerDestacado: String,
    tamanhoTextoDestacado: Int = 32,
    subTitulo: String,
    children: @Composable () -> Unit,
    onClick: () -> Unit,
    buttonValue: String,
    isValidationSuccessful: Boolean,
    stateError: String?,
    isLoading: Boolean,
    page: Int? = null
) {
    var backgroundColorButton by remember {
        mutableStateOf(CINZA_CLARO)
    }

    var contentColorButton by remember {
        mutableStateOf(CINZA_ESCURO)
    }

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
        isValidationSuccessful && stateError == null -> {
            VERDE_CLARO
        }

        else -> {
            CINZA_CLARO
        }
    }

    contentColorButton = when {
        isValidationSuccessful && stateError == null -> {
            BRANCO
        }

        else -> {
            CINZA_ESCURO
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = BRANCO
    ) { paddingValues ->
        if (!isLoading) {
            Column(
                modifier = Modifier
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding()
                    )
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())

            ) {
                RegistrationHeader(
                    navigationController = navigationController,
                    fieldId = fieldId,
                    userName = userName,
                    fallbackRoute = fallbackRoute
                )

                TitleRegistration(
                    texto = texto,
                    textoASerDestacado = textoASerDestacado,
                    corEmDestaque = VERDE_ESCURO,
                    subTexto = "",
                    tamanhoTextoDestacado = tamanhoTextoDestacado.sp,
                    paginaUsuario = false,
                    subtituloDestacado = "",
                    subtitulo = subTitulo
                )

                children()

                Spacer(modifier = Modifier.weight(1f))

                ButtonRegistration(
                    onClick = onClick,
                    buttonValue = buttonValue,
                    backgroundColor = backgroundColor,
                    contentColor = contentColor
                )
            }
        } else {
            LoadingScreen()
        }
    }
}