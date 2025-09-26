package com.banap.banap.app.presentation.analysis.ui.readmore.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.banap.banap.app.navigation.screens.Screen
import com.banap.banap.app.presentation.analysis.ui.readmore.components.ReadMoreContainer
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO

@Composable
fun ReadMore(
    navigationController: NavController,
    tokenViewModel: TokenViewModel
) {
    var etapa by remember {
        mutableStateOf("")
    }

    var titulo by remember {
        mutableStateOf("")
    }

    var texto by remember {
        mutableStateOf("")
    }

    var imagem by remember {
        mutableIntStateOf(0)
    }

    val pagerState = rememberPagerState(
        pageCount = {
            5
        }
    )

    when (pagerState.currentPage) {
        0 -> {
            etapa = "Proporção"
        }

        1 -> {
            etapa = "Adubo"
        }

        2 -> {
            etapa = "Medida"
        }

        3 -> {
            etapa = "Aplicação"
        }

        4 -> {
            etapa = "Manutenção"
        }
    }

    ReadMoreContainer {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .align(Alignment.TopCenter)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 15.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .size(40.dp),
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = VERDE_CLARO,
                            contentColor = BRANCO
                        ),
                        content = {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = (pagerState.currentPage + 1).toString(),
                                    style = Typography.labelSmall,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }
                    )

                    Text(
                        text = etapa,
                        style = Typography.headlineLarge,
                        color = VERDE_CLARO
                    )
                }

                Button(
                    onClick = {
                        navigationController.navigate(
                            Screen.AnalysisInformation.createRoute(
                                analysisId = tokenViewModel.getToken("analysisId") ?: "",
                                analysisName = tokenViewModel.getToken("analysisName") ?: "",
                                fieldName = tokenViewModel.getToken("fieldName") ?: ""
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BRANCO,
                        contentColor = VERDE_CLARO
                    ),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(
                        0.5.dp,
                        VERDE_CLARO.copy(
                            alpha = 0.2F
                        )
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Text(
                        text = "Pular",
                        style = Typography.bodyMedium,
                        fontSize = 14.sp
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .align(Alignment.Center)
            ) { page ->
                when (page) {
                    0 -> {
                        imagem = R.drawable.readmoreimage1
                        titulo = "Qual a proporção\n de adubo recomendado?"
                        texto = "A proporção de adubo é de N: 1,7, P: 1, e K: 3,3."
                    }

                    1 -> {
                        imagem = R.drawable.readmoreimage2
                        titulo = "Qual adubo?"
                        texto = "O adubo formulado 14-7-28."
                    }

                    2 -> {
                        imagem = R.drawable.readmoreimage3
                        titulo = "E a quantidade?"
                        texto = "Serão utilizados 3.248Kg/H para atingir a produção desejada."
                    }

                    3 -> {
                        imagem = R.drawable.readmoreimage4
                        titulo = "Qual é a proporção \nde aplicação por planta?"
                        texto = "Deverá ser aplicado 1,3 kg por planta."
                    }

                    4 -> {
                        imagem = R.drawable.readmoreimage5
                        titulo = "Qual é a forma\n correta de aplicar(por planta)?"
                        texto = "4 aplicações de 350g, com intervalo de 2 meses entre elas."
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 60.dp
                    )
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(
                            id = imagem
                        ),
                        contentDescription = "Imagem de uma pessoa mexendo no celular",
                        modifier = Modifier
                            .scale(0.9f)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            space = 10.dp
                        )
                    ) {
                        Text(
                            text = titulo,
                            textAlign = TextAlign.Center,
                            style = Typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = VERDE_ESCURO
                        )

                        Text(
                            text = texto,
                            textAlign = TextAlign.Center,
                            style = Typography.bodyLarge,
                            fontWeight = FontWeight.Normal,
                            color = VERDE_ESCURO
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 20.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) VERDE_ESCURO else CINZA_CLARO

                        Card(
                            modifier = Modifier
                                .size(15.dp),
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(
                                containerColor = color
                            ),
                            content = {}
                        )
                    }
                }
            }
        }
    }
}