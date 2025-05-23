package com.banap.banap.app.presentation.field.ui.registration.components

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.validation.description.event.DescriptionTextFieldFormEvent
import com.banap.banap.app.presentation.validation.description.utils.validationDataDescription
import com.banap.banap.app.presentation.validation.description.viewmodel.DescriptionTextFieldViewModel
import com.banap.banap.app.presentation.validation.dropdown.event.DropdownTextFieldFormEvent
import com.banap.banap.app.presentation.validation.dropdown.utils.validationDataDropdown
import com.banap.banap.app.presentation.validation.dropdown.viewmodel.DropdownTextFieldViewModel
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import com.banap.banap.app.util.enumerator.FieldPage
import com.banap.banap.core.ui.components.ButtonRegistration
import com.banap.banap.core.ui.components.DropdownTextField
import com.banap.banap.core.ui.components.LoadingScreen
import com.banap.banap.core.ui.components.TextBoxRegistration
import com.banap.banap.core.ui.components.TitleRegistration
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.data.model.producer.LogList
import com.banap.banap.domain.model.field.FieldBoundary
import com.banap.banap.domain.viewmodel.field.CreateFieldViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ThirdPage(
    navigationController: NavController,
    createFieldViewModel: CreateFieldViewModel = hiltViewModel(),
    currentPage: MutableState<FieldPage>,
    snackBarHostState: SnackbarHostState,
    context: Context,
    producerId: String,
    propertyId: String,
    stateName: RegistrationFormState,
    markers: List<LatLng>,
    logList: MutableList<LogList>,
    isValidationSuccessful: MutableState<Boolean>
) {
    val createFieldState = createFieldViewModel.state.value

    val fieldBoundary: List<FieldBoundary> by remember {
        mutableStateOf(
            markers.map {
                FieldBoundary(
                    lat = it.latitude,
                    lng = it.longitude
                )
            }
        )
    }
    val viewModelDescription = viewModel<DescriptionTextFieldViewModel>()
    val stateDescription = viewModelDescription.state

    val viewModelDropdown = viewModel<DropdownTextFieldViewModel>()
    val stateDropdown = viewModelDropdown.state

    val validationDataDescription = validationDataDescription(
        context = context,
        viewModelDescription = viewModelDescription,
        stateDescription = stateDescription
    )

    val validationDataDropdown = validationDataDropdown(
        context = context,
        viewModelDropdown = viewModelDropdown,
        stateDropdown = stateDropdown
    )

    isValidationSuccessful.value = validationDataDescription && validationDataDropdown

    var isLoading: Boolean by remember {
        mutableStateOf(false)
    }

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
        isValidationSuccessful.value -> {
            VERDE_CLARO
        }

        else -> {
            CINZA_CLARO
        }
    }

    contentColorButton = when {
        isValidationSuccessful.value -> {
            BRANCO
        }

        else -> {
            CINZA_ESCURO
        }
    }

    LaunchedEffect(createFieldState.error) {
        if (createFieldState.error.isNotEmpty()) {
            isLoading = false

            var message = ""
            var showSnackBar = false

            when {
                createFieldState.error.contains("500") -> {
                    Log.d("Error", createFieldState.error)
                }

                else -> {
                    showSnackBar = true
                    message = "Não foi possível se conectar ao servidor!"
                }
            }

            Log.d("Error", createFieldState.error)

            val autoDismissJob = launch {
                delay(5_000L)
                snackBarHostState.currentSnackbarData?.dismiss()
            }

            if (showSnackBar) {
                snackBarHostState.showSnackbar(
                    message = message,
                    actionLabel = "Entendi",
                    duration = SnackbarDuration.Indefinite
                )
            }

            autoDismissJob.cancel()
        }
    }

    LaunchedEffect(createFieldState.response) {
        createFieldState.response?.let {
            navigationController.navigate("Home")
        }
    }

    if (!isLoading) {
        Column {
            Box {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.linhas_propriedade),
                    contentDescription = "Vetor de linhas",
                    modifier = Modifier
                        .padding(
                            top = 17.dp
                        )
                        .fillMaxWidth()
                        .scale(1.2F)
                )

                IconButton(
                    onClick = {
                        currentPage.value = FieldPage.SECOND
                    },
                    modifier = Modifier
                        .padding(
                            top = 40.dp,
                            start = 20.dp,
                            bottom = 40.dp
                        )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
                        contentDescription = "Icone de voltar",
                        modifier = Modifier
                            .scale(1.2F)
                    )
                }
            }

            TitleRegistration(
                texto = "Cadastrando seu ",
                textoASerDestacado = "Talhão...",
                corEmDestaque = VERDE_ESCURO,
                subTexto = "",
                tamanhoTextoDestacado = 36.sp,
                paginaUsuario = false,
                subtituloDestacado = "",
                subtitulo = ""
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(40.dp)
                ) {
                    TextBoxRegistration(
                        value = stateDescription.description,
                        onValueChange = {
                            viewModelDescription.onEvent(
                                DescriptionTextFieldFormEvent.DescriptionChanged(
                                    it
                                )
                            )
                            viewModelDescription.onEvent(DescriptionTextFieldFormEvent.Submit)
                        },
                        isError = stateDescription.descriptionError != null,
                        errorState = stateDescription.descriptionError,
                        label = "Descrição",
                        placeholder = "Descreva seu talhão",
                        tipoTeclado = KeyboardType.Text,
                        modifier = Modifier
                            .heightIn(
                                min = 70.dp,
                                max = 100.dp
                            )
                            .fillMaxWidth(),
                        lastOne = true,
                        maxLines = 10
                    )

                    DropdownTextField(
                        label = "Cultura",
                        value = stateDropdown.option,
                        placeholder = "Escolha uma cultura",
                        options = listOf(
                            "Banana Nanica",
                            "Banana Prata",
                            "Banana da Terra",
                            "Banana Maçã",
                            "Banana Ouro"
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

                ButtonRegistration(
                    onClick = {
                        viewModelDescription.onEvent(DescriptionTextFieldFormEvent.Submit)
                        viewModelDropdown.onEvent(DropdownTextFieldFormEvent.Submit)

                        if (isValidationSuccessful.value) {
                            createFieldViewModel.createField(
                                producerId = producerId,
                                propertyId = propertyId,
                                name = stateName.name,
                                description = stateDescription.description,
                                crop = stateDropdown.option,
                                fieldBoundary = fieldBoundary
                            )

                            logList.add(
                                LogList(
                                    author = "Gilmar",
                                    activity = "cadastrou um talhão."
                                )
                            )

                            isLoading = true
                        }
                    },
                    buttonValue = "Continuar",
                    backgroundColor = backgroundColor,
                    contentColor = contentColor
                )
            }
        }
    } else {
        LoadingScreen()
    }
}