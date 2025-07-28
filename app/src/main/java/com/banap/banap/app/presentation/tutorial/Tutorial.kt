package com.banap.banap.app.presentation.tutorial.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import kotlinx.coroutines.launch

@Composable
fun Tutorial(
    navigationController: NavController
) {
    var titulo by remember {
        mutableStateOf("")
    }

    var texto by remember {
        mutableStateOf("")
    }

    var imagem by remember {
        mutableIntStateOf(0)
    }

    var numeroPagina by remember {
        mutableIntStateOf(0)
    }

    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        pageCount = {
            4
        }
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = BRANCO
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(bottom = 30.dp, top = innerPadding.calculateTopPadding() + 15.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        navigationController.navigate("ReadyToStart")
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

            Box (
                modifier = Modifier
                    .fillMaxSize()
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .align(Alignment.TopCenter)
                ) { page ->
                    numeroPagina = page

                    when (page) {
                        0 -> {
                            imagem = R.drawable.tutorial_01
                            titulo = "Insira os dados manualmente"
                            texto = "Forneça manualmente os nutrientes do solo da cultura"
                        }

                        1 -> {
                            imagem = R.drawable.tutorial_02
                            titulo = "Nossa aplicação fará por você"
                            texto = "Ou utilize nosso produto para detectá-los automaticamente"
                        }

                        2 -> {
                            imagem = R.drawable.tutorial_03
                            titulo = "Seus dados serão análisados"
                            texto = "O sistema fará uma análise desses dados fornecidos"
                        }

                        3 -> {
                            imagem = R.drawable.tutorial_04
                            titulo = "Baseado nos dados fornecidos"
                            texto = "Recomendará métodos de cultivo e manejo da cultura"
                        }
                    }

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(
                                id = imagem
                            ),
                            contentDescription = "Imagem de uma pessoa mexendo no celular",
                            modifier = Modifier
                                .scale(0.9f)
                        )

                        Spacer(modifier = Modifier.height(60.dp))

                        Column (
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
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage - 1
                                )
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BRANCO,
                            contentColor = VERDE_CLARO,
                            disabledContainerColor = BRANCO,
                            disabledContentColor = CINZA_ESCURO
                        ),
                        enabled = if (pagerState.currentPage >= 1) true else false,
                        contentPadding = PaddingValues(min(0.dp, 0.dp))
                    ) {
                        Text(
                            text = "Voltar",
                            style = Typography.bodySmall,
                            fontWeight = if (pagerState.currentPage >= 1) FontWeight.Bold else FontWeight.Normal
                        )
                    }

                    Row (
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 10.dp
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(pagerState.pageCount + 1) { iteration ->
                            val color =
                                if (pagerState.currentPage == iteration) VERDE_ESCURO else CINZA_CLARO

                            Card(
                                modifier = Modifier
                                    .size(10.dp),
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(
                                    containerColor = color
                                )
                            ) {

                            }
                        }
                    }

                    Button(
                        onClick = {
                            if (pagerState.currentPage == 3) {
                                navigationController.navigate("ReadyToStart")
                            } else {
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        pagerState.currentPage + 1
                                    )
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = VERDE_CLARO,
                            contentColor = BRANCO
                        ),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(min(10.dp, 10.dp))
                    ) {
                        Text(
                            text = if (pagerState.currentPage == 3) "Começar" else "Avançar",
                            style = Typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}