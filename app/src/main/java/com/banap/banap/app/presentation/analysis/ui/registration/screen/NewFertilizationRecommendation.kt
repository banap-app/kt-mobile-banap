package com.banap.banap.app.presentation.analysis.ui.registration.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.banap.banap.app.presentation.analysis.ui.registration.components.AnalysisResult
import com.banap.banap.app.presentation.analysis.ui.registration.components.ResultCard
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
import com.banap.banap.core.ui.util.FertilizerCalculator
import com.banap.banap.data.model.producer.LogList
import com.banap.banap.domain.model.analysis.NPKResult
import kotlinx.coroutines.delay

@Composable
fun NewFertilizationRecommendation(
    navigationController: NavController,
    analysisList: MutableList<String>,
    logList: MutableList<LogList>
) {
    val context = LocalContext.current

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

    var npkResult: NPKResult? by remember {
        mutableStateOf(null)
    }

    var page: Int by remember {
        mutableIntStateOf(1)
    }

    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(1_000)
            page = 2
            isLoading = false
            analysisMade = true
        }
    }

    RegistrationScreenPattern(
        navigationController = navigationController,
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
                                "Menor que 20%",
                                "Entre 20 e 30%",
                                "Entre 30 e 40%",
                                "Entre 40 e 50%",
                                "Maior que 50%"
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
        },
        onClick = {
            if (page == 1) {
                viewModelPhosphorus.onEvent(PhosphorusTextFieldFormEvent.Submit)
                viewModelPotassium.onEvent(PotassiumTextFieldFormEvent.Submit)
                viewModelDropdown.onEvent(DropdownTextFieldFormEvent.Submit)

                if (isValidationSuccessful && !analysisMade) {
                    npkResult = FertilizerCalculator.calculateNPK(
                        phosphor = statePhosphorus.phosphorus.toDouble(),
                        potassium = statePotassium.potassium.toDouble(),
                        expectedProductivity =
                        when (stateDropdown.option) {
                            "Menor que 20%" -> 19
                            "Entre 20 e 30%" -> 29
                            "Entre 30 e 40%" -> 39
                            "Entre 40 e 50%" -> 49
                            else -> 0
                        }
                    )
                    isLoading = true
                }
            } else {
                analysisList.add("Análise 01")
                logList.add(
                    LogList(
                        author = "Gilmar",
                        activity = "cadastrou uma análise."
                    )
                )

                navigationController.navigate("Information")
            }
        },
        buttonValue = if (analysisMade) "Cadastrar" else "Calcular",
        isLoading = isLoading
    )
}