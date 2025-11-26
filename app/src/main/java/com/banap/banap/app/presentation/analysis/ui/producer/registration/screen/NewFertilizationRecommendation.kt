package com.banap.banap.app.presentation.analysis.ui.producer.registration.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.banap.banap.app.navigation.screens.Screen
import com.banap.banap.app.presentation.analysis.ui.producer.registration.components.AnalysisResult
import com.banap.banap.app.presentation.analysis.ui.producer.registration.components.ResultCard
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
import com.banap.banap.core.ui.components.DropdownTextField
import com.banap.banap.core.ui.components.RegistrationScreenPattern
import com.banap.banap.core.ui.components.TextBoxRegistration
import com.banap.banap.data.model.analysis.TypeAnalysis
import com.banap.banap.domain.model.analysis.NPKResult
import com.banap.banap.domain.viewmodel.analysis.CreateAnalysisViewModel

@Composable
fun NewFertilizationRecommendation(
    navigationController: NavController,
    createAnalysisViewModel: CreateAnalysisViewModel = hiltViewModel(),
    tokenViewModel: TokenViewModel
) {
    val context = LocalContext.current

    val createAnalysisState = createAnalysisViewModel.state.value

    val viewModelDropdown = viewModel<DropdownTextFieldViewModel>()
    val stateDropdown = viewModelDropdown.state

    val viewModelPhosphorus = viewModel<PhosphorusTextFieldViewModel>()
    val statePhosphorus = viewModelPhosphorus.state

    val viewModelPotassium = viewModel<PotassiumTextFieldViewModel>()
    val statePotassium = viewModelPotassium.state

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

    var analysisMade by remember {
        mutableStateOf(false)
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var error by remember {
        mutableStateOf("")
    }

    var npkResult: NPKResult? by remember {
        mutableStateOf(null)
    }

    var page: Int by remember {
        mutableIntStateOf(1)
    }

    var fieldId by remember {
        mutableStateOf("")
    }

    var userName by remember {
        mutableStateOf("")
    }

    LaunchedEffect(true) {
        tokenViewModel.getToken("phosphorus")?.let {
            viewModelPhosphorus.onEvent(PhosphorusTextFieldFormEvent.PhosphorusChanged(it))
            viewModelPhosphorus.onEvent(PhosphorusTextFieldFormEvent.Submit)
        }
    }

    LaunchedEffect(true) {
        tokenViewModel.getToken("potassium")?.let {
            viewModelPotassium.onEvent(PotassiumTextFieldFormEvent.PotassiumChanged(it))
            viewModelPotassium.onEvent(PotassiumTextFieldFormEvent.Submit)
        }
    }

    LaunchedEffect(true) {
        tokenViewModel.getToken("option")?.let {
            viewModelDropdown.onEvent(DropdownTextFieldFormEvent.OptionChanged(it))
            viewModelDropdown.onEvent(DropdownTextFieldFormEvent.Submit)
        }
    }

    LaunchedEffect(true) {
        tokenViewModel.getToken("fieldId")?.let {
            Log.d("ID", it)
            fieldId = it
        }
    }

    LaunchedEffect(true) {
        tokenViewModel.getToken("userName")?.let {
            Log.d("userName", it)
            userName = it
        }
    }

    LaunchedEffect(createAnalysisState.response) {
        createAnalysisState.response?.let {
            page = 2
            analysisMade = true
            npkResult = NPKResult(
                nitrogen = it.typeAnalysis.nitrogen ?: 0.0,
                phosphor = it.typeAnalysis.phosphor ?: 0.0,
                potassium = it.typeAnalysis.potassium ?: 0.0
            )
        }
    }

    LaunchedEffect(createAnalysisState.isLoading) {
        isLoading = createAnalysisState.isLoading
    }

    LaunchedEffect(createAnalysisState.error) {
        error = createAnalysisState.error
    }

    RegistrationScreenPattern(
        navigationController = navigationController,
        fieldId = fieldId,
        userName = userName,
        fallbackRoute = "Information",
        isValidationSuccessful = isValidationSuccessful,
        stateError = null,
        texto = "Cálculo de Recomendação de adubação de ",
        textoASerDestacado = "Solo...",
        subTitulo =
        if (!analysisMade) {
            "Precisamos que você nos informe o nível de nutrientes do seu solo! Depois, nos diga a produtividade que deseja obter..."
        } else {
            "Esse é o resultado do cálculo. A quantidade de nitrogênio, fosfóro e potássio que nós recomendamos que seja aplicado ao solo, é de:"
        },
        children = {
            when {
                analysisMade -> {
                    AnalysisResult(
                        text = "Quantidade de nitrogênio, fosfóro e potásssio necessário, para que se obtenha a produtividade esperada, é de:",
                        children = {
                            ResultCard(
                                nutrient = "Potássio",
                                result = "${npkResult?.potassium} Kg/ha"
                            )

                            ResultCard(
                                nutrient = "Fosfóro",
                                result = "${npkResult?.phosphor} Kg/ha"
                            )

                            ResultCard(
                                nutrient = "Nitrogênio",
                                result = "${npkResult?.nitrogen} Kg/ha"
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
                        verticalArrangement = Arrangement.spacedBy(40.dp)
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

                                tokenViewModel.saveToken("phosphorus", it)
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

                                tokenViewModel.saveToken("potassium", it)
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

                                tokenViewModel.saveToken("option", it)
                            },
                            errorState = stateDropdown.optionError
                        )
                    }
                }
            }
        },
        onClick = {
            if (page == 1) {
                viewModelPhosphorus.onEvent(PhosphorusTextFieldFormEvent.Submit)
                viewModelPotassium.onEvent(PotassiumTextFieldFormEvent.Submit)
                viewModelDropdown.onEvent(DropdownTextFieldFormEvent.Submit)

                if (isValidationSuccessful && !analysisMade) {
                    createAnalysisViewModel.createAnalysis(
                        fieldId = fieldId,
                        typeAnalysis = TypeAnalysis(
                            phosphor = statePhosphorus.phosphorus.toDouble(),
                            potassium = statePotassium.potassium.toDouble(),
                            expectedProductivity =
                            when (stateDropdown.option) {
                                "Menor que 20 t/ha" -> 19
                                "Entre 20 e 30 t/ha" -> 29
                                "Entre 30 e 40 t/ha" -> 39
                                "Entre 40 e 50 t/ha" -> 49
                                "Maior que 50 t/ha" -> 60
                                else -> 0
                            }
                        )
                    )
                }
            } else {
                navigationController.navigate(
                    Screen.Information.createRoute(
                        fieldId = fieldId,
                        userName = userName
                    )
                )
            }
        },
        buttonValue = if (analysisMade) "Cadastrar" else "Calcular",
        isLoading = isLoading
    )
}