package com.banap.banap.app.presentation.home.ui.producer.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.home.ui.components.ScaffoldCustomizedForEngineerScreens
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.validation.crea.event.CreaTextFieldFormEvent
import com.banap.banap.app.presentation.validation.crea.utils.validationDataCrea
import com.banap.banap.app.presentation.validation.crea.viewmodel.CreaTextFieldViewModel
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
import com.banap.banap.core.ui.components.TextBoxRegistration
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.domain.viewmodel.engineer.DeleteEngineerViewModel
import com.banap.banap.domain.viewmodel.engineer.UpdateEngineerViewModel
import com.banap.banap.domain.viewmodel.producer.DeleteProducerViewModel
import com.banap.banap.domain.viewmodel.producer.UpdateProducerViewModel

@Composable
fun UpdateUserInformation(
    navigationController: NavController,
    updateProducerViewModel: UpdateProducerViewModel = hiltViewModel(),
    deleteProducerViewModel: DeleteProducerViewModel = hiltViewModel(),
    updateEngineerViewModel: UpdateEngineerViewModel = hiltViewModel(),
    deleteEngineerViewModel: DeleteEngineerViewModel = hiltViewModel(),
    tokenViewModel: TokenViewModel,
    name: String,
    email: String,
    crea: String
) {
    // Variaveis do Scaffold

    val snackBarHostState = remember { SnackbarHostState() }

    // Variaveis de controle da tela

    var hasLoadingScreen: Boolean by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val updateProducerState by updateProducerViewModel.state
    val deleteProducerState by deleteProducerViewModel.state
    val updateEngineerState by updateEngineerViewModel.state
    val deleteEngineerState by deleteEngineerViewModel.state

    val viewModelName = viewModel<NameTextFieldViewModel>()
    val stateName = viewModelName.state

    val viewModelEmail = viewModel<EmailTextFieldViewModel>()
    val stateEmail = viewModelEmail.state

    val viewModelCrea = viewModel<CreaTextFieldViewModel>()
    val stateCrea = viewModelCrea.state

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

    val validationDataCrea = validationDataCrea(
        context = context,
        viewModelCrea = viewModelCrea,
        stateCrea = stateCrea
    )

    val isValidationSuccessful = when {
        crea.isNotEmpty() -> {
            validationDataName && validationDataEmail && validationDataCrea
        }

        else -> {
            validationDataName && validationDataEmail && validationDataPassword
        }
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

    LaunchedEffect(Unit) {
        if (name.isNotEmpty()) {
            tokenViewModel.saveToken("name", name)
        }
    }

    LaunchedEffect(Unit) {
        if (email.isNotEmpty()) {
            tokenViewModel.saveToken("email", email)
        }
    }

    LaunchedEffect(Unit) {
        if (crea.isNotEmpty()) {
            tokenViewModel.saveToken("crea", crea)
        }
    }

    LaunchedEffect(Unit) {
        tokenViewModel.getToken("name")?.let {
            viewModelName.onEvent(NameTextFieldFormEvent.NameChanged(it))
            viewModelName.onEvent(NameTextFieldFormEvent.Submit)
        }
    }

    LaunchedEffect(Unit) {
        tokenViewModel.getToken("email")?.let {
            viewModelEmail.onEvent(EmailTextFieldFormEvent.EmailChanged(it))
            viewModelEmail.onEvent(EmailTextFieldFormEvent.Submit)
        }
    }

    LaunchedEffect(Unit) {
        tokenViewModel.getToken("crea")?.let {
            viewModelCrea.onEvent(CreaTextFieldFormEvent.CreaChanged(it))
            viewModelCrea.onEvent(CreaTextFieldFormEvent.Submit)
        }
    }

    // Update Producer

    LaunchedEffect(updateProducerState.response) {
        updateProducerState.response?.let {
            if (it.success) {
                tokenViewModel.clearTokens(
                    listOf(
                        "name",
                        "email",
                        "crea",
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

    LaunchedEffect(updateEngineerState.response) {
        updateEngineerState.response?.let {
            if (it.statusCode == 204) {
                tokenViewModel.clearTokens(
                    listOf(
                        "name",
                        "email",
                        "crea",
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
                    error.name?.first()
                        ?.let { NameTextFieldFormEvent.SetError(it) }
                        ?.let { viewModelName.onEvent(it) }
                    error.email?.first()
                        ?.let { EmailTextFieldFormEvent.SetError(it) }
                        ?.let { viewModelEmail.onEvent(it) }
                    error.password?.first()
                        ?.let { PasswordTextFieldFormEvent.SetError(it) }
                        ?.let { viewModelPassword.onEvent(it) }
                    error.crea?.first()
                        ?.let { CreaTextFieldFormEvent.SetError(it) }
                        ?.let { viewModelCrea.onEvent(it) }
                }
            }  else {
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
    ) {
        Box(
            modifier = Modifier
                .padding(
                    top = 20.dp
                )
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .background(VERDE_CLARO)
                    .height(220.dp)
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        navigationController.navigate("EngineerHome")
                    },
                    modifier = Modifier
                        .padding(
                            top = 40.dp,
                            start = 20.dp
                        )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
                        contentDescription = "Icone de voltar",
                        tint = BRANCO,
                        modifier = Modifier
                            .scale(1.2F)
                    )
                }

                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.image_top_right_header),
                    contentDescription = "Imagem do formulário",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(
                            size = 130.dp
                        )
                )

                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.image_bottom_left_header),
                    contentDescription = "Imagem do formulário",
                    modifier = Modifier
                        .padding(
                            bottom = 10.dp
                        )
                        .align(Alignment.BottomStart)
                        .size(
                            size = 130.dp
                        )
                )
            }

            Card(
                modifier = Modifier
                    .padding(
                        top = 0.2 * screenHeight
                    )
                    .align(Alignment.BottomCenter)
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(
                    containerColor = BRANCO,
                    contentColor = VERDE_ESCURO
                ),
                shape = RoundedCornerShape(
                    topStart = 45.dp,
                    topEnd = 45.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            top = 100.dp,
                            bottom = 20.dp
                        )
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .padding(
                                start = 10.dp,
                                end = 10.dp
                            ),
                        verticalArrangement = Arrangement.spacedBy(
                            space = 40.dp
                        )
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

                        if (crea.isNotEmpty()) {
                            TextBoxRegistration(
                                value = stateCrea.crea,
                                onValueChange = {
                                    viewModelCrea.onEvent(CreaTextFieldFormEvent.CreaChanged(it))
                                    viewModelCrea.onEvent(CreaTextFieldFormEvent.Submit)

                                    tokenViewModel.saveToken("crea", it)
                                },
                                isError = stateCrea.creaError != null,
                                errorState = stateCrea.creaError,
                                label = "CREA",
                                placeholder = "123456-SP",
                                tipoTeclado = KeyboardType.Number,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                lastOne = true
                            )
                        }
                    }

                    ButtonRegistration(
                        onClick = {
                            viewModelName.onEvent(NameTextFieldFormEvent.Submit)
                            viewModelEmail.onEvent(EmailTextFieldFormEvent.Submit)
                            viewModelPassword.onEvent(PasswordTextFieldFormEvent.Submit)

                            if (crea.isNotEmpty()) {
                                viewModelCrea.onEvent(CreaTextFieldFormEvent.Submit)
                            }

                            if (isValidationSuccessful) {
                                if (crea.isNotEmpty()) {
                                    updateEngineerViewModel.updateEngineer(
                                        name = tokenViewModel.getToken("name") ?: stateName.name,
                                        email = tokenViewModel.getToken("email") ?: stateEmail.email,
                                        password = tokenViewModel.getToken("password") ?: statePassword.password,
                                        crea = tokenViewModel.getToken("crea") ?: stateCrea.crea
                                    )
                                } else {
                                    updateProducerViewModel.updateProducer(
                                        name = tokenViewModel.getToken("name") ?: stateName.name,
                                        email = tokenViewModel.getToken("email") ?: stateEmail.email,
                                        password = tokenViewModel.getToken("password") ?: statePassword.password
                                    )
                                }
                            }
                        },
                        buttonValue = "Atualizar",
                        backgroundColor = backgroundColor,
                        contentColor = contentColor
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(
                        top = 0.13 * screenHeight
                    )
                    .align(Alignment.TopCenter)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.user_error),
                    contentDescription = "Imagem do formulário",
                    modifier = Modifier
                        .size(
                            size = 120.dp
                        )
                )

                Card(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(40.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = VERDE_CLARO,
                        contentColor = BRANCO
                    ),
                    border = BorderStroke(
                        color = BRANCO,
                        width = 5.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.fieldiconedit),
                            contentDescription = "Icone de adicionar nova propriedade",
                            modifier = Modifier
                                .size(
                                    10.dp
                                )
                        )
                    }
                }
            }
        }
    }
}