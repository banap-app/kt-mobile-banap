package com.banap.banap.app.presentation.engineer.ui.registration

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
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.validation.crea.event.CreaTextFieldFormEvent
import com.banap.banap.app.presentation.validation.crea.utils.validationDataCrea
import com.banap.banap.app.presentation.validation.crea.viewmodel.CreaTextFieldViewModel
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
import com.banap.banap.domain.viewmodel.engineer.CreateEngineerViewModel

@Composable
fun NewEngineerSecondPage(
    navigationController: NavController,
    tokenViewModel: TokenViewModel,
    createEngineerViewModel: CreateEngineerViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    val viewModelCrea = viewModel<CreaTextFieldViewModel>()
    val stateCrea = viewModelCrea.state

    var isValidationSuccessful by remember {
        mutableStateOf(false)
    }

    isValidationSuccessful = validationDataCrea(
        context = context,
        viewModelCrea = viewModelCrea,
        stateCrea = stateCrea
    )

    var isLoading: Boolean by remember {
        mutableStateOf(false)
    }

    var hasContentError: String by remember {
        mutableStateOf("")
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
        isValidationSuccessful && stateCrea.creaError == null -> {
            VERDE_CLARO
        }

        else -> {
            CINZA_CLARO
        }
    }

    contentColorButton = when {
        isValidationSuccessful && stateCrea.creaError == null -> {
            BRANCO
        }

        else -> {
            CINZA_ESCURO
        }
    }

    // Atribuindo os valores dos campos guardados

    LaunchedEffect(true) {
        tokenViewModel.getToken("creaError")?.let {
            viewModelCrea.onEvent(
                CreaTextFieldFormEvent.CreaChanged(
                    tokenViewModel.getToken("crea") ?: ""
                )
            )
            viewModelCrea.onEvent(CreaTextFieldFormEvent.SetError(it))
        } ?: run {
            tokenViewModel.getToken("crea")?.let {
                viewModelCrea.onEvent(CreaTextFieldFormEvent.CreaChanged(it))
                viewModelCrea.onEvent(CreaTextFieldFormEvent.Submit)
            }
        }
    }

    // Cadastro de Engenheiro

    val engineerState = createEngineerViewModel.state.value

    LaunchedEffect(engineerState.response) {
        engineerState.response?.let {
            if (it.statusCode == 201) {
                navigationController.navigate("Login")
            }
        }
    }

    LaunchedEffect(engineerState.isLoading) {
        isLoading = engineerState.isLoading
    }

    LaunchedEffect(engineerState.errors) {
        engineerState.errors?.let { errorResponse ->
            if (errorResponse.statusCode == 400) {
                errorResponse.errors?.forEach { error ->
                    error.name?.first()
                        ?.let { tokenViewModel.saveToken("nameError", it) }
                    error.email?.first()
                        ?.let { tokenViewModel.saveToken("emailError", it) }
                    error.password?.first()
                        ?.let { tokenViewModel.saveToken("passwordError", it) }
                    error.crea?.first()
                        ?.let {
                            tokenViewModel.saveToken("creaError", it)
                            CreaTextFieldFormEvent.SetError(it)
                        }
                        ?.let { viewModelCrea.onEvent(it) }

                    if (
                        tokenViewModel.getToken("nameError")?.isNotEmpty() == true ||
                        tokenViewModel.getToken("emailError")?.isNotEmpty() == true ||
                        tokenViewModel.getToken("passwordError")?.isNotEmpty() == true
                    ) {
                        navigationController.navigate("NewEngineerFirstPage")
                    }
                }
            } else {
                snackBarHostState.showSnackbar(
                    message = errorResponse.message,
                    actionLabel = "Entendi",
                    duration = SnackbarDuration.Indefinite
                )
            }
        }
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
                Modifier
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding()
                    )
            ) {
                RegistrationHeader(
                    navigationController = navigationController,
                    fallbackRoute = "NewEngineerFirstPage"
                )

                TitleRegistration(
                    texto = "Precisamos dos \nseus ",
                    textoASerDestacado = "documentos...",
                    corEmDestaque = VERDE_ESCURO,
                    subTexto = "",
                    tamanhoTextoDestacado = 28.sp,
                    paginaUsuario = false,
                    subtituloDestacado = "Agora falta pouco!",
                    subtitulo = "Só precisamos de uma confirmação de seus dados... "
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    TextBoxRegistration(
                        value = stateCrea.crea,
                        onValueChange = {
                            viewModelCrea.onEvent(CreaTextFieldFormEvent.CreaChanged(it))
                            viewModelCrea.onEvent(CreaTextFieldFormEvent.Submit)

                            tokenViewModel.getToken("creaError")?.let {
                                tokenViewModel.clearToken("creaError")
                            }
                            tokenViewModel.saveToken("crea", it)
                        },
                        isError = stateCrea.creaError != null,
                        errorState = stateCrea.creaError,
                        label = "CREA",
                        placeholder = "123456-SP",
                        tipoTeclado = KeyboardType.Text,
                        modifier = Modifier
                            .fillMaxWidth(),
                        lastOne = true
                    )

                    ButtonRegistration(
                        onClick = {
                            viewModelCrea.onEvent(CreaTextFieldFormEvent.Submit)

                            if (isValidationSuccessful) {
                                createEngineerViewModel.createEngineer(
                                    name = tokenViewModel.getToken("name") ?: "",
                                    email = tokenViewModel.getToken("email") ?: "",
                                    password = tokenViewModel.getToken("password") ?: "",
                                    crea = tokenViewModel.getToken("crea") ?: ""
                                )
                            }
                        },
                        buttonValue = "Cadastrar",
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