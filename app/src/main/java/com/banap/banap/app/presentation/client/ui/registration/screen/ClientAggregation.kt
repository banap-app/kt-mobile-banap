package com.banap.banap.app.presentation.client.ui.registration.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.client.ui.registration.components.EngineerTitleRegistration
import com.banap.banap.app.presentation.home.ui.components.ScaffoldCustomizedForEngineerScreens
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.validation.email.event.EmailTextFieldFormEvent
import com.banap.banap.app.presentation.validation.email.utils.validationDataEmail
import com.banap.banap.app.presentation.validation.email.viewmodel.EmailTextFieldViewModel
import com.banap.banap.core.ui.components.ButtonRegistration
import com.banap.banap.core.ui.components.Modal
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.components.TextBoxRegistration
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import kotlinx.coroutines.delay

@Composable
fun ClientAggregation(
    navigationController: NavController,
    tokenViewModel: TokenViewModel
) {
    // Variaveis da Tela

    val context = LocalContext.current

    // Variaveis do Scaffold

    val snackBarHostState = remember { SnackbarHostState() }

    // Estados da tela

    var hasLoadingScreen: Boolean by remember {
        mutableStateOf(false)
    }

    var hasContentError: String by remember {
        mutableStateOf("")
    }

    // Variaveis do TextField de Email

    val viewModelEmail = viewModel<EmailTextFieldViewModel>()
    val stateEmail = viewModelEmail.state

    // Variaveis para validação dos campos

    val validationDataEmail = validationDataEmail(
        context = context,
        viewModelEmail = viewModelEmail,
        stateEmail = stateEmail
    )

    val isValidationSuccessful = validationDataEmail

    // Variaveis para controle das cores do botão de cadastro

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

    // Variavel do modal

    var isNotificationSent: Boolean by remember {
        mutableStateOf(false)
    }

    // Launched

    LaunchedEffect(hasLoadingScreen) {
        if (hasLoadingScreen) {
            delay(2_000)
            hasLoadingScreen = false
            isNotificationSent = true
        }
    }

    ScaffoldCustomizedForEngineerScreens(
        snackBarHostState = snackBarHostState,
        hasLoadingScreen = hasLoadingScreen
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            RegistrationHeader(
                navigationController = navigationController,
                fallbackRoute = "Home"
            )

            EngineerTitleRegistration(
                title = "Agregando um novo",
                highlightedTitle = "cliente",
                description = "Primeiramente precisamos identificar o cliente em questão. Para isso, informe o email cadastrado pelo cliente."
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = 40.dp
                    )
                ) {
                    TextBoxRegistration(
                        value = stateEmail.email,
                        onValueChange = {
                            viewModelEmail.onEvent(EmailTextFieldFormEvent.EmailChanged(it))
                            viewModelEmail.onEvent(EmailTextFieldFormEvent.Submit)
                        },
                        isError = stateEmail.emailError != null,
                        errorState = stateEmail.emailError,
                        label = "Email do Cliente",
                        placeholder = "exemplo@gmail.com",
                        tipoTeclado = KeyboardType.Email,
                        modifier = Modifier
                            .fillMaxWidth(),
                        lastOne = true
                    )
                }

                ButtonRegistration(
                    onClick = {
                        viewModelEmail.onEvent(EmailTextFieldFormEvent.Submit)

                        if (isValidationSuccessful) {
                            hasLoadingScreen = true
                        }
                    },
                    buttonValue = "Agregar",
                    backgroundColor = backgroundColor,
                    contentColor = contentColor
                )

                if (isNotificationSent) {
                    Modal(
                        onConfirm = {
                            navigationController.navigate("EngineerHome")
                        },
                        onDismiss = {
                            isNotificationSent = false
                        },
                        icon = ImageVector.vectorResource(id = R.drawable.baseline_check_24),
                        iconColor = VERDE_CLARO,
                        title = "Solicitação enviada\n com sucesso!",
                        description = "O cliente receberá uma notificação de confirmação em alguns instantes.",
                        onConfirmText = "Entendi",
                        onConfirmButtonBackgroundColor = VERDE_CLARO,
                        onConfirmButtonContentColor = BRANCO,
                        hasDismissButton = false,
                        space = 0.dp
                    )
                }
            }
        }
    }
}