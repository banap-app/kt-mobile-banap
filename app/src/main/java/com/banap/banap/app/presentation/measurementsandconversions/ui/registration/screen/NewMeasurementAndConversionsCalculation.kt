package com.banap.banap.app.presentation.measurementsandconversions.ui.registration.screen

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
import com.banap.banap.app.presentation.validation.dropdown.event.DropdownTextFieldFormEvent
import com.banap.banap.app.presentation.validation.dropdown.utils.validationDataDropdown
import com.banap.banap.app.presentation.validation.dropdown.viewmodel.DropdownTextFieldViewModel
import com.banap.banap.app.presentation.validation.spacing.event.SpaceTextFieldFormEvent
import com.banap.banap.app.presentation.validation.spacing.utils.validationDataLineSpace
import com.banap.banap.app.presentation.validation.spacing.viewmodel.LineSpaceTextFieldViewModel
import com.banap.banap.core.ui.components.ButtonRegistration
import com.banap.banap.core.ui.components.DropdownTextField
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.components.TextBoxRegistration
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import kotlinx.coroutines.delay

@Composable
fun NewMeasurementAndConversionsCalculation(
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

    // Variaveis do DropdownTextField

    val viewModelDropdown = viewModel<DropdownTextFieldViewModel>()
    val stateDropdown = viewModelDropdown.state

    // Variaveis do TextField de Espaco entre linhas

    val viewModelMeasure = viewModel<LineSpaceTextFieldViewModel>()
    val stateMeasure = viewModelMeasure.state

    // Variaveis para validação dos campos

    val validationDataDropdown = validationDataDropdown(
        context = context,
        viewModelDropdown = viewModelDropdown,
        stateDropdown = stateDropdown
    )

    val validationDataMeasure = validationDataLineSpace(
        context = context,
        viewModelLineSpace = viewModelMeasure,
        stateLineSpace = stateMeasure
    )

    val isValidationSuccessful = validationDataMeasure && validationDataDropdown

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

            when (stateDropdown.option) {
                "Kg/ha para G/ha" -> {
                    // 1 kg = 1000 g
                    result = stateMeasure.lineSpace.toDouble() * 1_000
                }

                "G/dm³ para Mg/dm³" -> {
                    // 1.000 g = 1 Mg
                    result = stateMeasure.lineSpace.toDouble() * 1_000
                }

                "G/kg para Mg/dm³" -> {
                    // 1 g/kg = 0.001 Mg/dm³ (aproximadamente)
                    result = stateMeasure.lineSpace.toDouble() * 0.001
                }

                "G/kg para T/ha" -> {
                    // 1 g/kg = 0.001 T/ha
                    result = stateMeasure.lineSpace.toDouble() * 0.001
                }

                "Mg/dm³ para G/kg" -> {
                    // 1 Mg = 1.000.000 g, 1 dm³ ≈ 1 kg (densidade da água)
                    result = stateMeasure.lineSpace.toDouble() * 1_000_000
                }

                "Kh/ha para T/ha" -> {
                    // Provável erro de digitação (Kg/ha → T/ha)
                    // 1 T = 1000 Kg
                    result = stateMeasure.lineSpace.toDouble() / 1000
                }

                "K cmol/dm³ para K G/dm³" -> {
                    // 1 cmol K/dm³ = 391 mg K/kg solo ≈ 0.000391 g/dm³
                    result = stateMeasure.lineSpace.toDouble() * 0.000391
                }

                "M para Cm" -> {
                    // 1 metro = 100 cm
                    result = stateMeasure.lineSpace.toDouble() * 100
                }

                "Cm para M" -> {
                    // 1 cm = 0.01 m
                    result = stateMeasure.lineSpace.toDouble() / 100
                }

                "T/ha para G/kg" -> {
                    // 1 T = 1.000.000 g
                    result = stateMeasure.lineSpace.toDouble() * 1_000_000
                }
            }

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
                title = "Medidas e",
                highlightedTitle = "conversões",
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
                            result = "${String.format("%.2f", result)} pl/h"
                        )
                    }
                }

                else -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            space = 40.dp
                        )
                    ) {
                        DropdownTextField(
                            label = "Medida",
                            value = stateDropdown.option,
                            placeholder = "Escolha uma das medidas",
                            options = listOf(
                                "Kg/ha para G/ha",
                                "G/dm³ para Mg/dm³",
                                "G/kg para Mg/dm³",
                                "G/kg para T/ha",
                                "Mg/dm³ para G/kg",
                                "Kh/ha para T/ha",
                                "K cmol/dm³ para K G/dm³",
                                "M para Cm",
                                "Cm para M",
                                "T/ha para G/kg"
                            ),
                            onOptionSelected = {
                                viewModelDropdown.onEvent(
                                    DropdownTextFieldFormEvent.OptionChanged(
                                        it
                                    )
                                )
                                viewModelDropdown.onEvent(DropdownTextFieldFormEvent.Submit)
                            },
                            errorState = stateDropdown.optionError
                        )

                        TextBoxRegistration(
                            value = stateMeasure.lineSpace,
                            onValueChange = {
                                viewModelMeasure.onEvent(SpaceTextFieldFormEvent.SpaceChanged(it))
                                viewModelMeasure.onEvent(SpaceTextFieldFormEvent.Submit)
                            },
                            isError = stateMeasure.lineSpaceError != null,
                            errorState = stateMeasure.lineSpaceError,
                            label =
                                when (stateDropdown.option) {
                                    "Kg/ha para G/ha" -> "Kg/ha"
                                    "G/dm³ para Mg/dm³" -> "G/dm³"
                                    "G/kg para Mg/dm³" -> "G/kg"
                                    "G/kg para T/ha" -> "G/kg"
                                    "Mg/dm³ para G/kg" -> "Mg/dm³"
                                    "Kh/ha para T/ha" -> "Kg/ha"
                                    "K cmol/dm³ para K G/dm³" -> "K cmol/dm³"
                                    "M para Cm" -> "Metros (m)"
                                    "Cm para M" -> "Centímetros (cm)"
                                    "T/ha para G/kg" -> "T/ha"
                                    else -> "Valor"
                                },
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
                        viewModelDropdown.onEvent(DropdownTextFieldFormEvent.Submit)
                        viewModelMeasure.onEvent(SpaceTextFieldFormEvent.Submit)

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