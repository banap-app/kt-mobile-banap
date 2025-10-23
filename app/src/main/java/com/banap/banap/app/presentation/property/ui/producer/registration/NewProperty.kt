package com.banap.banap.app.presentation.property.ui.producer.registration

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.banap.banap.app.navigation.screens.Screen
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.validation.name.event.NameTextFieldFormEvent
import com.banap.banap.app.presentation.validation.name.utils.validationDataName
import com.banap.banap.app.presentation.validation.name.viewmodel.NameTextFieldViewModel
import com.banap.banap.core.ui.components.ButtonRegistration
import com.banap.banap.core.ui.components.LoadingScreen
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.components.TextBoxRegistration
import com.banap.banap.core.ui.components.TitleRegistration
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.data.model.producer.LogList
import com.banap.banap.domain.viewmodel.property.CreatePropertyViewModel
import com.banap.banap.domain.viewmodel.property.GetPropertyByIdViewModel
import com.banap.banap.domain.viewmodel.property.UpdatePropertyViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NewProperty(
    navigationController: NavController,
    createPropertyViewModel: CreatePropertyViewModel = hiltViewModel(),
    getPropertyByIdViewModel: GetPropertyByIdViewModel = hiltViewModel(),
    updatePropertyViewModel: UpdatePropertyViewModel = hiltViewModel(),
    logList: MutableList<LogList>,
    tokenViewModel: TokenViewModel,
    propertyId: String
) {
    val context = LocalContext.current

    val createPropertyState = createPropertyViewModel.state.value
    val getPropertyByIdState = getPropertyByIdViewModel.state.value
    val updatePropertyState = updatePropertyViewModel.state.value

    val snackBarHostState = remember { SnackbarHostState() }

    val viewModelName = viewModel<NameTextFieldViewModel>()
    val stateName = viewModelName.state

    var isValidationSuccessful by remember {
        mutableStateOf(false)
    }

    var isLoading: Boolean by remember {
        mutableStateOf(false)
    }

    var propertyExists: Boolean? by remember {
        mutableStateOf(null)
    }

    var getPropertyByIdError: String by remember {
        mutableStateOf("")
    }

    var updatePropertyError: String by remember {
        mutableStateOf("")
    }

    isValidationSuccessful = validationDataName(
        context = context,
        viewModelName = viewModelName,
        stateName = stateName
    )

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
        isValidationSuccessful && stateName.nameError == null -> {
            VERDE_CLARO
        }

        else -> {
            CINZA_CLARO
        }
    }

    contentColorButton = when {
        isValidationSuccessful && stateName.nameError == null -> {
            BRANCO
        }

        else -> {
            CINZA_ESCURO
        }
    }

    LaunchedEffect(Unit) {
        if (!propertyId.contains("propertyId")) {
            tokenViewModel.getToken("updatePropertyId")?.let {
                getPropertyByIdViewModel.getPropertyById(it)
            } ?: run {
                getPropertyByIdViewModel.getPropertyById(propertyId)
            }
        }
    }

    LaunchedEffect(true) {
        tokenViewModel.getToken("propertyName")?.let {
            viewModelName.onEvent(NameTextFieldFormEvent.NameChanged(it))
            viewModelName.onEvent(NameTextFieldFormEvent.Submit)
        }
    }

    LaunchedEffect(getPropertyByIdState.error) {
        getPropertyByIdError = getPropertyByIdState.error
    }

    LaunchedEffect(getPropertyByIdState.response) {
        getPropertyByIdState.response?.let {
            tokenViewModel.saveToken("updatePropertyId", it.id)

            it.name.let { name ->
                viewModelName.onEvent(NameTextFieldFormEvent.NameChanged(name))
                viewModelName.onEvent(NameTextFieldFormEvent.Submit)

                tokenViewModel.saveToken("propertyName", name)
            }
        }
    }

    LaunchedEffect(updatePropertyState.error) {
        updatePropertyError = updatePropertyState.error
    }

    LaunchedEffect(updatePropertyState.response) {
        updatePropertyState.response?.let {
            tokenViewModel.clearToken("listingPropertyName")
            tokenViewModel.saveToken("listingPropertyName", stateName.name)

            navigationController.navigate(
                Screen.Property.createRoute(
                    name = stateName.name,
                    userName = tokenViewModel.getToken("userName") ?: "",
                    propertyId = tokenViewModel.getToken("updatePropertyId") ?: "",
                    producerId = tokenViewModel.getToken("producerId") ?: ""
                )
            )
        }
    }

    LaunchedEffect(updatePropertyState.isLoading) {
        isLoading = updatePropertyState.isLoading
    }

    LaunchedEffect(createPropertyState.error) {
        if (createPropertyState.error.isNotEmpty()) {
            isLoading = false

            var message = ""
            var showSnackBar = false

            when {
                createPropertyState.error.contains("500") -> {
                    propertyExists = true
                    Log.d("Error", createPropertyState.error)
                }

                else -> {
                    showSnackBar = true
                    message = "Não foi possível se conectar ao servidor!"
                }
            }

            Log.d("Error", createPropertyState.error)

            val autoDismissJob = launch {
                delay(5_000L)
                createPropertyViewModel.clearError()
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

            createPropertyViewModel.clearError()
        }
    }

    LaunchedEffect(createPropertyState.response) {
        createPropertyState.response?.let {
            navigationController.navigate("Home")
        }
    }

    LaunchedEffect(createPropertyState.isLoading) {
        isLoading = createPropertyState.isLoading
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = BRANCO,
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState
            ) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = BRANCO,
                    contentColor = VERDE_CLARO,
                    actionColor = VERDE_CLARO
                )
            }
        }
    ) { innerPadding ->
        if (!isLoading) {
            Column(
                modifier = Modifier
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding()
                    )
            ) {
                RegistrationHeader(
                    navigationController = navigationController,
                    fallbackRoute = "Home"
                )

                TitleRegistration(
                    texto = tokenViewModel.getToken("updatePropertyId")
                        ?.takeIf { it.isNotBlank() && !it.contains("propertyId") }
                        ?.let {
                            "Atualizando sua "
                        } ?: run {
                            "Cadastrando sua "
                    },
                    textoASerDestacado = "propriedade...",
                    corEmDestaque = VERDE_ESCURO,
                    subTexto = "",
                    tamanhoTextoDestacado = 36.sp,
                    paginaUsuario = false,
                    subtituloDestacado = "",
                    subtitulo = tokenViewModel.getToken("updatePropertyId")
                        ?.takeIf { it.isNotBlank() && !it.contains("propertyId") }
                        ?.let {
                            "Estaremos atualizando sua propriedade..."
                        } ?: run {
                            "O primeiro passo a ser feito é cadastrar sua propriedade..."
                    }
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    TextBoxRegistration(
                        value = stateName.name,
                        onValueChange = {
                            viewModelName.onEvent(NameTextFieldFormEvent.NameChanged(it))
                            viewModelName.onEvent(NameTextFieldFormEvent.Submit)

                            tokenViewModel.saveToken("propertyName", it)
                        },
                        isError = stateName.nameError != null || propertyExists == true,
                        errorState = if (propertyExists == true) "A propriedade já existe" else stateName.nameError,
                        label = "Nome da Propriedade",
                        placeholder = "Propriedade 01",
                        tipoTeclado = KeyboardType.Text,
                        modifier = Modifier
                            .fillMaxWidth(),
                        lastOne = true
                    )

                    ButtonRegistration(
                        onClick = {
                            viewModelName.onEvent(NameTextFieldFormEvent.Submit)

                            if (isValidationSuccessful) {
                                tokenViewModel.getToken("updatePropertyId")
                                    ?.takeIf { it.isNotBlank() && !it.contains("propertyId") }
                                    ?.let {
                                        updatePropertyViewModel.updateProperty(
                                            propertyId = it,
                                            name = stateName.name
                                        )
                                    } ?: run {
                                    createPropertyViewModel.createProperty(
                                        name = stateName.name
                                    )
                                }

                                logList.add(
                                    LogList(
                                        author = "Gilmar",
                                        activity = "cadastrou uma propriedade."
                                    )
                                )
                            }
                        },
                        buttonValue = tokenViewModel.getToken("updatePropertyId")
                            ?.takeIf { it.isNotBlank() && !it.contains("propertyId") }
                            ?.let {
                                "Atualizar"
                            } ?: run {
                            "Cadastrar"
                        },
                        backgroundColor = backgroundColor,
                        contentColor = contentColor
                    )
                }
            }
        } else {
            LoadingScreen()
        }
    }
}