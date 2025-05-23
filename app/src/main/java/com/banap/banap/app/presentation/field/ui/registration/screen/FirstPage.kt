package com.banap.banap.app.presentation.field.ui.registration.screen

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.banap.banap.core.ui.components.ButtonRegistration
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.components.TextBoxRegistration
import com.banap.banap.core.ui.components.TitleRegistration
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.app.presentation.validation.name.utils.validationDataName
import com.banap.banap.app.presentation.validation.name.event.NameTextFieldFormEvent
import com.banap.banap.app.presentation.validation.name.viewmodel.NameTextFieldViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewFieldFirstPage (
    navigationController: NavController
) {
    val context = LocalContext.current

    val viewModelName = viewModel<NameTextFieldViewModel>()
    val stateName = viewModelName.state

    var isValidationSuccessful by remember {
        mutableStateOf(false)
    }

    isValidationSuccessful = validationDataName(
        context = context,
        viewModelName = viewModelName,
        stateName = stateName
    )

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
        isValidationSuccessful && stateName.nameError == null -> {
            VERDE_CLARO
        }

        else -> {
            CINZA_CLARO
        }
    }

    contentColorButton = when {
        isValidationSuccessful && stateName.nameError == null -> {
            BRANCO
        }

        else -> {
            CINZA_ESCURO
        }
    }

    Scaffold (
        modifier = Modifier
            .fillMaxSize(),
        containerColor = BRANCO
    ) {
        Column {
            RegistrationHeader(
                navigationController = navigationController,
                fallbackRoute = "Home"
            )

            TitleRegistration(
                texto = "Cadastrando seu ",
                textoASerDestacado = "Talhão...",
                corEmDestaque = VERDE_ESCURO,
                subTexto = "",
                tamanhoTextoDestacado = 36.sp,
                paginaUsuario = false,
                subtituloDestacado = "",
                subtitulo = ""
            )

            Column (
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                TextBoxRegistration(
                    value = stateName.name,
                    onValueChange = {
                        viewModelName.onEvent(NameTextFieldFormEvent.NameChanged(it))
                        viewModelName.onEvent(NameTextFieldFormEvent.Submit)
                    },
                    isError = stateName.nameError != null,
                    errorState = stateName.nameError,
                    label = "Identificação",
                    placeholder = "Talhão 01",
                    tipoTeclado = KeyboardType.Text,
                    modifier = Modifier
                        .fillMaxWidth(),
                    lastOne = true
                )

                ButtonRegistration(
                    onClick = {
                        viewModelName.onEvent(NameTextFieldFormEvent.Submit)

                        if (isValidationSuccessful) {
                            navigationController.navigate("NewFieldSecondPage")
                        }
                    },
                    buttonValue = "Continuar",
                    backgroundColor = backgroundColor,
                    contentColor = contentColor
                )
            }
        }
    }
}