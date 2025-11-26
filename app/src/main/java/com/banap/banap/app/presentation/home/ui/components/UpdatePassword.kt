package com.banap.banap.app.presentation.home.ui.components

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
import androidx.compose.material3.SnackbarDuration
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.banap.banap.app.presentation.client.ui.registration.components.EngineerTitleRegistration
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.validation.password.event.PasswordTextFieldFormEvent
import com.banap.banap.app.presentation.validation.password.utils.validationDataConfirmPassword
import com.banap.banap.app.presentation.validation.password.utils.validationDataNewPassword
import com.banap.banap.app.presentation.validation.password.utils.validationDataPassword
import com.banap.banap.app.presentation.validation.password.viewmodel.ConfirmPasswordTextFieldViewModel
import com.banap.banap.app.presentation.validation.password.viewmodel.NewPasswordTextFieldViewModel
import com.banap.banap.app.presentation.validation.password.viewmodel.PasswordTextFieldViewModel
import com.banap.banap.core.ui.components.ButtonRegistration
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.components.TextBoxRegistration
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.domain.viewmodel.engineer.UpdateEngineerViewModel
import com.banap.banap.domain.viewmodel.producer.UpdateProducerViewModel

@Composable
fun UpdatePassword(
    navigationController: NavController,
    updateProducerViewModel: UpdateProducerViewModel = hiltViewModel(),
    updateEngineerViewModel: UpdateEngineerViewModel = hiltViewModel(),
    tokenViewModel: TokenViewModel
) {
    // Variaveis do Scaffold

    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    // Variaveis de controle da tela

    var hasLoadingScreen: Boolean by remember {
        mutableStateOf(false)
    }

    // Variaveis do TextField da Senha Atual

    val viewModelCurrentPassword = viewModel<PasswordTextFieldViewModel>()
    val stateCurrentPassword = viewModelCurrentPassword.state

    // Variaveis do TextField da Senha Nova

    val viewModelNewPassword = viewModel<NewPasswordTextFieldViewModel>()
    val stateNewPassword = viewModelNewPassword.state

    // Variaveis do TextField de Confirmação de Senha

    val viewModelConfirmPassword = viewModel<ConfirmPasswordTextFieldViewModel>()
    val stateConfirmPassword = viewModelConfirmPassword.state

    // Validacao dos Campos

    val validationDataCurrentPassword = validationDataPassword(
        context = context,
        viewModelPassword = viewModelCurrentPassword,
        statePassword = stateCurrentPassword
    )

    val validationDataNewPassword = validationDataNewPassword(
        context = context,
        viewModelPassword = viewModelNewPassword,
        statePassword = stateNewPassword
    )

    val validationDataConfirmPassword = validationDataConfirmPassword(
        context = context,
        viewModelPassword = viewModelConfirmPassword,
        statePassword = stateConfirmPassword
    )

    val isValidationSuccessful =
        validationDataCurrentPassword && validationDataNewPassword && validationDataConfirmPassword

    // Controle das Cores do Botao

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

    // Update Producer

    val updateProducerState by updateProducerViewModel.state

    LaunchedEffect(updateProducerState.response) {
        updateProducerState.response?.let {
            if (it.success) {
                tokenViewModel.clearTokens(
                    listOf(
                        "password"
                    )
                )

                navigationController.navigate("Home")
            }
        }
    }

    LaunchedEffect(updateProducerState.error) {
        if (updateProducerState.error.isNotEmpty()) {
            snackBarHostState.showSnackbar(
                message = updateProducerState.error,
                actionLabel = "Entendi",
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    LaunchedEffect(updateProducerState.isLoading) {
        hasLoadingScreen = updateProducerState.isLoading
    }

    // Update Engineer

    val updateEngineerState by updateEngineerViewModel.state

    LaunchedEffect(updateEngineerState.response) {
        updateEngineerState.response?.let {
            if (it.statusCode == 204) {
                tokenViewModel.clearTokens(
                    listOf(
                        "password"
                    )
                )

                navigationController.navigate("EngineerHome")
            }
        }
    }

    LaunchedEffect(updateEngineerState.errors) {
        updateEngineerState.errors?.let { errorResponse ->
            if (errorResponse.statusCode == 400) {
                errorResponse.errors?.forEach { error ->
                    error.password?.first()
                        ?.let { PasswordTextFieldFormEvent.SetError(it) }
                        ?.let { viewModelCurrentPassword.onEvent(it) }
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

    LaunchedEffect(updateEngineerState.isLoading) {
        hasLoadingScreen = updateEngineerState.isLoading
    }

    ScaffoldCustomizedForEngineerScreens(
        snackBarHostState = snackBarHostState,
        hasLoadingScreen = hasLoadingScreen
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            RegistrationHeader(
                navigationController = navigationController,
                fixedRoute = "Settings"
            )

            EngineerTitleRegistration(
                title = "Modificando sua",
                highlightedTitle = "senha",
                description = "Atualize sua senha de acesso. Informe sua senha atual e escolha uma nova para proteger sua conta."
            )

            Column(
                modifier = Modifier
                    .padding(
                        bottom = 60.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(
                    space = 40.dp
                )
            ) {
                TextBoxRegistration(
                    value = stateCurrentPassword.password,
                    onValueChange = {
                        viewModelCurrentPassword.onEvent(
                            PasswordTextFieldFormEvent.PasswordChanged(
                                it
                            )
                        )
                        viewModelCurrentPassword.onEvent(PasswordTextFieldFormEvent.Submit)

                        tokenViewModel.saveToken("password", it)
                    },
                    isError = stateCurrentPassword.passwordError != null,
                    errorState = stateCurrentPassword.passwordError,
                    isPassword = true,
                    label = "Senha Atual",
                    placeholder = "Senha123#",
                    tipoTeclado = KeyboardType.Password,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                TextBoxRegistration(
                    value = stateNewPassword.newPassword,
                    onValueChange = {
                        viewModelNewPassword.onEvent(
                            PasswordTextFieldFormEvent.PasswordChanged(
                                it
                            )
                        )
                        viewModelNewPassword.onEvent(PasswordTextFieldFormEvent.Submit)

                        tokenViewModel.saveToken("password", it)
                    },
                    isError = stateNewPassword.newPasswordError != null,
                    errorState = stateNewPassword.newPasswordError,
                    isPassword = true,
                    label = "Nova Senha",
                    placeholder = "Senha123#",
                    tipoTeclado = KeyboardType.Password,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                TextBoxRegistration(
                    value = stateConfirmPassword.confirmPassword,
                    onValueChange = {
                        viewModelConfirmPassword.onEvent(
                            PasswordTextFieldFormEvent.PasswordChanged(
                                it
                            )
                        )
                        viewModelConfirmPassword.onEvent(PasswordTextFieldFormEvent.Submit)

                        tokenViewModel.saveToken("password", it)
                    },
                    isError = stateConfirmPassword.confirmPasswordError != null,
                    errorState = stateConfirmPassword.confirmPasswordError,
                    isPassword = true,
                    label = "Confirmar Senha",
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
                    viewModelCurrentPassword.onEvent(PasswordTextFieldFormEvent.Submit)
                    viewModelNewPassword.onEvent(PasswordTextFieldFormEvent.Submit)
                    viewModelConfirmPassword.onEvent(PasswordTextFieldFormEvent.Submit)

                    if (isValidationSuccessful) {
                        // Codigo aqui
                    }
                },
                buttonValue = "Agregar",
                backgroundColor = backgroundColor,
                contentColor = contentColor
            )
        }
    }
}