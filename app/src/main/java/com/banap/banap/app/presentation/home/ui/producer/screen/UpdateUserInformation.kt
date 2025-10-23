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
import com.banap.banap.core.ui.components.TextBoxRegistration
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.domain.viewmodel.producer.DeleteProducerViewModel
import com.banap.banap.domain.viewmodel.producer.UpdateProducerViewModel

@Composable
fun UpdateUserInformation(
    navigationController: NavController,
    updateProducerViewModel: UpdateProducerViewModel = hiltViewModel(),
    deleteProducerViewModel: DeleteProducerViewModel = hiltViewModel(),
    tokenViewModel: TokenViewModel,
    name: String,
    email: String,
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val updateProducerState by updateProducerViewModel.state
    val deleteProducerState by deleteProducerViewModel.state

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

    LaunchedEffect(Unit) {
        if (!name.contains("name")) {
            tokenViewModel.saveToken("nameProducer", name)
        }
    }

    LaunchedEffect(Unit) {
        if (!email.contains("email")) {
            tokenViewModel.saveToken("emailProducer", email)
        }
    }

    LaunchedEffect(Unit) {
        tokenViewModel.getToken("nameProducer")?.let {
            viewModelName.onEvent(NameTextFieldFormEvent.NameChanged(it))
            viewModelName.onEvent(NameTextFieldFormEvent.Submit)
        }
    }

    LaunchedEffect(Unit) {
        tokenViewModel.getToken("emailProducer")?.let {
            viewModelEmail.onEvent(EmailTextFieldFormEvent.EmailChanged(it))
            viewModelEmail.onEvent(EmailTextFieldFormEvent.Submit)
        }
    }

    LaunchedEffect(updateProducerState.response) {
        updateProducerState.response?.let {
            if (it.success) {
                navigationController.navigate("EngineerHome")
            }
        }
    }

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

                            tokenViewModel.saveToken("nameProducer", it)
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

                            tokenViewModel.saveToken("emailProducer", it)
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

                            tokenViewModel.saveToken("passwordProducer", it)
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

                ButtonRegistration(
                    onClick = {
                        viewModelName.onEvent(NameTextFieldFormEvent.Submit)
                        viewModelEmail.onEvent(EmailTextFieldFormEvent.Submit)
                        viewModelPassword.onEvent(PasswordTextFieldFormEvent.Submit)

                        if (isValidationSuccessful) {
                            updateProducerViewModel.updateProducer(
                                name = stateName.name,
                                email = stateEmail.email,
                                password = statePassword.password
                            )
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