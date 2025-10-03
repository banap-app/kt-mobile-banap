package com.banap.banap.app.presentation.field.ui.registration.components

import android.content.Context
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.validation.field.utils.validationDataFieldName
import com.banap.banap.app.presentation.validation.field.viewmodel.FieldNameTextFieldViewModel
import com.banap.banap.app.presentation.validation.model.RegistrationFormState
import com.banap.banap.app.presentation.validation.name.event.NameTextFieldFormEvent
import com.banap.banap.app.util.enumerator.FieldPage
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
fun FirstPage(
    navigationController: NavController,
    currentPage: MutableState<FieldPage>,
    viewModel: FieldNameTextFieldViewModel,
    context: Context,
    state: RegistrationFormState,
    isValidationSuccessful: MutableState<Boolean>,
    innerPadding: PaddingValues,
    tokenViewModel: TokenViewModel
) {
    isValidationSuccessful.value = validationDataFieldName(
        context = context,
        viewModelName = viewModel,
        stateName = state
    )

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
        isValidationSuccessful.value && state.nameError == null -> {
            VERDE_CLARO
        }

        else -> {
            CINZA_CLARO
        }
    }

    contentColorButton = when {
        isValidationSuccessful.value && state.nameError == null -> {
            BRANCO
        }

        else -> {
            CINZA_ESCURO
        }
    }

    Column (
        modifier = Modifier
            .padding(
                top = innerPadding.calculateTopPadding(),
                bottom = innerPadding.calculateBottomPadding()
            )
    ) {
        RegistrationHeader(
            navigationController = navigationController,
            fallbackRoute = "Home"
        )

        TitleRegistration(
            texto = "Cadastrando seu ",
            textoASerDestacado = "Talhão...",
            corEmDestaque = VERDE_ESCURO,
            subTexto = "",
            tamanhoTextoDestacado = 36.sp,
            paginaUsuario = false,
            subtituloDestacado = "",
            subtitulo = ""
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TextBoxRegistration(
                value = state.name,
                onValueChange = {
                    viewModel.onEvent(NameTextFieldFormEvent.NameChanged(it))
                    viewModel.onEvent(NameTextFieldFormEvent.Submit)

                    tokenViewModel.saveToken("fieldName", it)
                },
                isError = state.nameError != null,
                errorState = state.nameError,
                label = "Identificação",
                placeholder = "Talhão 01",
                tipoTeclado = KeyboardType.Text,
                modifier = Modifier
                    .fillMaxWidth(),
                lastOne = true
            )

            ButtonRegistration(
                onClick = {
                    viewModel.onEvent(NameTextFieldFormEvent.Submit)

                    if (isValidationSuccessful.value) {
                        currentPage.value = FieldPage.SECOND
                    }
                },
                buttonValue = "Continuar",
                backgroundColor = backgroundColor,
                contentColor = contentColor
            )
        }
    }
}