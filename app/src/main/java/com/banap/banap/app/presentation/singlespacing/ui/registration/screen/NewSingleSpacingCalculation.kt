package com.banap.banap.app.presentation.singlespacing.ui.registration.screen

import android.widget.Space
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.analysis.ui.producer.registration.components.ResultCard
import com.banap.banap.app.presentation.client.ui.registration.components.EngineerTitleRegistration
import com.banap.banap.app.presentation.home.ui.components.ScaffoldCustomizedForEngineerScreens
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.validation.email.event.EmailTextFieldFormEvent
import com.banap.banap.app.presentation.validation.spacing.event.SpaceTextFieldFormEvent
import com.banap.banap.app.presentation.validation.spacing.utils.validationDataLineSpace
import com.banap.banap.app.presentation.validation.spacing.utils.validationDataPlantSpace
import com.banap.banap.app.presentation.validation.spacing.viewmodel.LineSpaceTextFieldViewModel
import com.banap.banap.app.presentation.validation.spacing.viewmodel.PlantSpaceTextFieldViewModel
import com.banap.banap.core.ui.components.ButtonRegistration
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.components.TextBoxRegistration
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import kotlinx.coroutines.delay

@Composable
fun NewSingleSpacingCalculation(
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

    // Variaveis do TextField de Espaco entre plantas

    val viewModelPlantsSpace = viewModel<PlantSpaceTextFieldViewModel>()
    val statePlants = viewModelPlantsSpace.state

    // Variaveis do TextField de Espaco entre linhas

    val viewModelLinesSpace = viewModel<LineSpaceTextFieldViewModel>()
    val stateLines = viewModelLinesSpace.state

    // Variaveis para validação dos campos

    val validationDataPlants = validationDataPlantSpace(
        context = context,
        viewModelPlantSpace = viewModelPlantsSpace,
        statePlantSpace = statePlants
    )

    val validationDataLines = validationDataLineSpace(
        context = context,
        viewModelLineSpace = viewModelLinesSpace,
        stateLineSpace = stateLines
    )

    val isValidationSuccessful = validationDataPlants && validationDataLines

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

    // Variaveis para controle das paginas dos calculo

    var page: Int by remember {
        mutableIntStateOf(1)
    }

    var isCalculationMade by remember {
        mutableStateOf(false)
    }

    var result by remember {
        mutableDoubleStateOf(0.0)
    }

    // Launched

    LaunchedEffect(hasLoadingScreen) {
        if (hasLoadingScreen) {
            delay(2_000)
            page = 2
            isCalculationMade = true
            result = (10000 / (stateLines.lineSpace.toDouble() * statePlants.plantSpace.toDouble()))
            hasLoadingScreen = false
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
                .verticalScroll(rememberScrollState())
        ) {
            RegistrationHeader(
                navigationController = navigationController,
                fixedRoute = "Tools"
            )

            EngineerTitleRegistration(
                title = "Espaçamento",
                highlightedTitle = "simples(mxm)",
                description = "Com essas ferramentas esperamos  facilitar ainda mais sua vida! Simplificando e reunindo tudo o que você precisa em um só lugar! "
            )

            when {
                isCalculationMade -> {
                    Column (
                        modifier = Modifier
                            .padding(
                                bottom = 60.dp
                            )
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(
                            space = 42.dp,
                            alignment = Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.singlespacingresult),
                            contentDescription = "Icone de Calculo de espaçamento simples feito"
                        )

                        ResultCard(
                            nutrient = "Resultado",
                            result = "$result pl/h"
                        )
                    }
                }

                else -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            space = 40.dp
                        )
                    ) {
                        TextBoxRegistration(
                            value = statePlants.plantSpace,
                            onValueChange = {
                                viewModelPlantsSpace.onEvent(SpaceTextFieldFormEvent.SpaceChanged(it))
                                viewModelPlantsSpace.onEvent(SpaceTextFieldFormEvent.Submit)
                            },
                            isError = statePlants.plantSpaceError != null,
                            errorState = statePlants.plantSpaceError,
                            label = "Entre plantas",
                            placeholder = "Digite aqui a quantidade",
                            tipoTeclado = KeyboardType.Email,
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        TextBoxRegistration(
                            value = stateLines.lineSpace,
                            onValueChange = {
                                viewModelLinesSpace.onEvent(SpaceTextFieldFormEvent.SpaceChanged(it))
                                viewModelLinesSpace.onEvent(SpaceTextFieldFormEvent.Submit)
                            },
                            isError = stateLines.lineSpaceError != null,
                            errorState = stateLines.lineSpaceError,
                            label = "Entre linhas",
                            placeholder = "Digite aqui a quantidade",
                            tipoTeclado = KeyboardType.Email,
                            modifier = Modifier
                                .fillMaxWidth(),
                            lastOne = true
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            ButtonRegistration(
                onClick = {
                    if (page == 1) {
                        viewModelPlantsSpace.onEvent(SpaceTextFieldFormEvent.Submit)
                        viewModelLinesSpace.onEvent(SpaceTextFieldFormEvent.Submit)

                        if (isValidationSuccessful && !isCalculationMade) {
                            hasLoadingScreen = true
                        }
                    } else {
                        navigationController.navigate("EngineerHome")
                    }
                },
                buttonValue = if (isCalculationMade) "Feito" else "Calcular",
                backgroundColor = backgroundColor,
                contentColor = contentColor
            )
        }
    }
}