package com.banap.banap.app.presentation.analysis.ui.information.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.analysis.ui.information.components.AnalysisCard
import com.banap.banap.app.presentation.analysis.ui.information.components.CreateDetails
import com.banap.banap.app.presentation.analysis.ui.information.components.Description
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.skeleton.ui.analysis.screen.AnalysisInformationSkeleton
import com.banap.banap.core.ui.components.Button
import com.banap.banap.core.ui.components.ImageInformation
import com.banap.banap.core.ui.components.Information
import com.banap.banap.core.ui.components.InformationScreenPattern
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.core.ui.util.ISOConverter
import com.banap.banap.core.ui.util.getFirstName
import com.banap.banap.data.model.analysis.AnalysisResponse
import com.banap.banap.domain.viewmodel.analysis.GetAnalysisByIdViewModel

@Composable
fun AnalysisInformation(
    navigationController: NavController,
    getAnalysisByIdViewModel: GetAnalysisByIdViewModel = hiltViewModel(),
    tokenViewModel: TokenViewModel,
    analysisId: String,
    analysisName: String,
    fieldName: String
) {
    val getAnalysisByIdState = getAnalysisByIdViewModel.state.value

    var analysis: AnalysisResponse? by remember {
        mutableStateOf(null)
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var getAnalysisByIdError by remember {
        mutableStateOf("")
    }

    LaunchedEffect(true) {
        if (!analysisName.contains("analysisName")) {
            tokenViewModel.saveToken("analysisName", analysisName)
        }
    }

    LaunchedEffect(true) {
        if (!fieldName.contains("fieldName")) {
            tokenViewModel.saveToken("fieldName", fieldName)
        }
    }

    LaunchedEffect(true) {
        if (tokenViewModel.getToken("analysisId").isNullOrEmpty()) {
            getAnalysisByIdViewModel.getAnalysisById(analysisId)
        } else {
            getAnalysisByIdViewModel.getAnalysisById(tokenViewModel.getToken("analysisId") ?: "")
        }
    }

    LaunchedEffect(getAnalysisByIdState.response) {
        getAnalysisByIdState.response?.let {
            tokenViewModel.saveToken("analysisId", getAnalysisByIdState.response.id)
            analysis = getAnalysisByIdState.response
        }
    }

    LaunchedEffect(getAnalysisByIdState.error) {
        getAnalysisByIdError = getAnalysisByIdState.error
    }

    LaunchedEffect(getAnalysisByIdState.isLoading) {
        isLoading = getAnalysisByIdState.isLoading
    }

    InformationScreenPattern(
        navigationController = navigationController,
        fieldId = tokenViewModel.getToken("fieldId") ?: "",
        userName = tokenViewModel.getToken("userName") ?: "",
        fixedRoute = "Information",
        title = tokenViewModel.getToken("analysisName") ?: "",
        titleIcon = R.drawable.analysisicontitle,
        isLoading = isLoading
    ) {
        when {
            isLoading -> {
                AnalysisInformationSkeleton()
            }

            else -> {
                ImageInformation(
                    image = R.drawable.fieldimage,
                    icon = R.drawable.field,
                    text = tokenViewModel.getToken("fieldName") ?: ""
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.analysisiconimageinformation),
                        contentDescription = "Icone da Cultura",
                        tint = VERDE_CLARO
                    )
                }

                Information(
                    space = 20.dp,
                    icon = R.drawable.round_signal_cellular_alt_24,
                    title = "Dados da análise"
                ) {
                    Description(
                        text = "Temos dois tipos de cálculos feitos acerca dos dados disponibilizados. O de calagem, e recomendação de adubação de solo."
                    )
                }

                analysis?.typeAnalysis?.liming?.let {
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
                                    username = tokenViewModel.getToken("userName")
                                        ?.let { it1 -> getFirstName(it1) },
                                    createdAt = ISOConverter(
                                        analysis?.createdAt ?: "2025-09-18T15:23:16.588Z"
                                    )
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
                                        subTitle = "${analysis?.typeAnalysis?.currentBaseSaturation?.toInt()} mmolc dm⁻³"
                                    )

                                    AnalysisCard(
                                        modifier = Modifier
                                            .widthIn(
                                                min = 155.dp
                                            ),
                                        title = "C.T.C",
                                        subTitle = "${analysis?.typeAnalysis?.totalCationExchangeCapacity?.toInt()} mmolc dm⁻³"
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
                                            text = "${analysis?.typeAnalysis?.relativeTotalNeutralizingPower?.toInt()}%",
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
                                        text = "${analysis?.typeAnalysis?.liming}kg/ha",
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
                                    username = tokenViewModel.getToken("userName")
                                        ?.let { it1 -> getFirstName(it1) },
                                    createdAt = ISOConverter(
                                        analysis?.createdAt ?: "2025-09-18T15:23:16.588Z"
                                    )
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
                                        subTitle = "${analysis?.typeAnalysis?.phosphor?.toInt()} mg dm⁻³"
                                    )

                                    AnalysisCard(
                                        modifier = Modifier
                                            .widthIn(
                                                min = 155.dp
                                            ),
                                        title = "Potássio",
                                        subTitle = "${analysis?.typeAnalysis?.potassium?.toInt()} mmolc dm⁻³"
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
                                            text = "${
                                                when (analysis?.typeAnalysis?.expectedProductivity) {
                                                    19 -> "Menor que 20 t/ha"
                                                    29 -> "Entre 20 e 30 t/ha"
                                                    39 -> "Entre 30 e 40 t/ha"
                                                    49 -> "Entre 40 e 50 t/ha"
                                                    60 -> "Maior que 50 t/ha"
                                                    else -> 0
                                                }
                                            }",
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
                                        subTitle = "${analysis?.typeAnalysis?.phosphor?.toInt()} mg dm⁻³"
                                    )

                                    AnalysisCard(
                                        modifier = Modifier
                                            .widthIn(
                                                min = 155.dp
                                            ),
                                        title = "Potássio",
                                        subTitle = "${analysis?.typeAnalysis?.potassium?.toInt()} mmolc dm⁻³"
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
                                            text = "${analysis?.typeAnalysis?.nitrogen?.toInt()} dm⁻³",
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