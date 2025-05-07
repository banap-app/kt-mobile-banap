package com.banap.banap.app.presentation.task.ui.registration.screen

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
import com.banap.banap.app.presentation.task.ui.registration.components.Scheduling
import com.banap.banap.app.presentation.validation.cultivation.event.CultivationTextFieldFormEvent
import com.banap.banap.app.presentation.validation.cultivation.utils.validationDataCultivation
import com.banap.banap.app.presentation.validation.cultivation.viewmodel.CultivationTextFieldViewModel
import com.banap.banap.app.presentation.validation.name.event.NameTextFieldFormEvent
import com.banap.banap.app.presentation.validation.name.utils.validationDataName
import com.banap.banap.app.presentation.validation.name.viewmodel.NameTextFieldViewModel
import com.banap.banap.core.ui.components.DropdownTextField
import com.banap.banap.core.ui.components.RegistrationScreenPattern
import com.banap.banap.core.ui.components.TextBoxRegistration
import kotlinx.coroutines.delay

@Composable
fun NewTask(
    navigationController: NavController
) {
    val context = LocalContext.current

    val viewModelName = viewModel<NameTextFieldViewModel>()
    val stateName = viewModelName.state

    val viewModelCultivation = viewModel<CultivationTextFieldViewModel>()
    val stateCultivation = viewModelCultivation.state

    var isValidationSuccessful by remember {
        mutableStateOf(false)
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    val validationDataName = validationDataName(
        context = context,
        viewModelName = viewModelName,
        stateName = stateName
    )

    val validationDataCultivation = validationDataCultivation(
        context = context,
        viewModelCultivation = viewModelCultivation,
        stateCultivation = stateCultivation
    )

    isValidationSuccessful = validationDataName && validationDataCultivation

    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(2_000)

            if (!navigationController.popBackStack()) {
                navigationController.navigate("Information")
            }
        }
    }

    RegistrationScreenPattern(
        navigationController = navigationController,
        fallbackRoute = "Home",
        texto = "Criando uma ",
        textoASerDestacado = "tarefa...",
        tamanhoTextoDestacado = 36,
        subTitulo = "Planeje e gerencie suas tarefas diárias com facilidade! Defina prioridades, prazos e se organize com maior facilidade!",
        buttonValue = "Cadastrar",
        onClick = {
            viewModelName.onEvent(NameTextFieldFormEvent.Submit)

            if (validationDataName) {
                isLoading = true
            }
        },
        isValidationSuccessful = validationDataName,
        stateError = stateName.nameError,
        isLoading = isLoading,
        children = {
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
                    label = "Tarefa",
                    placeholder = "Digite aqui a tarefa",
                    tipoTeclado = KeyboardType.Text,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                DropdownTextField(
                    label = "Talhão",
                    value = stateCultivation.cultivation,
                    placeholder = "Escolha um talhão para a tarefa",
                    options = listOf(
                        "Talhão 1"
                    ),
                    onOptionSelected = {
                        viewModelCultivation.onEvent(
                            CultivationTextFieldFormEvent.CultivationChanged(
                                it
                            )
                        )
                        viewModelCultivation.onEvent(CultivationTextFieldFormEvent.Submit)
                    }
                )

                Scheduling(
                    label = "Agendamento"
                )

                DropdownTextField(
                    label = "Prioridade",
                    value = stateCultivation.cultivation,
                    placeholder = "Defina uma prioridade",
                    options = listOf(
                        "Baixa prioridade",
                        "Prioridade média",
                        "Alta prioridade"
                    ),
                    onOptionSelected = {
                        viewModelCultivation.onEvent(
                            CultivationTextFieldFormEvent.CultivationChanged(
                                it
                            )
                        )
                        viewModelCultivation.onEvent(CultivationTextFieldFormEvent.Submit)
                    }
                )
            }

        }
    )
}