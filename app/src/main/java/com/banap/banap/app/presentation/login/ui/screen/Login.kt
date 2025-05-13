package com.banap.banap.app.presentation.login.ui.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.domain.viewmodel.login.LoginViewModel
import com.banap.banap.core.ui.components.Button
import com.banap.banap.app.presentation.login.ui.components.TextBox
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.core.ui.util.setColorInText
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.ShapeLogin
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.core.ui.theme.VERMELHO
import com.banap.banap.app.presentation.validation.email.event.EmailTextFieldFormEvent
import com.banap.banap.app.presentation.validation.email.viewmodel.EmailTextFieldViewModel
import com.banap.banap.app.presentation.validation.password.event.PasswordTextFieldFormEvent
import com.banap.banap.app.presentation.validation.password.viewmodel.PasswordTextFieldViewModel
import com.banap.banap.app.presentation.validation.email.utils.validationDataEmail
import com.banap.banap.app.presentation.validation.password.utils.validationDataPassword
import com.banap.banap.core.ui.util.ConnectivityAwareContent
import android.os.Build.VERSION_CODES
import android.provider.Settings.Panel.ACTION_INTERNET_CONNECTIVITY
import android.provider.Settings.ACTION_WIFI_SETTINGS
import android.util.Log
import com.banap.banap.core.ui.components.LoadingScreen
import com.banap.banap.domain.viewmodel.token.TokenVerificationViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Login(
    navigationController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    tokenViewModel: TokenViewModel,
    tokenVerificationViewModel: TokenVerificationViewModel
) {
    val loginState = loginViewModel.state.value
    val tokenVerificationState = tokenVerificationViewModel.state.value

    val context = LocalContext.current

    val snackBarHostState = remember { SnackbarHostState() }

    var isApplicationOnline: Boolean? by remember {
        mutableStateOf(null)
    }

    var isCredentialsCorrect: Boolean? by remember {
        mutableStateOf(null)
    }

    var isLoading: Boolean by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(context) {
        Log.d("TOKEN", tokenViewModel.getToken("token").toString())
        Log.d("VERIFIED_TOKEN", tokenViewModel.getToken("verifiedToken").toString())
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

    LaunchedEffect(loginState.response) {
        loginState.response?.token?.let { token ->
            tokenViewModel.saveToken("token", token)

            if (tokenViewModel.getToken("token") != null) {
                navigationController.navigate("home")
            }
        }
    }

    LaunchedEffect(loginState.error) {
        if (loginState.error.isNotEmpty()) {
            isLoading = false

            var message: String = ""
            var showSnackBar: Boolean = false

            when {
                loginState.error.contains("422") -> {
                    isCredentialsCorrect = false
                }

                else -> {
                    showSnackBar = true
                    message = "Não foi possível se conectar ao servidor"
                }
            }

            Log.d("Error", loginState.error)

            if (showSnackBar) {
                snackBarHostState.showSnackbar(
                    message = message,
                    actionLabel = "Entendi",
                    duration = SnackbarDuration.Indefinite
                )
            }
        }
    }

    LaunchedEffect(tokenVerificationState.error) {
        if (tokenVerificationState.error.isNotEmpty()) {
            Log.d("Error token verification", tokenVerificationState.error)

            val message: String = when {
                tokenVerificationState.error.contains("422") -> {
                    "Sessão expirada, logue novamente"
                }

                else -> {
                    "Ocorreu um erro, logue novamente"
                }
            }

            val autoDismissJob = launch {
                delay(5_000L)
                tokenVerificationViewModel.clearError()
                snackBarHostState.currentSnackbarData?.dismiss()
            }

            snackBarHostState.showSnackbar(
                message = message,
                actionLabel = "Entendi",
                duration = SnackbarDuration.Indefinite
            )

            autoDismissJob.cancel()

            tokenVerificationViewModel.clearError()
        }
    }

    val viewModelEmail = viewModel<EmailTextFieldViewModel>()
    val stateEmail = viewModelEmail.state

    val viewModelPassword = viewModel<PasswordTextFieldViewModel>()
    val statePassword = viewModelPassword.state

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

    val isValidationSuccessful = validationDataEmail && validationDataPassword

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
                    containerColor = VERDE_CLARO,
                    contentColor = BRANCO,
                    actionColor = BRANCO
                )
            }
        }
    ) {
        if (!isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.desenho_de_cima),
                    contentDescription = "Vetor de linhas",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .scale(1.2F)
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.banap_vector),
                            contentDescription = "Logo do Banap",
                            modifier = Modifier
                                .scale(0.8F)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Entre com sua\n conta!",
                            textAlign = TextAlign.Center,
                            style = Typography.titleSmall,
                            color = VERDE_ESCURO
                        )
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    TextBox(
                        value = stateEmail.email,
                        onValueChange = {
                            viewModelEmail.onEvent(EmailTextFieldFormEvent.EmailChanged(it))
                            viewModelEmail.onEvent(EmailTextFieldFormEvent.Submit)
                        },
                        modifier = Modifier
                            .onFocusChanged {
                                if (it.isFocused) {
                                    isCredentialsCorrect = null
                                }
                            }
                            .fillMaxWidth(),
                        maxLines = 1,
                        keyboardType = KeyboardType.Email,
                        icon = R.drawable.email,
                        iconColor = PRETO,
                        placeholder = "email@gmail.com",
                        passwordTextBox = false,
                        label = "Email",
                        labelTextStyle = Typography.labelSmall,
                        labelColor = PRETO,
                        isError = stateEmail.emailError != null || isCredentialsCorrect == false
                    )

                    if (stateEmail.emailError != null || isCredentialsCorrect == false) {
                        Text(
                            text = stateEmail.emailError ?: "O email está incorreto",
                            color = VERMELHO,
                            style = Typography.displaySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    TextBox(
                        value = statePassword.password,
                        onValueChange = {
                            viewModelPassword.onEvent(PasswordTextFieldFormEvent.PasswordChanged(it))
                            viewModelPassword.onEvent(PasswordTextFieldFormEvent.Submit)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        maxLines = 1,
                        keyboardType = KeyboardType.Password,
                        icon = R.drawable.lock,
                        iconColor = PRETO,
                        placeholder = "Senha123",
                        passwordTextBox = true,
                        label = "Senha",
                        labelTextStyle = Typography.labelSmall,
                        labelColor = PRETO,
                        isError = statePassword.passwordError != null || isCredentialsCorrect == false,
                        lastOne = true
                    )

                    if (statePassword.passwordError != null || isCredentialsCorrect == false) {
                        Text(
                            text = statePassword.passwordError ?: "A senha está incorreta",
                            color = VERMELHO,
                            style = Typography.displaySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Esqueceu sua senha?",
                        textAlign = TextAlign.End,
                        style = Typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = VERDE_ESCURO,
                        modifier = Modifier
                            .fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Button(
                        texto = "Entrar",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        hasIcon = false,
                        shape = ShapeLogin.small,
                        onClick = {
                            viewModelEmail.onEvent(EmailTextFieldFormEvent.Submit)
                            viewModelPassword.onEvent(PasswordTextFieldFormEvent.Submit)

                            if (isValidationSuccessful && isApplicationOnline == true) {
                                loginViewModel.authenticateUser(
                                    stateEmail.email,
                                    statePassword.password
                                )

                                isLoading = true
                            } else {
                                isCredentialsCorrect = false
                            }
                        },
                        backgroundColor = VERDE_CLARO,
                        contentColor = BRANCO,
                        defaultElevetion = 3.dp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = setColorInText(
                            texto = "Não possui uma conta? ",
                            textoASerDestacado = "Crie uma.",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            corEmDestaque = VERDE_ESCURO,
                            ordemInversa = false,
                            useLink = true,
                            navigationController = navigationController
                        ),
                        textAlign = TextAlign.Center,
                        style = Typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.desenho_de_baixo),
                    contentDescription = "Vetor de linhas",
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .scale(1.2F)
                )
            }
        } else {
            LoadingScreen()
        }
    }
}