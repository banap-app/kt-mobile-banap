package com.banap.banap.app.presentation.engineer.ui.registration

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
import androidx.compose.ui.unit.sp
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
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.components.TextBoxRegistration
import com.banap.banap.core.ui.components.TitleRegistration
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO

@Composable
fun NewEngineerFirstPage(
    navigationController: NavController,
    tokenViewModel: TokenViewModel
) {
    val context = LocalContext.current

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
        tokenViewModel.getToken("nameError")?.let {
            viewModelName.onEvent(
                NameTextFieldFormEvent.LoadName(
                    tokenViewModel.getToken("name") ?: ""
                )
            )
            viewModelName.onEvent(NameTextFieldFormEvent.SetError(it))
        } ?: run {
            tokenViewModel.getToken("name")?.let {
                viewModelName.onEvent(NameTextFieldFormEvent.NameChanged(it))
                viewModelName.onEvent(NameTextFieldFormEvent.Submit)
            }
        }
    }

    LaunchedEffect(true) {
        tokenViewModel.getToken("emailError")?.let {
            viewModelEmail.onEvent(
                EmailTextFieldFormEvent.LoadEmail(
                    tokenViewModel.getToken("email") ?: ""
                )
            )
            viewModelEmail.onEvent(EmailTextFieldFormEvent.SetError(it))
        } ?: run {
            tokenViewModel.getToken("email")?.let {
                viewModelEmail.onEvent(EmailTextFieldFormEvent.EmailChanged(it))
                viewModelEmail.onEvent(EmailTextFieldFormEvent.Submit)
            }
        }
    }

    LaunchedEffect(true) {
        tokenViewModel.getToken("passwordError")?.let {
            viewModelPassword.onEvent(
                PasswordTextFieldFormEvent.LoadPassword(
                    tokenViewModel.getToken(
                        "password"
                    ) ?: ""
                )
            )
            viewModelPassword.onEvent(PasswordTextFieldFormEvent.SetError(it))
        } ?: run {
            tokenViewModel.getToken("password")?.let {
                viewModelPassword.onEvent(PasswordTextFieldFormEvent.PasswordChanged(it))
                viewModelPassword.onEvent(PasswordTextFieldFormEvent.Submit)
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = BRANCO
    ) { innerPadding ->
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
                textoASerDestacado = "Engenheiro!",
                corEmDestaque = VERDE_ESCURO,
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

                        tokenViewModel.getToken("nameError")?.let {
                            tokenViewModel.clearToken("nameError")
                        }
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

                        tokenViewModel.getToken("emailError")?.let {
                            tokenViewModel.clearToken("emailError")
                        }
                        tokenViewModel.saveToken("email", it)
                    },
                    isError = stateEmail.emailError != null,
                    errorState = stateEmail.emailError,
                    label = "Email",
                    placeholder = "exemplo@gmail.com",
                    tipoTeclado = KeyboardType.Email,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                TextBoxRegistration(
                    value = statePassword.password,
                    onValueChange = {
                        viewModelPassword.onEvent(PasswordTextFieldFormEvent.PasswordChanged(it))
                        viewModelPassword.onEvent(PasswordTextFieldFormEvent.Submit)

                        tokenViewModel.getToken("passwordError")?.let {
                            tokenViewModel.clearToken("passwordError")
                        }
                        tokenViewModel.saveToken("password", it)
                    },
                    isError = statePassword.passwordError != null,
                    errorState = statePassword.passwordError,
                    isPassword = true,
                    label = "Senha",
                    placeholder = "12345678",
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

                    if (isValidationSuccessful) {
                        navigationController.navigate("NewEngineerSecondPage")
                    }
                },
                buttonValue = "Continuar",
                backgroundColor = backgroundColor,
                contentColor = contentColor
            )
        }
    }
}