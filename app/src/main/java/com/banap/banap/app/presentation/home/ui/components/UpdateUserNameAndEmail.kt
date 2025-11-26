package com.banap.banap.app.presentation.home.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.client.ui.listing.components.HandlingAllStates
import com.banap.banap.app.presentation.client.ui.registration.components.EngineerTitleRegistration
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.skeleton.ui.settings.components.SettingsSkeleton
import com.banap.banap.app.presentation.validation.email.event.EmailTextFieldFormEvent
import com.banap.banap.app.presentation.validation.email.utils.validationDataEmail
import com.banap.banap.app.presentation.validation.email.viewmodel.EmailTextFieldViewModel
import com.banap.banap.app.presentation.validation.name.event.NameTextFieldFormEvent
import com.banap.banap.app.presentation.validation.name.utils.validationDataName
import com.banap.banap.app.presentation.validation.name.viewmodel.NameTextFieldViewModel
import com.banap.banap.core.ui.components.ButtonRegistration
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.components.TextBoxRegistration
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.util.shimmerEffect
import com.banap.banap.domain.viewmodel.engineer.GetEngineerByIdViewModel
import com.banap.banap.domain.viewmodel.engineer.UpdateEngineerViewModel
import com.banap.banap.domain.viewmodel.producer.GetProducerByIdViewModel
import com.banap.banap.domain.viewmodel.producer.UpdateProducerViewModel

@Composable
fun UpdateUserNameAndEmail(
    navigationController: NavController,
    getProducerByIdViewModel: GetProducerByIdViewModel,
    updateProducerViewModel: UpdateProducerViewModel = hiltViewModel(),
    getEngineerByIdViewModel: GetEngineerByIdViewModel,
    updateEngineerViewModel: UpdateEngineerViewModel = hiltViewModel(),
    tokenViewModel: TokenViewModel
) {
    // Variaveis do Scaffold

    val snackBarHostState = remember { SnackbarHostState() }

    // Variaveis de controle da tela

    var hasLoadingScreen: Boolean by remember {
        mutableStateOf(false)
    }

    var isUserDataLoading: Boolean by remember {
        mutableStateOf(true)
    }

    var errorInUserData: String by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val updateProducerState by updateProducerViewModel.state
    val updateEngineerState by updateEngineerViewModel.state

    val viewModelName = viewModel<NameTextFieldViewModel>()
    val stateName = viewModelName.state

    val viewModelEmail = viewModel<EmailTextFieldViewModel>()
    val stateEmail = viewModelEmail.state

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

    val isValidationSuccessful = validationDataName && validationDataEmail

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

    // Recuperar os dados quando o usuario sai da tela

    LaunchedEffect(Unit) {
        tokenViewModel.getToken("username")?.let {
            viewModelName.onEvent(NameTextFieldFormEvent.NameChanged(it))
            viewModelName.onEvent(NameTextFieldFormEvent.Submit)
        }
    }

    LaunchedEffect(Unit) {
        tokenViewModel.getToken("useremail")?.let {
            viewModelEmail.onEvent(EmailTextFieldFormEvent.EmailChanged(it))
            viewModelEmail.onEvent(EmailTextFieldFormEvent.Submit)
        }
    }

    // Pegar os dados do engenheiro ou do produtor

    val getEngineerByIdState = getEngineerByIdViewModel.state.value

    LaunchedEffect(Unit) {
        if (
            tokenViewModel.getToken("useremail")
                .isNullOrEmpty()
            &&
            tokenViewModel.getToken("username")
                .isNullOrEmpty()
        ) {
            tokenViewModel.getToken("engineerId")?.let {
                getEngineerByIdViewModel.getEngineerById()
            } ?: run {
                getProducerByIdViewModel.getProducerById()
            }
        }
    }

    // GetEngineerById

    LaunchedEffect(getEngineerByIdState.response) {
        getEngineerByIdState.response?.let {
            if (it.statusCode == 200) {
                it.data?.name?.let { name ->
                    viewModelName.onEvent(NameTextFieldFormEvent.NameChanged(name))
                    viewModelName.onEvent(NameTextFieldFormEvent.Submit)

                    tokenViewModel.saveToken("username", name)
                }

                it.data?.email?.let { email ->
                    viewModelEmail.onEvent(EmailTextFieldFormEvent.EmailChanged(email))
                    viewModelEmail.onEvent(EmailTextFieldFormEvent.Submit)

                    tokenViewModel.saveToken("useremail", email)
                }
            }
        }
    }

    LaunchedEffect(getEngineerByIdState.errors) {
        getEngineerByIdState.errors?.let { errorResponse ->
            errorInUserData = errorResponse.message

            snackBarHostState.showSnackbar(
                message = errorResponse.message,
                actionLabel = "Entendi",
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    LaunchedEffect(getEngineerByIdState.isLoading) {
        isUserDataLoading = getEngineerByIdState.isLoading
    }

    // GetProducerById

    val getProducerByIdState = getProducerByIdViewModel.state.value

    LaunchedEffect(getProducerByIdState.response) {
        getProducerByIdState.response?.let {
            if (it.name.isNotEmpty()) {
                viewModelName.onEvent(NameTextFieldFormEvent.NameChanged(it.name))
                viewModelName.onEvent(NameTextFieldFormEvent.Submit)

                tokenViewModel.saveToken("username", it.name)
            }

            if (it.email.isNotEmpty()) {
                viewModelEmail.onEvent(EmailTextFieldFormEvent.EmailChanged(it.email))
                viewModelEmail.onEvent(EmailTextFieldFormEvent.Submit)

                tokenViewModel.saveToken("useremail", it.email)
            }
        }
    }

    LaunchedEffect(getProducerByIdState.error) {
        if (getProducerByIdState.error.isNotEmpty()) {
            errorInUserData = getProducerByIdState.error

            snackBarHostState.showSnackbar(
                message = getProducerByIdState.error,
                actionLabel = "Entendi",
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    LaunchedEffect(getProducerByIdState.isLoading) {
        isUserDataLoading = getEngineerByIdState.isLoading
    }

    // Update Producer

    LaunchedEffect(updateProducerState.response) {
        updateProducerState.response?.let {
            if (it.success) {
                tokenViewModel.clearTokens(
                    listOf(
                        "username",
                        "useremail"
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
                        "username",
                        "useremail"
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
                title = "Alterando seus",
                highlightedTitle = "dados",
                description = "Atualize suas informações pessoais. As alterações serão aplicadas imediatamente após a confirmação."
            )

            when {
                isUserDataLoading -> {
                    SettingsSkeleton()
                }

                errorInUserData.isNotEmpty() -> {
                    HandlingAllStates(
                        modifier = Modifier
                            .fillMaxHeight(0.7f)
                            .fillMaxWidth(),
                        text = "Ocorreu um erro \nao carregar seus dados...",
                        buttonText = "Tentar Novamente",
                        icon = ImageVector.vectorResource(id = R.drawable.homeiconretry),
                        onClick = {}
                    )
                }

                else -> {
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
                                value = stateName.name,
                                onValueChange = {
                                    viewModelName.onEvent(NameTextFieldFormEvent.NameChanged(it))
                                    viewModelName.onEvent(NameTextFieldFormEvent.Submit)

                                    tokenViewModel.saveToken("username", it)
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

                                    tokenViewModel.saveToken("useremail", it)
                                },
                                isError = stateEmail.emailError != null,
                                errorState = stateEmail.emailError,
                                label = "Email",
                                placeholder = "exemplo@gmail.com",
                                tipoTeclado = KeyboardType.Email,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }

                        ButtonRegistration(
                            onClick = {
                                viewModelName.onEvent(NameTextFieldFormEvent.Submit)
                                viewModelEmail.onEvent(EmailTextFieldFormEvent.Submit)

                                if (isValidationSuccessful) {
                                    tokenViewModel.getToken("engineerId")?.let {
                                        updateEngineerViewModel.updateEngineer(
                                            name = tokenViewModel.getToken("username")
                                                ?: stateName.name,
                                            email = tokenViewModel.getToken("useremail")
                                                ?: stateEmail.email
                                        )
                                    } ?: run {
                                        updateProducerViewModel.updateProducer(
                                            name = tokenViewModel.getToken("username")
                                                ?: stateName.name,
                                            email = tokenViewModel.getToken("useremail")
                                                ?: stateEmail.email
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
            }
        }
    }
}