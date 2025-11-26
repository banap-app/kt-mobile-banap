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
import com.banap.banap.app.presentation.analysis.ui.producer.registration.components.AnalysisResult
import com.banap.banap.app.presentation.analysis.ui.producer.registration.components.ResultCard
import com.banap.banap.app.presentation.client.ui.registration.components.EngineerTitleRegistration
import com.banap.banap.app.presentation.home.ui.components.ScaffoldCustomizedForEngineerScreens
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.validation.dropdown.event.DropdownTextFieldFormEvent
import com.banap.banap.app.presentation.validation.dropdown.utils.validationDataDropdown
import com.banap.banap.app.presentation.validation.dropdown.viewmodel.DropdownTextFieldViewModel
import com.banap.banap.app.presentation.validation.phosphorus.event.PhosphorusTextFieldFormEvent
import com.banap.banap.app.presentation.validation.phosphorus.utils.validationDataPhosphorus
import com.banap.banap.app.presentation.validation.phosphorus.viewmodel.PhosphorusTextFieldViewModel
import com.banap.banap.app.presentation.validation.potassium.event.PotassiumTextFieldFormEvent
import com.banap.banap.app.presentation.validation.potassium.utils.validationDataPotassium
import com.banap.banap.app.presentation.validation.potassium.viewmodel.PotassiumTextFieldViewModel
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
fun NewEngineerFertilizationRecommendationAnalysis(
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

    // Variaveis do TextField do Fosforo

    val viewModelPhosphorus = viewModel<PhosphorusTextFieldViewModel>()
    val statePhosphorus = viewModelPhosphorus.state

    // Variaveis do TextField do Potassio

    val viewModelPotassium = viewModel<PotassiumTextFieldViewModel>()
    val statePotassium = viewModelPotassium.state

    // Variaveis do TextField do Dropdown

    val viewModelDropdown = viewModel<DropdownTextFieldViewModel>()
    val stateDropdown = viewModelDropdown.state

    // Variaveis para validação dos campos

    val validationDataDropdown = validationDataDropdown(
        context = context,
        viewModelDropdown = viewModelDropdown,
        stateDropdown = stateDropdown
    )

    val validationDataPhosphorus = validationDataPhosphorus(
        context = context,
        viewModelPhosphorus = viewModelPhosphorus,
        statePhosphorus = statePhosphorus
    )

    val validationDataPotassium = validationDataPotassium(
        context = context,
        viewModelPotassium = viewModelPotassium,
        statePotassium = statePotassium
    )

    val isValidationSuccessful =
        validationDataDropdown && validationDataPhosphorus && validationDataPotassium

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
                        text = "Quantidade de nitrogênio, fosfóro e potásssio necessário, para que se obtenha a produtividade esperada, é de:",
                        children = {
                            ResultCard(
                                nutrient = "Potássio",
                                result = "23 Kg/ha"
                            )

                            ResultCard(
                                nutrient = "Fosfóro",
                                result = "23 Kg/ha"
                            )

                            ResultCard(
                                nutrient = "Nitrogênio",
                                result = "23 Kg/ha"
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
                            value = statePhosphorus.phosphorus,
                            onValueChange = {
                                viewModelPhosphorus.onEvent(
                                    PhosphorusTextFieldFormEvent.PhosphorusChanged(
                                        it
                                    )
                                )
                                viewModelPhosphorus.onEvent(PhosphorusTextFieldFormEvent.Submit)
                            },
                            isError = statePhosphorus.phosphorusError != null,
                            errorState = statePhosphorus.phosphorusError,
                            label = "Fosfóro (P)",
                            placeholder = "exemplo mg dm⁻³",
                            tipoTeclado = KeyboardType.Text,
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        TextBoxRegistration(
                            value = statePotassium.potassium,
                            onValueChange = {
                                viewModelPotassium.onEvent(
                                    PotassiumTextFieldFormEvent.PotassiumChanged(
                                        it
                                    )
                                )
                                viewModelPotassium.onEvent(PotassiumTextFieldFormEvent.Submit)
                            },
                            isError = statePotassium.potassiumError != null,
                            errorState = statePotassium.potassiumError,
                            label = "Potássio (K)",
                            placeholder = "exemplo mmol dm⁻³",
                            tipoTeclado = KeyboardType.Text,
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        DropdownTextField(
                            label = "Produtividade Esperada",
                            value = stateDropdown.option,
                            placeholder = "Qual é a produtividade esperada?",
                            options = listOf(
                                "Menor que 20 t/ha",
                                "Entre 20 e 30 t/ha",
                                "Entre 30 e 40 t/ha",
                                "Entre 40 e 50 t/ha",
                                "Maior que 50 t/ha"
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
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            ButtonRegistration(
                onClick = {
                    if (page == 1) {
                        viewModelPhosphorus.onEvent(PhosphorusTextFieldFormEvent.Submit)
                        viewModelPotassium.onEvent(PotassiumTextFieldFormEvent.Submit)
                        viewModelDropdown.onEvent(DropdownTextFieldFormEvent.Submit)

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