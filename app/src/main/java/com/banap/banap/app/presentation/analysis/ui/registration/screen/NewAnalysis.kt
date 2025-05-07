package com.banap.banap.app.presentation.analysis.ui.registration.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.banap.banap.app.presentation.analysis.ui.registration.components.AnalysisResult
import com.banap.banap.app.presentation.analysis.ui.registration.components.ResultCard
import com.banap.banap.app.presentation.validation.name.event.NameTextFieldFormEvent
import com.banap.banap.app.presentation.validation.name.utils.validationDataName
import com.banap.banap.app.presentation.validation.name.viewmodel.NameTextFieldViewModel
import com.banap.banap.core.ui.components.RegistrationScreenPattern
import com.banap.banap.core.ui.components.TextBoxRegistration
import kotlinx.coroutines.delay

@Composable
fun NewAnalysis(
    navigationController: NavController
) {
    val context = LocalContext.current

    val viewModelName = viewModel<NameTextFieldViewModel>()
    val stateName = viewModelName.state

    var isValidationSuccessful by remember {
        mutableStateOf(false)
    }

    var analysisMade by remember {
        mutableStateOf(false)
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    isValidationSuccessful = validationDataName(
        context = context,
        viewModelName = viewModelName,
        stateName = stateName
    )

    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(2_000)
            isLoading = false
            analysisMade = true
        }
    }

    RegistrationScreenPattern(
        navigationController = navigationController,
        fallbackRoute = "Information",
        isValidationSuccessful = isValidationSuccessful,
        stateError = stateName.nameError,
        texto = "Cálculo de Calagem do ",
        textoASerDestacado = "Solo...",
        subTitulo = "Método conhecido para calcular a quantidade de calcário necessária a ser aplicada no solo, com o objetivo de corrigir a acidez e alcançar a saturação desejada de bases.",
        children = {
            when {
                analysisMade -> {
                    AnalysisResult(
                        text = "Quantidade de calcário necessária, para que se obtenha a saturação de bases desejada, é de:",
                        children = {
                            ResultCard(
                                nutrient = "Calcário",
                                result = "260Kg/ha"
                            )

                            ResultCard(
                                nutrient = "Calcário",
                                result = "260Kg/ha"
                            )

                            ResultCard(
                                nutrient = "Calcário",
                                result = "260Kg/ha"
                            )
                        }
                    )
                }

                else -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(40.dp)
                    ) {
                        TextBoxRegistration(
                            value = stateName.name,
                            onValueChange = {
                                viewModelName.onEvent(NameTextFieldFormEvent.NameChanged(it))
                                viewModelName.onEvent(NameTextFieldFormEvent.Submit)
                            },
                            isError = stateName.nameError != null,
                            errorState = stateName.nameError,
                            label = "SBA em V%",
                            placeholder = "exemplo%",
                            tipoTeclado = KeyboardType.Text,
                            modifier = Modifier
                                .fillMaxWidth(),
                            lastOne = true,
                            informationIcon = true,
                            informationIconOnClick = {
                                navigationController.navigate("ExplanationFormData")
                            }
                        )

                        TextBoxRegistration(
                            value = stateName.name,
                            onValueChange = {
                                viewModelName.onEvent(NameTextFieldFormEvent.NameChanged(it))
                                viewModelName.onEvent(NameTextFieldFormEvent.Submit)
                            },
                            isError = stateName.nameError != null,
                            errorState = stateName.nameError,
                            label = "CTC",
                            placeholder = "exemplo",
                            tipoTeclado = KeyboardType.Text,
                            modifier = Modifier
                                .fillMaxWidth(),
                            lastOne = true,
                            informationIcon = true,
                            informationIconOnClick = {
                                navigationController.navigate("ExplanationFormData")
                            }
                        )

                        TextBoxRegistration(
                            value = stateName.name,
                            onValueChange = {
                                viewModelName.onEvent(NameTextFieldFormEvent.NameChanged(it))
                                viewModelName.onEvent(NameTextFieldFormEvent.Submit)
                            },
                            isError = stateName.nameError != null,
                            errorState = stateName.nameError,
                            label = "PRNT em %",
                            placeholder = "exemplo%",
                            tipoTeclado = KeyboardType.Text,
                            modifier = Modifier
                                .fillMaxWidth(),
                            lastOne = true,
                            informationIcon = true,
                            informationIconOnClick = {
                                navigationController.navigate("ExplanationFormData")
                            }
                        )
                    }
                }
            }
        },
        onClick = {
            viewModelName.onEvent(NameTextFieldFormEvent.Submit)

            if (isValidationSuccessful && !analysisMade) {
                isLoading = true
            } else {
                navigationController.navigate("Information")
            }
        },
        buttonValue = if (analysisMade) "Cadastrar" else "Calcular",
        isLoading = isLoading
    )
}