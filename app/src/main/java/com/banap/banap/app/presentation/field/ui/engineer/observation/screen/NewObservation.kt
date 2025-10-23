package com.banap.banap.app.presentation.field.ui.engineer.observation.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.banap.banap.app.presentation.client.ui.registration.components.EngineerTitleRegistration
import com.banap.banap.app.presentation.home.ui.components.ScaffoldCustomizedForEngineerScreens
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.validation.description.event.DescriptionTextFieldFormEvent
import com.banap.banap.app.presentation.validation.description.utils.validationDataDescription
import com.banap.banap.app.presentation.validation.description.viewmodel.DescriptionTextFieldViewModel
import com.banap.banap.app.presentation.validation.name.event.NameTextFieldFormEvent
import com.banap.banap.app.presentation.validation.name.utils.validationDataName
import com.banap.banap.app.presentation.validation.name.viewmodel.NameTextFieldViewModel
import com.banap.banap.core.ui.components.ButtonRegistration
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.components.TextBoxRegistration
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun NewObservation(
    navigationController: NavController,
    tokenViewModel: TokenViewModel
) {
    // Variaveis da Tela

    val context = LocalContext.current

    // Variaveis do Scaffold

    val snackBarHostState = remember { SnackbarHostState() }

    // Estados da tela

    var hasLoadingScreen: Boolean by remember {
        mutableStateOf(false)
    }

    var hasContentError: String by remember {
        mutableStateOf("")
    }

    // Variaveis do TextField de Name

    val viewModelName = viewModel<NameTextFieldViewModel>()
    val stateName = viewModelName.state

    // Variaveis do TextField de Description

    val viewModelDescription = viewModel<DescriptionTextFieldViewModel>()
    val stateDescription = viewModelDescription.state

    // Variaveis para validação dos campos

    val validationDataName = validationDataName(
        context = context,
        viewModelName = viewModelName,
        stateName = stateName
    )

    val validationDataDescription = validationDataDescription(
        context = context,
        viewModelDescription = viewModelDescription,
        stateDescription = stateDescription
    )

    val isValidationSuccessful = validationDataName && validationDataDescription

    // Variaveis para controle das cores do botão de cadastro

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

    // Launched

    LaunchedEffect(hasLoadingScreen) {
        if (hasLoadingScreen) {
            delay(2_000)
            withContext(Dispatchers.Main) {
                navigationController.navigate("ClientField")
            }
        }
    }

    ScaffoldCustomizedForEngineerScreens(
        snackBarHostState = snackBarHostState,
        hasLoadingScreen = hasLoadingScreen
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            RegistrationHeader(
                navigationController = navigationController,
                fallbackRoute = "ClientField"
            )

            EngineerTitleRegistration(
                title = "Fazendo uma",
                highlightedTitle = "observação",
                description = "A observação pode ser visualizada pelo produtor. Elas são uma forma de contato entre o cliente e você!"
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = 40.dp
                    )
                ) {
                    TextBoxRegistration(
                        value = stateName.name,
                        onValueChange = {
                            viewModelName.onEvent(NameTextFieldFormEvent.NameChanged(it))
                            viewModelName.onEvent(NameTextFieldFormEvent.Submit)
                        },
                        isError = stateName.nameError != null,
                        errorState = stateName.nameError,
                        label = "Título da Observação",
                        placeholder = "Digite aqui o título",
                        tipoTeclado = KeyboardType.Text,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    TextBoxRegistration(
                        value = stateDescription.description,
                        onValueChange = {
                            viewModelDescription.onEvent(
                                DescriptionTextFieldFormEvent.DescriptionChanged(
                                    it
                                )
                            )
                            viewModelDescription.onEvent(DescriptionTextFieldFormEvent.Submit)
                        },
                        isError = stateDescription.descriptionError != null,
                        errorState = stateDescription.descriptionError,
                        label = "Observação",
                        placeholder = "Digite aqui a observação",
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
                }

                ButtonRegistration(
                    onClick = {
                        viewModelName.onEvent(NameTextFieldFormEvent.Submit)
                        viewModelDescription.onEvent(DescriptionTextFieldFormEvent.Submit)

                        if (isValidationSuccessful) {
                            hasLoadingScreen = true
                        }
                    },
                    buttonValue = "Agregar",
                    backgroundColor = backgroundColor,
                    contentColor = contentColor
                )
            }
        }
    }
}