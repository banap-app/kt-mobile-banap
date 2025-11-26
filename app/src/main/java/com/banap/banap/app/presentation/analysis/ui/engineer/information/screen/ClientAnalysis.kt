package com.banap.banap.app.presentation.analysis.ui.engineer.information.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.analysis.ui.producer.information.components.AnalysisCard
import com.banap.banap.app.presentation.analysis.ui.producer.information.components.CreateDetails
import com.banap.banap.app.presentation.analysis.ui.producer.information.components.Description
import com.banap.banap.app.presentation.client.ui.listing.components.HandlingAllStates
import com.banap.banap.app.presentation.client.ui.listing.components.ListingTitle
import com.banap.banap.app.presentation.home.ui.components.ScaffoldCustomizedForEngineerScreens
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.skeleton.ui.clients.screen.ClientAnalysisInformationSkeleton
import com.banap.banap.core.ui.components.Button
import com.banap.banap.core.ui.components.Information
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO

@Composable
fun ClientAnalysis(
    navigationController: NavController,
    tokenViewModel: TokenViewModel
) {
    // Variaveis do Scaffold

    val snackBarHostState = remember { SnackbarHostState() }

    // Estados da tela

    var isContentLoading: Boolean by remember {
        mutableStateOf(false)
    }

    var hasLoadingScreen: Boolean by remember {
        mutableStateOf(false)
    }

    var hasContentError: String by remember {
        mutableStateOf("")
    }

    // Estado que controla quais conteudos irao aparecer ou nao

    var isLimingCalculation: Float? by remember {
        mutableStateOf(null)
    }

    ScaffoldCustomizedForEngineerScreens(
        snackBarHostState = snackBarHostState,
        hasLoadingScreen = hasLoadingScreen
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding()
                )
        ) {
            item {
                RegistrationHeader(
                    navigationController = navigationController,
                    fixedRoute = "ClientField"
                )
            }

            when {
                isContentLoading -> {
                    item {
                        ClientAnalysisInformationSkeleton()
                    }
                }

                hasContentError.isNotEmpty() -> {
                    item {
                        HandlingAllStates(
                            modifier = Modifier
                                .fillParentMaxHeight(0.7f),
                            text = "Ocorreu um erro ao carregar\na análise...",
                            buttonText = "Tentar Novamente",
                            icon = ImageVector.vectorResource(id = R.drawable.homeiconretry),
                            onClick = {}
                        )
                    }
                }

                else -> {
                    item {
                        ListingTitle(
                            icon = ImageVector.vectorResource(id = R.drawable.analysisicontitle),
                            space = 15.dp,
                            title = "Análise 01"
                        )
                    }

                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(
                                space = 60.dp
                            )
                        ) {
                            Information(
                                space = 20.dp,
                                icon = R.drawable.round_signal_cellular_alt_24,
                                title = "Dados da análise"
                            ) {
                                Description(
                                    text = "Temos dois tipos de cálculos feitos acerca dos dados disponibilizados. O de calagem, e recomendação de adubação de solo."
                                )
                            }

                            isLimingCalculation
                                ?.let {
                                    Information(
                                        space = 20.dp,
                                        icon = R.drawable.fieldicontroubleshoot,
                                        title = "Cálculo de Calagem"
                                    ) {
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(
                                                space = 40.dp
                                            )
                                        ) {
                                            Description(
                                                text = "Esses são os dados que foram disponibilizados, por você, no momento em que a análise foi feita:"
                                            )

                                            Column(
                                                verticalArrangement = Arrangement.spacedBy(
                                                    space = 20.dp
                                                )
                                            ) {
                                                CreateDetails(
                                                    username = "Victor",
                                                    createdAt = "10 Maio 2024 ás 17:54"
                                                )

                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                ) {
                                                    AnalysisCard(
                                                        modifier = Modifier
                                                            .widthIn(
                                                                min = 155.dp
                                                            ),
                                                        title = "S.B",
                                                        subTitle = "44 mmolc dm⁻³"
                                                    )

                                                    AnalysisCard(
                                                        modifier = Modifier
                                                            .widthIn(
                                                                min = 155.dp
                                                            ),
                                                        title = "C.T.C",
                                                        subTitle = "83 mmolc dm⁻³"
                                                    )
                                                }

                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                ) {
                                                    AnalysisCard(
                                                        modifier = Modifier
                                                            .fillMaxWidth(),
                                                        title = "P.R.N.T",
                                                        subTitle = "Em porcentagem",
                                                        fillMaxWidth = true
                                                    ) {
                                                        Text(
                                                            text = "90%",
                                                            style = Typography.bodySmall,
                                                            fontWeight = FontWeight.SemiBold,
                                                            color = BRANCO
                                                        )
                                                    }
                                                }

                                                Description(
                                                    text = "* Saturação de base atual (S.B), Capacidade de troca catiônica do solo (C.T.C) e Poder relativo de neutralização total do Calcário (PRNT).",
                                                    color = VERDE_ESCURO,
                                                    style = Typography.displaySmall
                                                )
                                            }
                                        }
                                    }

                                    Information(
                                        space = 20.dp,
                                        icon = R.drawable.analysisiconchecksquare,
                                        title = "Aplicação recomendada"
                                    ) {
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(
                                                space = 40.dp
                                            )
                                        ) {
                                            Description(
                                                text = "Baseado nos cálculos feitos, a quantidade de calcário necessária na aplicação do solo, para que se obtenha a saturação de bases desejada, é de:"
                                            )

                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                            ) {
                                                AnalysisCard(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    title = "Calcário",
                                                    subTitle = "Em kilogramas/Hectare",
                                                    fillMaxWidth = true
                                                ) {
                                                    Text(
                                                        text = "6.650kg/ha",
                                                        style = Typography.labelSmall,
                                                        fontWeight = FontWeight.ExtraBold,
                                                        color = BRANCO
                                                    )
                                                }
                                            }
                                        }
                                    }
                                } ?: run {
                                    Information(
                                        space = 20.dp,
                                        icon = R.drawable.fieldicontroubleshoot,
                                        title = "Cálculo de Adubação"
                                    ) {
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(
                                                space = 40.dp
                                            )
                                        ) {
                                            Description(
                                                text = "Esses são os dados que foram disponibilizados, por você, no momento em que a análise foi feita:"
                                            )

                                            Column(
                                                verticalArrangement = Arrangement.spacedBy(
                                                    space = 20.dp
                                                )
                                            ) {
                                                CreateDetails(
                                                    username = "Victor",
                                                    createdAt = "10 Maio 2024 ás 17:54"
                                                )

                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    AnalysisCard(
                                                        modifier = Modifier
                                                            .widthIn(
                                                                min = 155.dp
                                                            ),
                                                        title = "Fósforo",
                                                        subTitle = "15 mg dm⁻³"
                                                    )

                                                    AnalysisCard(
                                                        modifier = Modifier
                                                            .widthIn(
                                                                min = 155.dp
                                                            ),
                                                        title = "Potássio",
                                                        subTitle = "83 mmolc dm⁻³"
                                                    )
                                                }

                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                ) {
                                                    AnalysisCard(
                                                        modifier = Modifier
                                                            .fillMaxWidth(),
                                                        title = "P.E",
                                                        fillMaxWidth = true
                                                    ) {
                                                        Text(
                                                            text = "Menor que 20 t/ha",
                                                            style = Typography.labelSmall,
                                                            fontWeight = FontWeight.ExtraBold,
                                                            color = BRANCO
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    Information(
                                        space = 20.dp,
                                        icon = R.drawable.analysisiconchecksquare,
                                        title = "Aplicação recomendada"
                                    ) {
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(
                                                space = 40.dp
                                            )
                                        ) {
                                            Description(
                                                text = "Baseado em cálculos feitos, nós construimos uma recomendação personalizada, de quanto de nitrogênio, fósforo e potássio será necessário aplicar nesse solo, para que se obtenha a produtividade esperada:"
                                            )

                                            Column(
                                                verticalArrangement = Arrangement.spacedBy(
                                                    space = 20.dp
                                                )
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    AnalysisCard(
                                                        modifier = Modifier
                                                            .widthIn(
                                                                min = 155.dp
                                                            ),
                                                        title = "Fósforo",
                                                        subTitle = "30 mg dm⁻³"
                                                    )

                                                    AnalysisCard(
                                                        modifier = Modifier
                                                            .widthIn(
                                                                min = 155.dp
                                                            ),
                                                        title = "Potássio",
                                                        subTitle = "90 mmolc dm⁻³"
                                                    )
                                                }

                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                ) {
                                                    AnalysisCard(
                                                        modifier = Modifier
                                                            .fillMaxWidth(),
                                                        title = "Nitrogênio",
                                                        fillMaxWidth = true
                                                    ) {
                                                        Text(
                                                            text = "70 dm⁻³",
                                                            style = Typography.labelSmall,
                                                            fontWeight = FontWeight.ExtraBold,
                                                            color = BRANCO
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 30.dp,
                                        end = 30.dp,
                                        bottom = 60.dp
                                    )
                            ) {
                                Button(
                                    texto = "Saber mais",
                                    modifier = Modifier
                                        .padding(vertical = 18.dp, horizontal = 15.dp),
                                    hasIcon = true,
                                    icon = ImageVector.vectorResource(id = R.drawable.baseline_read_more_24),
                                    shape = ShapeProperty.small,
                                    onClick = {
                                        navigationController.navigate("ReadMore")
                                    },
                                    backgroundColor = VERDE_CLARO,
                                    contentColor = BRANCO,
                                    defaultElevetion = 3.dp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}