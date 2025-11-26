package com.banap.banap.app.presentation.analysis.ui.engineer.registration

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.banap.banap.app.navigation.screens.Screen
import com.banap.banap.app.presentation.analysis.ui.producer.registration.components.AnalysisResult
import com.banap.banap.app.presentation.analysis.ui.producer.registration.components.ResultCard
import com.banap.banap.app.presentation.client.ui.registration.components.EngineerTitleRegistration
import com.banap.banap.app.presentation.home.ui.components.ScaffoldCustomizedForEngineerScreens
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.validation.ctc.event.CTCTextFieldFormEvent
import com.banap.banap.app.presentation.validation.ctc.utils.validationDataCtc
import com.banap.banap.app.presentation.validation.ctc.viewmodel.CTCTextFieldViewModel
import com.banap.banap.app.presentation.validation.prnt.event.PRNTextFieldFormEvent
import com.banap.banap.app.presentation.validation.prnt.utils.validationDataPrnt
import com.banap.banap.app.presentation.validation.prnt.viewmodel.PRNTextFieldViewModel
import com.banap.banap.app.presentation.validation.sba.event.SBATextFieldFormEvent
import com.banap.banap.app.presentation.validation.sba.utils.validationDataSba
import com.banap.banap.app.presentation.validation.sba.viewmodel.SBATextFieldViewModel
import com.banap.banap.core.ui.components.ButtonRegistration
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.components.TextBoxRegistration
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import kotlinx.coroutines.delay

@Composable
fun NewEngineerLimingAnalysis(
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

    // Variaveis do TextField de SBA

    val viewModelSba = viewModel<SBATextFieldViewModel>()
    val stateSba = viewModelSba.state

    // Variaveis do TextField de CTC

    val viewModelCtc = viewModel<CTCTextFieldViewModel>()
    val stateCtc = viewModelCtc.state

    // Variaveis do TextField de PRNT

    val viewModelPrnt = viewModel<PRNTextFieldViewModel>()
    val statePrnt = viewModelPrnt.state

    // Variaveis para validação dos campos

    val validationDataSba = validationDataSba(
        context = context,
        viewModelSba = viewModelSba,
        stateSba = stateSba
    )

    val validationDataCtc = validationDataCtc(
        context = context,
        viewModelCtc = viewModelCtc,
        stateCtc = stateCtc
    )

    val validationDataPrnt = validationDataPrnt(
        context = context,
        viewModelPrnt = viewModelPrnt,
        statePrnt = statePrnt
    )

    val isValidationSuccessful = validationDataSba && validationDataCtc && validationDataPrnt

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

    // Variaveis para controle das paginas da criação das análises

    var page: Int by remember {
        mutableIntStateOf(1)
    }

    var isAnalysisMade by remember {
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
            isAnalysisMade = true
            result = 23.0
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
                fixedRoute = "EngineerHome"
            )

            EngineerTitleRegistration(
                title = "Cálculo de Calagem\ndo",
                highlightedTitle = "solo",
                description =
                    if (!isAnalysisMade) {
                        "Método conhecido para calcular a quantidade de calcário necessária a ser aplicada no solo, com o objetivo de corrigir a acidez e alcançar a saturação desejada de bases."
                    } else {
                        "Esse é o resultado do cálculo de calagem que foi feito baseado nas informações disponibilzadas por você:"
                    }
            )

            when {
                isAnalysisMade -> {
                    AnalysisResult(
                        text = "Quantidade de calcário necessária, para que se obtenha a saturação de bases desejada, é de:",
                        children = {
                            ResultCard(
                                nutrient = "Calcário",
                                result = "${String.format("%.2f", result)} t/ha"
                            )
                        }
                    )
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .padding(
                                bottom = 60.dp
                            ),
                        verticalArrangement = Arrangement.spacedBy(
                            space = 40.dp
                        )
                    ) {
                        TextBoxRegistration(
                            value = stateSba.sba,
                            onValueChange = {
                                viewModelSba.onEvent(SBATextFieldFormEvent.SBAChanged(it))
                                viewModelSba.onEvent(SBATextFieldFormEvent.Submit)
                            },
                            isError = stateSba.sbaError != null,
                            errorState = stateSba.sbaError,
                            label = "SBA em V%",
                            placeholder = "exemplo%",
                            tipoTeclado = KeyboardType.Text,
                            modifier = Modifier
                                .fillMaxWidth(),
                            informationIcon = true,
                            informationIconOnClick = {
                                navigationController.navigate(
                                    Screen.ExplanationFormData.createRoute(
                                        id = "sba",
                                        typeUser = "engineer"
                                    )
                                )
                            }
                        )

                        TextBoxRegistration(
                            value = stateCtc.ctc,
                            onValueChange = {
                                viewModelCtc.onEvent(CTCTextFieldFormEvent.CTChanged(it))
                                viewModelCtc.onEvent(CTCTextFieldFormEvent.Submit)
                            },
                            isError = stateCtc.ctcError != null,
                            errorState = stateCtc.ctcError,
                            label = "CTC",
                            placeholder = "exemplo",
                            tipoTeclado = KeyboardType.Text,
                            modifier = Modifier
                                .fillMaxWidth(),
                            informationIcon = true,
                            informationIconOnClick = {
                                navigationController.navigate(
                                    Screen.ExplanationFormData.createRoute(
                                        id = "ctc",
                                        typeUser = "engineer"
                                    )
                                )
                            }
                        )

                        TextBoxRegistration(
                            value = statePrnt.prnt,
                            onValueChange = {
                                viewModelPrnt.onEvent(PRNTextFieldFormEvent.PRNTChanged(it))
                                viewModelPrnt.onEvent(PRNTextFieldFormEvent.Submit)
                            },
                            isError = statePrnt.prntError != null,
                            errorState = statePrnt.prntError,
                            label = "PRNT em %",
                            placeholder = "exemplo%",
                            tipoTeclado = KeyboardType.Text,
                            modifier = Modifier
                                .fillMaxWidth(),
                            lastOne = true,
                            informationIcon = true,
                            informationIconOnClick = {
                                navigationController.navigate(
                                    Screen.ExplanationFormData.createRoute(
                                        id = "prnt",
                                        typeUser = "engineer"
                                    )
                                )
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            ButtonRegistration(
                onClick = {
                    if (page == 1) {
                        viewModelSba.onEvent(SBATextFieldFormEvent.Submit)
                        viewModelCtc.onEvent(CTCTextFieldFormEvent.Submit)
                        viewModelPrnt.onEvent(PRNTextFieldFormEvent.Submit)

                        if (isValidationSuccessful && !isAnalysisMade) {
                            hasLoadingScreen = true
                        }
                    } else {
                        navigationController.navigate("EngineerHome")
                    }
                },
                buttonValue = if (isAnalysisMade) "Cadastrar" else "Calcular",
                backgroundColor = backgroundColor,
                contentColor = contentColor
            )
        }
    }
}