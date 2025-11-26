package com.banap.banap.app.presentation.producer.ui.registration

import android.content.Intent
import android.os.Build
import android.os.Build.VERSION_CODES
import android.provider.Settings.ACTION_WIFI_SETTINGS
import android.provider.Settings.Panel.ACTION_INTERNET_CONNECTIVITY
import android.util.Log
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.validation.email.event.EmailTextFieldFormEvent
import com.banap.banap.app.presentation.validation.email.utils.validationDataEmail
import com.banap.banap.app.presentation.validation.email.viewmodel.EmailTextFieldViewModel
import com.banap.banap.app.presentation.validation.name.event.NameTextFieldFormEvent
import com.banap.banap.app.presentation.validation.name.utils.validationDataName
import com.banap.banap.app.presentation.validation.name.viewmodel.NameTextFieldViewModel
import com.banap.banap.app.presentation.validation.password.event.PasswordTextFieldFormEvent
import com.banap.banap.app.presentation.validation.password.utils.validationDataPassword
import com.banap.banap.app.presentation.validation.password.viewmodel.PasswordTextFieldViewModel
import com.banap.banap.core.ui.components.ButtonRegistration
import com.banap.banap.core.ui.components.LoadingScreen
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.components.TextBoxRegistration
import com.banap.banap.core.ui.components.TitleRegistration
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.util.ConnectivityAwareContent
import com.banap.banap.domain.viewmodel.login.LoginViewModel
import com.banap.banap.domain.viewmodel.producer.CreateProducerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NewProducer(
    navigationController: NavController,
    createProducerViewModel: CreateProducerViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel(),
    tokenViewModel: TokenViewModel
) {
    val context = LocalContext.current

    val snackBarHostState = remember { SnackbarHostState() }

    val createProducerState = createProducerViewModel.state.value
    val loginState = loginViewModel.state.value

    val viewModelName = viewModel<NameTextFieldViewModel>()
    val stateName = viewModelName.state

    val viewModelEmail = viewModel<EmailTextFieldViewModel>()
    val stateEmail = viewModelEmail.state

    val viewModelPassword = viewModel<PasswordTextFieldViewModel>()
    val statePassword = viewModelPassword.state

    val validationDataName = validationDataName(
        context = context,
        viewModelName = viewModelName,
        stateName = stateName
    )

    val validationDataEmail = validationDataEmail(
        context = context,
        viewModelEmail = viewModelEmail,
        stateEmail = stateEmail
    )

    val validationDataPassword = validationDataPassword(
        context = context,
        viewModelPassword = viewModelPassword,
        statePassword = statePassword
    )

    val isValidationSuccessful = validationDataName && validationDataEmail && validationDataPassword

    var isLoading: Boolean by remember {
        mutableStateOf(false)
    }

    var isApplicationOnline: Boolean? by remember {
        mutableStateOf(null)
    }

    var userExists: Boolean? by remember {
        mutableStateOf(null)
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

    LaunchedEffect(true) {
        tokenViewModel.getToken("name")?.let {
            viewModelName.onEvent(NameTextFieldFormEvent.NameChanged(it))
            viewModelName.onEvent(NameTextFieldFormEvent.Submit)
        }
    }

    LaunchedEffect(true) {
        tokenViewModel.getToken("email")?.let {
            viewModelEmail.onEvent(EmailTextFieldFormEvent.EmailChanged(it))
            viewModelEmail.onEvent(EmailTextFieldFormEvent.Submit)
        }
    }

    LaunchedEffect(true) {
        tokenViewModel.getToken("password")?.let {
            viewModelPassword.onEvent(PasswordTextFieldFormEvent.PasswordChanged(it))
            viewModelPassword.onEvent(PasswordTextFieldFormEvent.Submit)
        }
    }

    ConnectivityAwareContent { isOnline ->
        isApplicationOnline = isOnline

        LaunchedEffect(isOnline) {
            if (!isOnline) {
                val result = snackBarHostState.showSnackbar(
                    message = "Sem conexão de internet",
                    actionLabel = "RECONECTAR",
                    duration = SnackbarDuration.Indefinite
                )
                if (result == SnackbarResult.ActionPerformed) {
                    val intent = if (Build.VERSION.SDK_INT >= VERSION_CODES.Q) {
                        Intent(ACTION_INTERNET_CONNECTIVITY)
                    } else {
                        Intent(ACTION_WIFI_SETTINGS)
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            } else {
                snackBarHostState.currentSnackbarData?.dismiss()
            }
        }
    }

    LaunchedEffect(createProducerState.response) {
        createProducerState.response?.let {
            if (it.id.isNotEmpty()) {
                navigationController.navigate("login")
            }
        }
    }

    LaunchedEffect(createProducerState.error) {
        if (createProducerState.error.isNotEmpty()) {
            isLoading = false

            var message = ""
            var showSnackBar = false

            when {
                createProducerState.error.contains("500") -> {
                    userExists = true
                    Log.d("CREATE PRODUCER Error", createProducerState.error)
                }

                else -> {
                    showSnackBar = true
                    message = "Não foi possível se conectar ao servidor!"
                }
            }

            Log.d("CREATE PRODUCER Error", createProducerState.error)

            val autoDismissJob = launch {
                delay(5_000L)
                createProducerViewModel.clearError()
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

            createProducerViewModel.clearError()
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
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding()
                    )
            ) {
                RegistrationHeader(
                    navigationController = navigationController,
                    fallbackRoute = "UserChoice"
                )

                TitleRegistration(
                    texto = "Olá, ",
                    textoASerDestacado = "Produtor!",
                    corEmDestaque = VERDE_CLARO,
                    subTexto = "Antes de tudo...",
                    tamanhoTextoDestacado = 28.sp,
                    paginaUsuario = true,
                    subtituloDestacado = "Um cadastro deve ser realizado!",
                    subtitulo = "Precisamos das suas informações, nos diga seu..."
                )

                Column(
                    Modifier
                        .padding(
                            bottom = 60.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(40.dp)
                ) {
                    TextBoxRegistration(
                        value = stateName.name,
                        onValueChange = {
                            viewModelName.onEvent(NameTextFieldFormEvent.NameChanged(it))
                            viewModelName.onEvent(NameTextFieldFormEvent.Submit)

                            tokenViewModel.saveToken("name", it)
                        },
                        isError = stateName.nameError != null,
                        errorState = stateName.nameError,
                        label = "Nome",
                        placeholder = "Exemplo",
                        tipoTeclado = KeyboardType.Text,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    TextBoxRegistration(
                        value = stateEmail.email,
                        onValueChange = {
                            viewModelEmail.onEvent(EmailTextFieldFormEvent.EmailChanged(it))
                            viewModelEmail.onEvent(EmailTextFieldFormEvent.Submit)

                            tokenViewModel.saveToken("email", it)
                        },
                        isError = stateEmail.emailError != null || userExists == true,
                        errorState = if (userExists == true) "O email já está cadastrado" else stateEmail.emailError,
                        label = "Email",
                        placeholder = "exemplo@gmail.com",
                        tipoTeclado = KeyboardType.Email,
                        modifier = Modifier
                            .onFocusChanged {
                                if (it.isFocused) {
                                    userExists = null
                                }
                            }
                            .fillMaxWidth()
                    )

                    TextBoxRegistration(
                        value = statePassword.password,
                        onValueChange = {
                            viewModelPassword.onEvent(
                                PasswordTextFieldFormEvent.PasswordChanged(
                                    it
                                )
                            )
                            viewModelPassword.onEvent(PasswordTextFieldFormEvent.Submit)

                            tokenViewModel.saveToken("password", it)
                        },
                        isError = statePassword.passwordError != null,
                        errorState = statePassword.passwordError,
                        isPassword = true,
                        label = "Senha",
                        placeholder = "Senha123#",
                        tipoTeclado = KeyboardType.Password,
                        modifier = Modifier
                            .fillMaxWidth(),
                        lastOne = true
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                ButtonRegistration(
                    onClick = {
                        viewModelName.onEvent(NameTextFieldFormEvent.Submit)
                        viewModelEmail.onEvent(EmailTextFieldFormEvent.Submit)
                        viewModelPassword.onEvent(PasswordTextFieldFormEvent.Submit)

                        if (isValidationSuccessful && isApplicationOnline == true) {
                            createProducerViewModel.createProducer(
                                name = stateName.name,
                                email = stateEmail.email,
                                password = statePassword.password
                            )

                            isLoading = true
                        }
                    },
                    buttonValue = "Cadastrar",
                    backgroundColor = backgroundColor,
                    contentColor = contentColor
                )
            }
        } else {
            LoadingScreen()
        }
    }
}
