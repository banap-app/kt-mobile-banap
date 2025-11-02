package com.banap.banap.app.presentation.analysis.ui.producer.registration.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.banap.banap.app.navigation.screens.Screen
import com.banap.banap.app.presentation.analysis.ui.producer.registration.components.AnalysisResult
import com.banap.banap.app.presentation.analysis.ui.producer.registration.components.ResultCard
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
import com.banap.banap.core.ui.components.RegistrationScreenPattern
import com.banap.banap.core.ui.components.TextBoxRegistration
import com.banap.banap.data.model.analysis.TypeAnalysis
import com.banap.banap.domain.viewmodel.analysis.CreateAnalysisViewModel

@Composable
fun NewLimingCalculation(
    navigationController: NavController,
    createAnalysisViewModel: CreateAnalysisViewModel = hiltViewModel(),
    tokenViewModel: TokenViewModel
) {
    val context = LocalContext.current

    val createAnalysisState = createAnalysisViewModel.state.value

    val viewModelSba = viewModel<SBATextFieldViewModel>()
    val stateSba = viewModelSba.state

    val viewModelCtc = viewModel<CTCTextFieldViewModel>()
    val stateCtc = viewModelCtc.state

    val viewModelPrnt = viewModel<PRNTextFieldViewModel>()
    val statePrnt = viewModelPrnt.state

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

    var analysisMade by remember {
        mutableStateOf(false)
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var error by remember {
        mutableStateOf("")
    }

    var limingCalculation by remember {
        mutableDoubleStateOf(0.0)
    }

    var page: Int by remember {
        mutableIntStateOf(1)
    }

    var userName by remember {
        mutableStateOf("")
    }

    var fieldId by remember {
        mutableStateOf("")
    }

    LaunchedEffect(true) {
        tokenViewModel.clearToken("id")
    }

    LaunchedEffect(true) {
        tokenViewModel.getToken("sba")?.let {
            viewModelSba.onEvent(SBATextFieldFormEvent.SBAChanged(it))
            viewModelSba.onEvent(SBATextFieldFormEvent.Submit)
        }
    }

    LaunchedEffect(true) {
        tokenViewModel.getToken("ctc")?.let {
            viewModelCtc.onEvent(CTCTextFieldFormEvent.CTChanged(it))
            viewModelCtc.onEvent(CTCTextFieldFormEvent.Submit)
        }
    }

    LaunchedEffect(true) {
        tokenViewModel.getToken("prnt")?.let {
            viewModelPrnt.onEvent(PRNTextFieldFormEvent.PRNTChanged(it))
            viewModelPrnt.onEvent(PRNTextFieldFormEvent.Submit)
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
            limingCalculation = it.typeAnalysis.liming ?: 0.0
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
        stateError = stateSba.sbaError,
        texto = "Cálculo de Calagem do ",
        textoASerDestacado = "Solo...",
        subTitulo =
        if (!analysisMade) {
            "Método conhecido para calcular a quantidade de calcário necessária a ser aplicada no solo, com o objetivo de corrigir a acidez e alcançar a saturação desejada de bases."
        } else {
            "Esse é o resultado do cálculo de calagem que foi feito baseado nas informações disponibilzadas por você:"
        },
        children = {
            when {
                analysisMade -> {
                    AnalysisResult(
                        text = "Quantidade de calcário necessária, para que se obtenha a saturação de bases desejada, é de:",
                        children = {
                            ResultCard(
                                nutrient = "Calcário",
                                result = "${String.format("%.2f", limingCalculation)} t/ha"
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
                            value = stateSba.sba,
                            onValueChange = {
                                viewModelSba.onEvent(SBATextFieldFormEvent.SBAChanged(it))
                                viewModelSba.onEvent(SBATextFieldFormEvent.Submit)

                                tokenViewModel.saveToken("sba", it)
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
                                        typeUser = "producer"
                                    )
                                )
                            }
                        )

                        TextBoxRegistration(
                            value = stateCtc.ctc,
                            onValueChange = {
                                viewModelCtc.onEvent(CTCTextFieldFormEvent.CTChanged(it))
                                viewModelCtc.onEvent(CTCTextFieldFormEvent.Submit)

                                tokenViewModel.saveToken("ctc", it)
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
                                        typeUser = "producer"
                                    )
                                )
                            }
                        )

                        TextBoxRegistration(
                            value = statePrnt.prnt,
                            onValueChange = {
                                viewModelPrnt.onEvent(PRNTextFieldFormEvent.PRNTChanged(it))
                                viewModelPrnt.onEvent(PRNTextFieldFormEvent.Submit)

                                tokenViewModel.saveToken("prnt", it)
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
                                        typeUser = "producer"
                                    )
                                )
                            }
                        )
                    }
                }
            }
        },
        onClick = {
            if (page == 1) {
                viewModelSba.onEvent(SBATextFieldFormEvent.Submit)
                viewModelCtc.onEvent(CTCTextFieldFormEvent.Submit)
                viewModelPrnt.onEvent(PRNTextFieldFormEvent.Submit)

                if (isValidationSuccessful && !analysisMade) {
                    createAnalysisViewModel.createAnalysis(
                        fieldId = fieldId,
                        typeAnalysis = TypeAnalysis(
                            desiredBaseSaturation = 70.0,
                            currentBaseSaturation = stateSba.sba.toDouble(),
                            totalCationExchangeCapacity = stateCtc.ctc.toDouble(),
                            relativeTotalNeutralizingPower = statePrnt.prnt.toDouble()
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
        isLoading = isLoading,
        page = page
    )
}