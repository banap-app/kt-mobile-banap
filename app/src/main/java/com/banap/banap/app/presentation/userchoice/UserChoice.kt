package com.banap.banap.app.presentation.userchoice.ui

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.core.ui.components.ButtonRegistration
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.components.TitleRegistration
import com.banap.banap.app.presentation.userchoice.utils.setColorInTextUserChoice
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UserChoice(
    navigationController: NavController
) {

    var cardProducer by remember {
        mutableStateOf(false)
    }

    var cardEngineer by remember {
        mutableStateOf(false)
    }

    var isSuccessful by remember {
        mutableStateOf(false)
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
        cardProducer && !cardEngineer -> {
            VERDE_CLARO
        }

        cardEngineer && !cardProducer -> {
            VERDE_ESCURO
        }

        else -> {
            CINZA_CLARO
        }
    }

    contentColorButton = when {
        cardProducer || cardEngineer -> {
            BRANCO
        }

        else -> {
            CINZA_ESCURO
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = BRANCO
    ) {
        Column {
            RegistrationHeader(
                navigationController = navigationController,
                fallbackRoute = "ReadyToStart"
            )

            TitleRegistration(
                texto = "Antes de começar a utilizar o ",
                textoASerDestacado = "Banap...",
                corEmDestaque = VERDE_ESCURO,
                subTexto = "",
                tamanhoTextoDestacado = 28.sp,
                paginaUsuario = false,
                subtituloDestacado = "",
                subtitulo = "Precisamos saber para qual finalidade nosso sistema será usado."
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = setColorInTextUserChoice(
                        primeiroTexto = "Você é um ",
                        primeiroTextoASerDestacado = "produtor",
                        corPrimeiroTexto = VERDE_CLARO,
                        segundoTexto = ",\n ou ",
                        segundoTextoASerDestacado = "engenheiro?",
                        corSegundoTexto = VERDE_ESCURO
                    ),
                    style = Typography.labelMedium,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 20.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.Bottom
            ) {
                Box(
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Card(
                        onClick = {
                            cardProducer = !cardProducer
                            cardEngineer = false

                            isSuccessful = cardProducer
                        },
                        modifier = Modifier
                            .size(135.dp),
                        shape = RoundedCornerShape(30.dp),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = VERDE_CLARO
                        ),
                        elevation = CardDefaults.elevatedCardElevation(
                            defaultElevation = 4.dp
                        ),
                        content = {}
                    )

                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.produtor),
                        contentDescription = "Imagem representativa de um produtor",
                        modifier = Modifier
                            .padding(
                                bottom = 70.dp,
                                start = 10.dp
                            )
                            .scale(1.2F)
                    )
                }

                Text(
                    text = "OU",
                    style = Typography.bodyLarge,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .padding(bottom = 60.dp)
                )

                Box(
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Card(
                        onClick = {
                            cardEngineer = !cardEngineer
                            cardProducer = false

                            isSuccessful = cardEngineer
                        },
                        modifier = Modifier
                            .size(135.dp),
                        shape = RoundedCornerShape(30.dp),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = VERDE_ESCURO
                        ),
                        elevation = CardDefaults.elevatedCardElevation(
                            defaultElevation = 4.dp
                        ),
                        content = {}
                    )

                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.engenheiro),
                        contentDescription = "Imagem representativa de um engenheiro",
                        modifier = Modifier
                            .padding(
                                bottom = 70.dp,
                                end = 20.dp
                            )
                            .scale(1.2F)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1F))

            ButtonRegistration(
                onClick = {
                    if (isSuccessful) {
                        navigationController.navigate(
                            if (cardProducer) "NewProducer" else "NewEngineerFirstPage"
                        )
                    }
                },
                buttonValue = "Continuar",
                backgroundColor = backgroundColor,
                contentColor = contentColor
            )
        }
    }
}