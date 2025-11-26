package com.banap.banap.app.presentation.field.ui.engineer.information.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.analysis.ui.producer.information.components.CreateDetails
import com.banap.banap.app.presentation.client.ui.listing.components.HandlingAllStates
import com.banap.banap.app.presentation.client.ui.listing.components.ListingTitle
import com.banap.banap.app.presentation.field.ui.engineer.information.components.BreakdownByQuarter
import com.banap.banap.app.presentation.field.ui.engineer.information.components.ObservationCard
import com.banap.banap.app.presentation.home.ui.components.NoData
import com.banap.banap.app.presentation.home.ui.components.ScaffoldCustomizedForEngineerScreens
import com.banap.banap.app.presentation.home.ui.components.TitleSection
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.skeleton.ui.clients.components.ClientFieldInformationAnalysisSectionSkeleton
import com.banap.banap.app.presentation.skeleton.ui.clients.components.ClientFieldInformationGraphicSectionSkeleton
import com.banap.banap.app.presentation.skeleton.ui.clients.components.ClientFieldInformationObservationSectionSkeleton
import com.banap.banap.app.presentation.skeleton.ui.clients.screen.ClientFieldInformationSkeleton
import com.banap.banap.core.ui.components.Button
import com.banap.banap.core.ui.components.ImageInformation
import com.banap.banap.core.ui.components.Information
import com.banap.banap.core.ui.components.ListItemCard
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.CINZA_INTERMEDIARIO
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.domain.model.client.GraphicData
import com.banap.banap.domain.model.client.generatePath

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientField(
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

    // Analises

    var analysis: List<String> by remember {
        mutableStateOf(
            listOf(
                "Análise 01",
                "Análise 02",
                "Análise 03"
            )
        )
    }

    var analysisLoading: Boolean by remember {
        mutableStateOf(false)
    }

    var hasAnalysisError: String by remember {
        mutableStateOf("")
    }

    // Observações

    var observations: List<String> by remember {
        mutableStateOf(
            listOf(
                "Observação 01",
                "Observação 02",
                "Observação 03"
            )
        )
    }

    var observationLoading: Boolean by remember {
        mutableStateOf(
            false
        )
    }

    var hasObservationError: String by remember {
        mutableStateOf("")
    }

    // Grafico

    val potassiumData = listOf(
        listOf(10f, 20f, 40f, 60f, 50f),
        listOf(15f, 30f, 25f, 55f, 70f)
    )

    val phosphorusData = listOf(
        listOf(5f, 10f, 15f, 20f, 25f),
        listOf(40f, 35f, 30f, 20f, 10f)
    )

    val allData = listOf(
        GraphicData("Potássio", potassiumData),
        GraphicData("Fósforo", phosphorusData)
    )

    var dataSets by remember { mutableStateOf(allData.flatMap { it.values }) }

    var graphicLoading: Boolean by remember {
        mutableStateOf(
            false
        )
    }

    var hasGraphicError: String by remember {
        mutableStateOf("")
    }

    var isGraphicFilterExpanded: Boolean by remember {
        mutableStateOf(
            false
        )
    }

    var graphicFilterChosenOption: String by remember {
        mutableStateOf(
            "Nutriente"
        )
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
                    fixedRoute = "ClientProperty"
                )
            }

            when {
                isContentLoading -> {
                    item {
                        ClientFieldInformationSkeleton()
                    }
                }

                hasContentError.isNotEmpty() -> {
                    item {
                        HandlingAllStates(
                            modifier = Modifier
                                .fillParentMaxHeight(0.7f),
                            text = "Ocorreu um erro ao carregar\nos talhões da propriedade...",
                            buttonText = "Tentar Novamente",
                            icon = ImageVector.vectorResource(id = R.drawable.homeiconretry),
                            onClick = {}
                        )
                    }
                }

                else -> {
                    item {
                        ListingTitle(
                            icon = ImageVector.vectorResource(id = R.drawable.field),
                            space = 15.dp,
                            title = "Talhão 01"
                        )
                    }

                    item {
                        ImageInformation(
                            image = R.drawable.fieldimage,
                            icon = R.drawable.fieldiconplant,
                            text = "Banana Nanica",
                            child = {
                                Text(
                                    text = "10",
                                    style = Typography.bodyLarge,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = VERDE_CLARO
                                )
                            }
                        )
                    }

                    item {
                        Information(
                            paddingTop = 60.dp,
                            icon = R.drawable.fieldicondescription,
                            title = "Descrição",
                            child = {
                                Text(
                                    text = "Esse talhão fica perto da cerca ao leste da fazenda, ao lado de outros talhões de banana prata..",
                                    style = Typography.bodyLarge,
                                    color = PRETO,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Justify
                                )
                            }
                        )
                    }

                    item {
                        Information(
                            paddingTop = 60.dp,
                            icon = R.drawable.round_signal_cellular_alt_24,
                            title = "Dados da análise"
                        )
                    }

                    when {
                        graphicLoading -> {
                            item {
                                ClientFieldInformationGraphicSectionSkeleton()
                            }
                        }

                        hasGraphicError.isNotEmpty() -> {
                            item {
                                HandlingAllStates(
                                    modifier = Modifier
                                        .padding(
                                            top = 40.dp
                                        ),
                                    text = "Ocorreu um erro\nao carregar os dados...",
                                    buttonText = "Tentar Novamente",
                                    icon = ImageVector.vectorResource(id = R.drawable.homeiconretry),
                                    onClick = {}
                                )
                            }
                        }

                        else -> {
                            item {
                                ExposedDropdownMenuBox(
                                    expanded = isGraphicFilterExpanded,
                                    onExpandedChange = {
                                        isGraphicFilterExpanded = !isGraphicFilterExpanded
                                    },
                                    modifier = Modifier
                                        .padding(
                                            horizontal = 30.dp,
                                            vertical = 20.dp
                                        )
                                ) {
                                    Card(
                                        onClick = {
                                            isGraphicFilterExpanded = true
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = BRANCO
                                        ),
                                        border = BorderStroke(
                                            1.dp,
                                            PRETO.copy(
                                                alpha = 0.1f
                                            )
                                        ),
                                        modifier = Modifier.menuAnchor()
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(
                                                start = 15.dp,
                                                end = 10.dp,
                                                top = 5.dp,
                                                bottom = 5.dp
                                            ),
                                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "ordenar por:",
                                                style = Typography.bodySmall,
                                                fontWeight = FontWeight.Bold,
                                                color = CINZA_INTERMEDIARIO
                                            )

                                            Text(
                                                text = graphicFilterChosenOption,
                                                style = Typography.displaySmall,
                                                fontWeight = FontWeight.Medium,
                                                color = PRETO,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )

                                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isGraphicFilterExpanded)
                                        }
                                    }

                                    ExposedDropdownMenu(
                                        expanded = isGraphicFilterExpanded,
                                        onDismissRequest = { isGraphicFilterExpanded = false },
                                        modifier = Modifier
                                            .background(BRANCO)
                                    ) {
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = "Todos os Nutrientes",
                                                    style = Typography.bodySmall,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = PRETO
                                                )
                                            },
                                            onClick = {
                                                graphicFilterChosenOption = "Todos os Nutrientes"
                                                dataSets = allData.flatMap { it.values }
                                                isGraphicFilterExpanded = false
                                            }
                                        )

                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = "Potássio",
                                                    style = Typography.bodySmall,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = PRETO
                                                )
                                            },
                                            onClick = {
                                                graphicFilterChosenOption = "Potássio"
                                                dataSets =
                                                    allData.first { it.name == "Potássio" }.values
                                                isGraphicFilterExpanded = false
                                            }
                                        )

                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = "Fósforo",
                                                    style = Typography.bodySmall,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = PRETO
                                                )
                                            },
                                            onClick = {
                                                graphicFilterChosenOption = "Fósforo"
                                                dataSets =
                                                    allData.first { it.name == "Fósforo" }.values
                                                isGraphicFilterExpanded = false
                                            }
                                        )
                                    }
                                }

                                Canvas(
                                    modifier = Modifier
                                        .padding(
                                            start = 30.dp,
                                            end = 50.dp,
                                        )
                                        .aspectRatio(3 / 2f)
                                        .fillMaxSize()
                                        .drawWithCache {
                                            val paths = dataSets.map { generatePath(it, size) }

                                            onDrawBehind {
                                                val colors = listOf(
                                                    Color.Green,
                                                    Color.Red,
                                                    Color.Blue,
                                                    Color.Yellow
                                                )
                                                paths.forEachIndexed { i, path ->
                                                    drawPath(
                                                        path = path,
                                                        color = colors[i],
                                                        style = Stroke(2.dp.toPx())
                                                    )
                                                }
                                            }
                                        }
                                ) {
                                    val barWidthPx = 1.dp.toPx()
                                    drawLine(
                                        color = PRETO.copy(
                                            alpha = 0.2f
                                        ),
                                        start = Offset(0f, 0f),
                                        end = Offset(0f, size.height),
                                        strokeWidth = barWidthPx
                                    )

                                    val verticalLines = 5
                                    val verticalSize = size.width / (verticalLines + 1)
                                    repeat(verticalLines) { i ->
                                        val startX = verticalSize * (i + 1)
                                        drawLine(
                                            color = PRETO.copy(
                                                alpha = 0.2f
                                            ),
                                            start = Offset(startX, 0f),
                                            end = Offset(startX, size.height),
                                            strokeWidth = barWidthPx
                                        )
                                    }

                                    val maxYValue = dataSets.flatten().maxOrNull() ?: 0f
                                    val horizontalLines = 6
                                    val sectionSize = size.height / (horizontalLines - 1)
                                    repeat(horizontalLines) { i ->
                                        val startY = size.height - (sectionSize * i)
                                        val value = (maxYValue / (horizontalLines - 1)) * i

                                        if (i != (horizontalLines - 1)) {
                                            drawLine(
                                                color = PRETO.copy(alpha = 0.2f),
                                                start = Offset(0f, startY),
                                                end = Offset(size.width, startY),
                                                strokeWidth = barWidthPx
                                            )
                                        }

                                        drawContext.canvas.nativeCanvas.apply {
                                            drawText(
                                                "${value.toInt()}",
                                                size.width + 25f,
                                                startY,
                                                android.graphics.Paint().apply {
                                                    color = android.graphics.Color.BLACK
                                                    textSize = 30f
                                                    textAlign = android.graphics.Paint.Align.LEFT
                                                    isAntiAlias = true
                                                }
                                            )
                                        }
                                    }
                                }

                                Column(
                                    modifier = Modifier
                                        .padding(
                                            start = 30.dp,
                                            end = 30.dp,
                                            top = 20.dp
                                        )
                                        .fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(
                                        space = 10.dp,
                                        alignment = Alignment.CenterVertically
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = when (graphicFilterChosenOption) {
                                                "Potássio" -> "Potássio (mmolc dm⁻³)"
                                                "Fósforo" -> "Fósforo (mg dm⁻³)"
                                                else -> "Todos os Nutrientes"
                                            },
                                            style = Typography.bodyMedium,
                                            color = PRETO
                                        )
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(
                                            space = 10.dp,
                                            alignment = Alignment.CenterHorizontally
                                        )
                                    ) {
                                        BreakdownByQuarter(
                                            "Jan/Mar",
                                            color = Color.Green
                                        )

                                        BreakdownByQuarter(
                                            "Abr/Jun",
                                            color = Color.Red
                                        )

                                        BreakdownByQuarter(
                                            "Julho/Set",
                                            color = Color.Blue
                                        )

                                        BreakdownByQuarter(
                                            "Ago/Dez",
                                            color = Color.Yellow
                                        )
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Information(
                            paddingTop = 60.dp,
                            icon = R.drawable.observation,
                            title = "Observações"
                        )
                    }

                    when {
                        observationLoading -> {
                            item {
                                ClientFieldInformationObservationSectionSkeleton()
                            }
                        }

                        hasObservationError.isNotEmpty() -> {
                            item {
                                HandlingAllStates(
                                    modifier = Modifier
                                        .padding(
                                            top = 20.dp
                                        ),
                                    text = "Ocorreu um erro\nao carregar as observações...",
                                    buttonText = "Tentar Novamente",
                                    icon = ImageVector.vectorResource(id = R.drawable.homeiconretry),
                                    onClick = {}
                                )
                            }
                        }

                        observations.isEmpty() -> {
                            item {
                                NoData(
                                    text = "Você ainda não fez nenhuma observação!",
                                    buttonValue = "Fazer Observação",
                                    modifier = Modifier
                                        .padding(
                                            top = 20.dp
                                        ),
                                    onClick = {}
                                )
                            }
                        }

                        else -> {
                            item {
                                Column(
                                    modifier = Modifier
                                        .padding(
                                            start = 30.dp,
                                            end = 30.dp,
                                            top = 20.dp,
                                            bottom = 10.dp
                                        ),
                                    horizontalAlignment = Alignment.End,
                                    verticalArrangement = Arrangement.spacedBy(
                                        space = 40.dp
                                    )
                                ) {
                                    Text(
                                        text = "10 Maio 2024 ás 17:54",
                                        style = Typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = CINZA_INTERMEDIARIO
                                    )
                                }
                            }

                            items(
                                count = observations.size,
                                key = {
                                    observations[it]
                                }
                            ) {
                                var isObservationDropdownExpanded =
                                    remember { mutableStateOf(false) }

                                ObservationCard(
                                    modifier = Modifier
                                        .padding(
                                            start = 30.dp,
                                            end = 30.dp,
                                            bottom = if (it == (observations.size - 1)) 40.dp else 10.dp
                                        ),
                                    isObservationDropdownExpanded = isObservationDropdownExpanded,
                                    observationTitle = observations[it],
                                    observationDescription = "Adubação no  talhão 1 realizada em 20/05/2024, verificar se teve efeitos na proxima análise."
                                )
                            }

                            item {
                                Row(
                                    modifier = Modifier
                                        .padding(
                                            start = 30.dp,
                                            end = 30.dp
                                        )
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Button(
                                        texto = "Fazer Observação",
                                        modifier = Modifier
                                            .padding(vertical = 18.dp, horizontal = 15.dp),
                                        hasIcon = true,
                                        shape = ShapeProperty.small,
                                        onClick = {
                                            navigationController.navigate("NewObservation")
                                        },
                                        backgroundColor = VERDE_CLARO,
                                        contentColor = BRANCO,
                                        defaultElevetion = 3.dp
                                    )
                                }
                            }
                        }
                    }

                    item {
                        TitleSection(
                            title = "Análises",
                            isRowList = false,
                            hasSpaceTop = true,
                            hasArrowButton = analysis.isNotEmpty(),
                            onClickClickableText = {
                                navigationController.navigate("Analysis")
                            },
                            onClickArrowButton = {
                                navigationController.navigate("Analysis")
                            }
                        )
                    }

                    when {
                        analysisLoading -> {
                            item {
                                ClientFieldInformationAnalysisSectionSkeleton()
                            }
                        }

                        hasAnalysisError.isNotEmpty() -> {
                            item {
                                HandlingAllStates(
                                    modifier = Modifier
                                        .padding(
                                            top = 20.dp,
                                            bottom = 60.dp
                                        ),
                                    text = "Ocorreu um erro\nao carregar as análises...",
                                    buttonText = "Tentar Novamente",
                                    icon = ImageVector.vectorResource(id = R.drawable.homeiconretry),
                                    onClick = {}
                                )
                            }
                        }

                        analysis.isEmpty() -> {
                            item {
                                Row(
                                    modifier = Modifier
                                        .padding(
                                            start = 30.dp,
                                            end = 30.dp,
                                            bottom = 60.dp
                                        )
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Gilmar ainda não realizou nenhuma análise!",
                                        style = Typography.bodyLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = CINZA_ESCURO
                                    )
                                }
                            }
                        }

                        else -> {
                            item {
                                Row(
                                    modifier = Modifier
                                        .padding(
                                            start = 30.dp,
                                            end = 30.dp,
                                            bottom = 20.dp
                                        )
                                        .fillMaxWidth()
                                ) {
                                    CreateDetails(
                                        username = "Victor",
                                        createdAt = "17 Maio 2024 ás 20:56"
                                    )
                                }
                            }

                            items(
                                count = analysis.size,
                                key = {
                                    analysis[it]
                                }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(
                                            top = 5.dp,
                                            bottom = if (it == (analysis.size - 1)) 60.dp else 5.dp,
                                            start = 30.dp,
                                            end = 30.dp
                                        )
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    ListItemCard(
                                        modifier = Modifier
                                            .height(120.dp)
                                            .widthIn(
                                                max = 290.dp
                                            ),
                                        onClick = {
                                            navigationController.navigate("ClientAnalysis")
                                        },
                                        title = "Análise",
                                        titleStyle = Typography.labelSmall,
                                        nameStyle = Typography.titleMedium,
                                        name = "Análise " + (if (((analysis.size - it)) < 10) "0${analysis.size - it}" else analysis.size - it)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .padding(end = 30.dp),
                                            horizontalArrangement = Arrangement.spacedBy(
                                                space = 15.dp,
                                                alignment = Alignment.CenterHorizontally
                                            )
                                        ) {
                                            Column(
                                                verticalArrangement = Arrangement.spacedBy(
                                                    space = 5.dp
                                                ),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Text(
                                                    text = "1+",
                                                    style = Typography.bodySmall,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    color = BRANCO
                                                )

                                                Icon(
                                                    imageVector = ImageVector.vectorResource(
                                                        id = R.drawable.analysisiconliming
                                                    ),
                                                    contentDescription = "Icone de Calagem",
                                                    modifier = Modifier
                                                        .scale(1.3f)
                                                )
                                            }

                                            Column(
                                                verticalArrangement = Arrangement.spacedBy(
                                                    space = 5.dp
                                                ),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Text(
                                                    text = "1+",
                                                    style = Typography.bodySmall,
                                                    fontWeight = FontWeight.ExtraBold,
                                                    color = BRANCO
                                                )

                                                Icon(
                                                    imageVector = ImageVector.vectorResource(
                                                        id = R.drawable.analysisiconnpk
                                                    ),
                                                    contentDescription = "Icone de Npk",
                                                    modifier = Modifier
                                                        .scale(1.3f)
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
        }
    }
}