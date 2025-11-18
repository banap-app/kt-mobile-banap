package com.banap.banap.app.presentation.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.client.ui.listing.components.ListingTitle
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.validation.email.event.EmailTextFieldFormEvent
import com.banap.banap.app.presentation.validation.name.event.NameTextFieldFormEvent
import com.banap.banap.app.presentation.validation.name.viewmodel.NameTextFieldViewModel
import com.banap.banap.core.ui.components.Modal
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.components.TextBoxRegistration
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_INTERMEDIARIO
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.core.ui.theme.VERMELHO
import com.banap.banap.core.ui.util.setColorInText
import com.banap.banap.domain.viewmodel.engineer.DeleteEngineerViewModel
import com.banap.banap.domain.viewmodel.producer.DeleteProducerViewModel

@Composable
fun Settings(
    navigationController: NavController,
    deleteEngineerViewModel: DeleteEngineerViewModel = hiltViewModel(),
    deleteProducerViewModel: DeleteProducerViewModel = hiltViewModel(),
    tokenViewModel: TokenViewModel
) {
    // Variaveis do Scaffold

    val snackBarHostState = remember { SnackbarHostState() }

    // Estados da tela

    var hasLoadingScreen: Boolean by remember {
        mutableStateOf(false)
    }

    var hasContentError: String by remember {
        mutableStateOf("")
    }

    // Variaveis do TextField de Nome

    val viewModelName = viewModel<NameTextFieldViewModel>()
    var stateName = viewModelName.state

    // Limpando os caches

    LaunchedEffect(true) {
        tokenViewModel.clearTokens(
            listOf(
                "name",
                "email"
            )
        )
    }

    // Modal

    var isLogoutMenuVisible: Boolean by remember {
        mutableStateOf(false)
    }

    var isDeleteMenuVisible: Boolean by remember {
        mutableStateOf(false)
    }

    var hasErrorOnGetData: Boolean by remember {
        mutableStateOf(false)
    }

    // Deletando o Produtor

    val deleteProducerState by deleteProducerViewModel.state

    LaunchedEffect(deleteProducerState.response) {
        deleteProducerState.response?.let {
            if (it.success) {
                tokenViewModel.clearAll()
                navigationController.navigate("Login")
            }
        }
    }

    LaunchedEffect(deleteProducerState.error) {
        if (deleteProducerState.error.isNotEmpty()) {
            snackBarHostState.showSnackbar(
                message = deleteProducerState.error,
                actionLabel = "Entendi",
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    LaunchedEffect(deleteProducerState.isLoading) {
        hasLoadingScreen = deleteProducerState.isLoading
    }

    // Deletando o Engenheiro

    val deleteEngineerState by deleteEngineerViewModel.state

    LaunchedEffect(deleteEngineerState.response) {
        deleteEngineerState.response?.let {
            if (it.statusCode == 204) {
                tokenViewModel.clearAll()
                navigationController.navigate("Login")
            }
        }
    }

    LaunchedEffect(deleteEngineerState.errors) {
        deleteEngineerState.errors?.let { errorResponse ->
            snackBarHostState.showSnackbar(
                message = errorResponse.message,
                actionLabel = "Entendi",
                duration = SnackbarDuration.Indefinite
            )
        }
    }

    LaunchedEffect(deleteEngineerState.isLoading) {
        hasLoadingScreen = deleteEngineerState.isLoading
    }

    // Mostrando mensagem para o usuario

    LaunchedEffect(hasErrorOnGetData) {
        if (hasErrorOnGetData) {
            snackBarHostState.showSnackbar(
                message = "Ocorreu um erro. Volte para a Tela Inicial.",
                actionLabel = "Entendi",
                duration = SnackbarDuration.Indefinite
            )
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
                    bottom = innerPadding.calculateBottomPadding() + 30.dp
                )
        ) {
            RegistrationHeader(
                navigationController = navigationController,
                fixedRoute = tokenViewModel.getToken("engineerId")?.let { "EngineerHome" }
                    ?: run { "Home" }
            )

            ListingTitle(
                icon = ImageVector.vectorResource(id = R.drawable.baseline_settings_24),
                space = 15.dp,
                title = "Configurações"
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 30.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(
                    space = 60.dp
                )
            ) {
                Column {
                    Text(
                        text = "Geral",
                        style = Typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = VERDE_ESCURO,
                        modifier = Modifier
                            .padding(
                                bottom = 20.dp
                            )
                    )

                    SettingsCard(
                        topRounded = 15.dp,
                        title = "Alterar seus dados",
                        icon = Icons.Filled.Person
                    ) {
                        if (!tokenViewModel.getToken("engineerId")
                                .isNullOrEmpty() || !tokenViewModel.getToken("producerId")
                                .isNullOrEmpty()
                        ) {
                            navigationController.navigate("UpdateUserNameAndEmail")
                        } else {
                            hasErrorOnGetData = true
                        }
                    }

                    SettingsCard(
                        bottomRounded = 15.dp,
                        title = "Modificar senha",
                        icon = Icons.Filled.Build
                    ) {
                        if (!tokenViewModel.getToken("engineerId")
                                .isNullOrEmpty() || !tokenViewModel.getToken("producerId")
                                .isNullOrEmpty()
                        ) {
                            navigationController.navigate("UpdatePassword")
                        } else {
                            hasErrorOnGetData = true
                        }
                    }
                }

                Column {
                    Text(
                        text = "Conta",
                        style = Typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = VERDE_ESCURO,
                        modifier = Modifier
                            .padding(
                                bottom = 20.dp
                            )
                    )

                    SettingsCard(
                        topRounded = 15.dp,
                        title = "Sair",
                        icon = ImageVector.vectorResource(id = R.drawable.baseline_logout_24)
                    ) {
                        isLogoutMenuVisible = true
                    }

                    if (isLogoutMenuVisible) {
                        Modal(
                            onConfirm = {
                                tokenViewModel.clearAll()
                                navigationController.navigate("Login")
                            },
                            onDismiss = {
                                isLogoutMenuVisible = false
                            },
                            icon = ImageVector.vectorResource(id = R.drawable.baseline_logout_24),
                            iconColor = VERMELHO,
                            title = "Tem certeza que\n deseja sair?",
                            description = "",
                            onConfirmText = "Sair",
                            onConfirmButtonBackgroundColor = VERMELHO,
                            onConfirmButtonContentColor = BRANCO,
                            onDismissText = "Cancelar",
                            onDismissButtonBackgroundColor = CINZA_INTERMEDIARIO,
                            onDismissButtonContentColor = PRETO,
                            space = 40.dp
                        )
                    }

                    SettingsCard(
                        bottomRounded = 15.dp,
                        contentColor = VERMELHO,
                        title = "Deletar conta",
                        icon = ImageVector.vectorResource(id = R.drawable.baseline_person_remove_alt_1_24)
                    ) {
                        if (!tokenViewModel.getToken("engineerId")
                                .isNullOrEmpty() || !tokenViewModel.getToken("producerId")
                                .isNullOrEmpty()
                        ) {
                            isDeleteMenuVisible = true
                        } else {
                            hasErrorOnGetData = true
                        }
                    }

                    if (isDeleteMenuVisible) {
                        Modal(
                            onConfirm = {
                                viewModelName.onEvent(NameTextFieldFormEvent.Submit)

                                if ((stateName.name == "excluir conta")) {
                                    tokenViewModel.getToken("engineerId")?.let {
                                        deleteEngineerViewModel.deleteEngineer()
                                    } ?: run {
                                        deleteProducerViewModel.deleteProducer(
                                            tokenViewModel.getToken("producerId") ?: ""
                                        )
                                    }
                                }
                            },
                            onDismiss = {
                                isDeleteMenuVisible = false
                            },
                            icon = ImageVector.vectorResource(id = R.drawable.baseline_logout_24),
                            iconColor = VERMELHO,
                            title = "Tem certeza que\n deseja apagar a conta?",
                            description = "Apagando sua conta, todas as suas informações tambem serão deletadas! Digite o texto solicitado para confirmar a exclusão.",
                            onConfirmText = "Deletar",
                            onConfirmTextFontWeight = if (stateName.name != "excluir conta") FontWeight.Normal else FontWeight.Black,
                            onConfirmButtonBackgroundColor = if (stateName.name != "excluir conta") CINZA_INTERMEDIARIO else VERMELHO,
                            onConfirmButtonContentColor = if (stateName.name != "excluir conta") PRETO else BRANCO,
                            onDismissText = "Cancelar",
                            onDismissButtonBackgroundColor = CINZA_INTERMEDIARIO,
                            onDismissButtonContentColor = PRETO,
                            space = 0.dp
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(
                                        start = 20.dp,
                                        end = 20.dp,
                                        bottom = 20.dp
                                    )
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = setColorInText(
                                        texto = "Digite",
                                        textoASerDestacado = " excluir conta:",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 14.sp,
                                        corEmDestaque = PRETO,
                                        ordemInversa = false
                                    ),
                                    style = Typography.bodySmall,
                                    fontWeight = FontWeight.Normal
                                )

                                TextField(
                                    value = stateName.name,
                                    onValueChange = {
                                        viewModelName.onEvent(NameTextFieldFormEvent.NameChanged(it))
                                        viewModelName.onEvent(NameTextFieldFormEvent.Submit)
                                    },
                                    modifier = Modifier,
                                    maxLines = 1,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = VERDE_ESCURO,
                                        cursorColor = VERDE_ESCURO,
                                        focusedContainerColor = BRANCO,
                                        unfocusedContainerColor = BRANCO,
                                        unfocusedTextColor = VERDE_ESCURO,
                                        focusedTextColor = VERDE_ESCURO,
                                        unfocusedPlaceholderColor = VERDE_ESCURO,
                                        focusedPlaceholderColor = VERDE_ESCURO,
                                        errorTextColor = VERMELHO,
                                        errorPlaceholderColor = VERMELHO,
                                        errorCursorColor = VERMELHO
                                    ),
                                    shape = RoundedCornerShape(0.dp),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Done
                                    ),
                                    textStyle = Typography.bodySmall,
                                    isError = stateName.nameError != null
                                )

                                if (!stateName.nameError.isNullOrEmpty()) {
                                    Text(
                                        text = stateName.nameError ?: "Ocorreu um erro...",
                                        color = VERMELHO,
                                        style = Typography.displaySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}