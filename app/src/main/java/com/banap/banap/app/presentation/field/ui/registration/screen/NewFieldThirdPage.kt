package com.banap.banap.app.presentation.field.ui.registration.screen

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.banap.banap.core.ui.components.DropdownTextField
import com.banap.banap.app.presentation.validation.cultivation.event.CultivationTextFieldFormEvent
import com.banap.banap.app.presentation.validation.cultivation.utils.validationDataCultivation
import com.banap.banap.app.presentation.validation.cultivation.viewmodel.CultivationTextFieldViewModel
import com.banap.banap.core.ui.components.ButtonRegistration
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.components.TextBoxRegistration
import com.banap.banap.core.ui.components.TitleRegistration
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.app.presentation.validation.description.utils.validationDataDescription
import com.banap.banap.app.presentation.validation.description.event.DescriptionTextFieldFormEvent
import com.banap.banap.app.presentation.validation.description.viewmodel.DescriptionTextFieldViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewFieldThirdPage (
    navigationController: NavController
) {
    val context = LocalContext.current

    val viewModelDescription = viewModel<DescriptionTextFieldViewModel>()
    val stateDescription = viewModelDescription.state

    val viewModelCultivation = viewModel<CultivationTextFieldViewModel>()
    val stateCultivation = viewModelCultivation.state

    val validationDataDescription = validationDataDescription(
        context = context,
        viewModelDescription = viewModelDescription,
        stateDescription = stateDescription
    )

    val validationDataCultivation = validationDataCultivation(
        context = context,
        viewModelCultivation = viewModelCultivation,
        stateCultivation = stateCultivation
    )

    val isValidationSuccessful = validationDataDescription && validationDataCultivation

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
        isValidationSuccessful -> {
            VERDE_CLARO
        }

        else -> {
            CINZA_CLARO
        }
    }

    contentColorButton = when {
        isValidationSuccessful -> {
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
                fallbackRoute = "NewFieldSecondPage"
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
                Column (
                    verticalArrangement = Arrangement.spacedBy(40.dp)
                ) {
                    TextBoxRegistration(
                        value = stateDescription.description,
                        onValueChange = {
                            viewModelDescription.onEvent(DescriptionTextFieldFormEvent.DescriptionChanged(it))
                            viewModelDescription.onEvent(DescriptionTextFieldFormEvent.Submit)
                        },
                        isError = stateDescription.descriptionError != null,
                        errorState = stateDescription.descriptionError,
                        label = "Descrição",
                        placeholder = "Descreva seu talhão",
                        tipoTeclado = KeyboardType.Text,
                        modifier = Modifier
                            .heightIn(
                                min = 70.dp,
                                max = 100.dp
                            )
                            .fillMaxWidth(),
                        lastOne = true,
                        maxLines = 10
                    )

                    DropdownTextField(
                        label = "Cultura",
                        value = stateCultivation.cultivation,
                        placeholder = "Escolha uma cultura",
                        options = listOf(
                            "Banana Nanica",
                            "Banana Prata",
                            "Banana da Terra",
                            "Banana Maçã",
                            "Banana Ouro"
                        ),
                        onOptionSelected = {
                            viewModelCultivation.onEvent(CultivationTextFieldFormEvent.CultivationChanged(it))
                            viewModelCultivation.onEvent(CultivationTextFieldFormEvent.Submit)
                        }
                    )
                }

                ButtonRegistration(
                    onClick = {
                        if (isValidationSuccessful) {
                            navigationController.navigate("Home")
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