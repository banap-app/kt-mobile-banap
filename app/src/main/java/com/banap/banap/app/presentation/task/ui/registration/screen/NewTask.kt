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
import com.banap.banap.app.presentation.validation.dropdown.event.DropdownTextFieldFormEvent
import com.banap.banap.app.presentation.validation.dropdown.utils.validationDataDropdown
import com.banap.banap.app.presentation.validation.dropdown.utils.validationDataDropdownPriority
import com.banap.banap.app.presentation.validation.dropdown.viewmodel.DropdownPriorityTextFieldViewModel
import com.banap.banap.app.presentation.validation.dropdown.viewmodel.DropdownTextFieldViewModel
import com.banap.banap.app.presentation.validation.name.event.NameTextFieldFormEvent
import com.banap.banap.app.presentation.validation.name.utils.validationDataName
import com.banap.banap.app.presentation.validation.name.viewmodel.NameTextFieldViewModel
import com.banap.banap.app.presentation.validation.scheduling.utils.validationDataScheduling
import com.banap.banap.core.ui.components.DropdownTextField
import com.banap.banap.core.ui.components.RegistrationScreenPattern
import com.banap.banap.core.ui.components.TextBoxRegistration
import com.banap.banap.data.model.producer.LogList
import com.banap.banap.data.model.producer.Task
import com.banap.banap.data.model.producer.TaskList
import kotlinx.coroutines.delay

@Composable
fun NewTask(
    navigationController: NavController,
    taskListHome: MutableList<TaskList>,
    taskListFieldInformation: MutableList<String>,
    logList: MutableList<LogList>
) {
    val context = LocalContext.current

    val viewModelName = viewModel<NameTextFieldViewModel>()
    val stateName = viewModelName.state

    val viewModelDropdownField = viewModel<DropdownTextFieldViewModel>()
    val stateDropdownField = viewModelDropdownField.state

    val viewModelDropdownPriority = viewModel<DropdownPriorityTextFieldViewModel>()
    val stateDropdownPriority = viewModelDropdownPriority.state

    var isLoading by remember {
        mutableStateOf(false)
    }

    var startTime = remember {
        mutableStateOf("")
    }
    var endTime = remember {
        mutableStateOf("")
    }

    var startError = remember {
        mutableStateOf("")
    }
    var endError = remember {
        mutableStateOf("")
    }

    val validationDataName = validationDataName(
        context = context,
        viewModelName = viewModelName,
        stateName = stateName
    )

    val validationDataOptionField = validationDataDropdown(
        context = context,
        viewModelDropdown = viewModelDropdownField,
        stateDropdown = stateDropdownField
    )

    val validationDataDropdownPriority = validationDataDropdownPriority(
        context = context,
        viewModelDropdown = viewModelDropdownPriority,
        stateDropdown = stateDropdownPriority
    )

    val validationDataScheduling = validationDataScheduling(
        startTime = startTime.value,
        endTime = endTime.value
    )

    val isValidationSuccessful = validationDataName && validationDataOptionField && validationDataDropdownPriority && validationDataScheduling

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
        fallbackRoute = "Information",
        texto = "Criando uma ",
        textoASerDestacado = "tarefa...",
        tamanhoTextoDestacado = 36,
        subTitulo = "Planeje e gerencie suas tarefas diárias com facilidade! Defina prioridades, prazos e se organize com maior facilidade!",
        buttonValue = "Cadastrar",
        onClick = {
            viewModelName.onEvent(NameTextFieldFormEvent.Submit)
            viewModelDropdownField.onEvent(DropdownTextFieldFormEvent.Submit)

            if (!validationDataScheduling) {
                startError.value = "* Requerido"
                endError.value = "* Requerido"
            }

            viewModelDropdownPriority.onEvent(DropdownTextFieldFormEvent.Submit)

            if (isValidationSuccessful) {
                taskListHome.add(
                    TaskList(
                        propertyName = "Propriedade 01",
                        tasks = listOf(
                            Task(
                                fieldName = "Talhão 01",
                                taskName = listOf(
                                    stateName.name
                                )
                            )
                        )
                    )
                )

                taskListFieldInformation.add(
                    stateName.name
                )

                logList.add(
                    LogList(
                        author = "Gilmar",
                        activity = "criou uma tarefa."
                    )
                )

                isLoading = true
            }
        },
        isValidationSuccessful = isValidationSuccessful,
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
                    value = stateDropdownField.option,
                    placeholder = "Escolha um talhão para a tarefa",
                    options = listOf(
                        "Talhão 1"
                    ),
                    onOptionSelected = {
                        viewModelDropdownField.onEvent(
                            DropdownTextFieldFormEvent.OptionChanged(
                                it
                            )
                        )
                        viewModelDropdownField.onEvent(DropdownTextFieldFormEvent.Submit)
                    },
                    errorState = stateDropdownField.optionError
                )

                Scheduling(
                    label = "Agendamento",
                    startTime = startTime,
                    endTime = endTime,
                    startError = startError,
                    endError = endError
                )

                DropdownTextField(
                    label = "Prioridade",
                    value = stateDropdownPriority.optionPriority,
                    placeholder = "Defina uma prioridade",
                    options = listOf(
                        "Baixa prioridade",
                        "Prioridade média",
                        "Alta prioridade"
                    ),
                    onOptionSelected = {
                        viewModelDropdownPriority.onEvent(
                            DropdownTextFieldFormEvent.OptionChanged(
                                it
                            )
                        )
                        viewModelDropdownPriority.onEvent(DropdownTextFieldFormEvent.Submit)
                    },
                    errorState = stateDropdownPriority.optionPriorityError
                )
            }

        }
    )
}