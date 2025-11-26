package com.banap.banap.app.presentation.analysis.ui.listing.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.client.ui.listing.components.HandlingAllStates
import com.banap.banap.app.presentation.client.ui.listing.components.ListingTitle
import com.banap.banap.app.presentation.home.ui.components.ScaffoldCustomizedForEngineerScreens
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.skeleton.ui.clients.screen.ListingClientsSkeleton
import com.banap.banap.core.ui.components.ListItemCard
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_INTERMEDIARIO
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.util.applyFilters
import com.banap.banap.data.model.analysis.AnalysisResponse
import com.banap.banap.data.model.analysis.FilterOptions
import com.banap.banap.domain.viewmodel.analysis.ListAnalysisViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Analysis(
    navigationController: NavController,
    tokenViewModel: TokenViewModel,
    listAnalysisViewModel: ListAnalysisViewModel
) {
    // Variaveis do Scaffold

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    // Variaveis do BottomSheet

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var chosenAnalysisType by remember { mutableStateOf("Calagem") }

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

    // Análises

    val listAnalysisState = listAnalysisViewModel.state.value

    var analysis: List<AnalysisResponse> by remember {
        mutableStateOf(listOf())
    }

    LaunchedEffect(listAnalysisState.isLoading) {
        isContentLoading = listAnalysisState.isLoading
    }

    LaunchedEffect(listAnalysisState.response) {
        listAnalysisState.response?.let {
            analysis = listAnalysisState.response.analysis
            Log.d("launched analysis", "launched analysis: $analysis")
        }
    }

    LaunchedEffect(listAnalysisState.error) {
        hasContentError = listAnalysisState.error
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
                    fieldId = tokenViewModel.getToken("fieldId"),
                    userName = tokenViewModel.getToken("userName"),
                    fixedRoute = "ClientField"
                )
            }

            item {
                ListingTitle(
                    icon = ImageVector.vectorResource(id = R.drawable.analysisicontitle),
                    space = 15.dp,
                    title = "Análises"
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 30.dp,
                            end = 30.dp,
                            bottom = 5.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Ordenar por:",
                            style = Typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = CINZA_INTERMEDIARIO
                        )

                        Text(
                            text = chosenAnalysisType,
                            style = Typography.displaySmall,
                            fontWeight = FontWeight.Medium,
                            color = PRETO,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        VerticalDivider(
                            modifier = Modifier
                                .height(20.dp),
                            thickness = 1.dp,
                            color = CINZA_INTERMEDIARIO
                        )

                        Row(
                            modifier = Modifier
                                .clickable {
                                    showBottomSheet = true
                                },
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_filter_list_24),
                                contentDescription = "Icone de filtro",
                                modifier = Modifier
                                    .padding(
                                        start = 8.dp,
                                        top = 5.dp,
                                        bottom = 5.dp
                                    )
                            )

                            Text(
                                text = "Filtro",
                                style = Typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = CINZA_INTERMEDIARIO,
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                        top = 5.dp,
                                        bottom = 5.dp
                                    )
                            )
                        }
                    }
                }

                if (showBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            showBottomSheet = false
                        },
                        sheetState = sheetState,
                        containerColor = BRANCO
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(
                                    horizontal = 30.dp,
                                    vertical = 10.dp
                                )
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(
                                space = 24.dp
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Filtro",
                                    style = Typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = PRETO
                                )

                                Text(
                                    text = "Resetar filtros",
                                    style = Typography.displaySmall,
                                    color = CINZA_INTERMEDIARIO
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(
                                        space = 16.dp
                                    )
                                ) {
                                    Text(
                                        text = "Tipos de Análises",
                                        style = Typography.bodyLarge,
                                        fontWeight = FontWeight.Normal,
                                        color = PRETO
                                    )

                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(
                                            space = 8.dp
                                        )
                                    ) {
                                        Card(
                                            colors = CardDefaults.cardColors(
                                                containerColor = if (chosenAnalysisType == "Calagem") VERDE_CLARO else BRANCO,
                                                contentColor = if (chosenAnalysisType == "Calagem") BRANCO else VERDE_CLARO
                                            ),
                                            onClick = {
                                                chosenAnalysisType = "Calagem"

                                                val filters = FilterOptions(
                                                    type = when (chosenAnalysisType) {
                                                        "Calagem" -> "LIMING"
                                                        else -> null
                                                    }
                                                )

                                                analysis = applyFilters(analysis, filters)
                                            },
                                            border =
                                            BorderStroke(
                                                width = 1.dp,
                                                color = VERDE_CLARO
                                            ),
                                            shape = RoundedCornerShape(20.dp)
                                        ) {
                                            Text(
                                                text = "Calagem",
                                                style = Typography.displaySmall,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier
                                                    .padding(
                                                        horizontal = 16.dp,
                                                        vertical = 10.dp
                                                    )
                                            )
                                        }

                                        Card(
                                            colors = CardDefaults.cardColors(
                                                containerColor = if (chosenAnalysisType == "N.P.K") VERDE_CLARO else BRANCO,
                                                contentColor = if (chosenAnalysisType == "N.P.K") BRANCO else VERDE_CLARO
                                            ),
                                            onClick = {
                                                chosenAnalysisType = "N.P.K"

                                                val filters = FilterOptions(
                                                    type = when (chosenAnalysisType) {
                                                        "N.P.K" -> "NPK"
                                                        else -> null
                                                    }
                                                )

                                                analysis = applyFilters(analysis, filters)
                                            },
                                            border =
                                            BorderStroke(
                                                width = 1.dp,
                                                color = VERDE_CLARO
                                            ),
                                            shape = RoundedCornerShape(20.dp)
                                        ) {
                                            Text(
                                                text = "N.P.K",
                                                style = Typography.displaySmall,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier
                                                    .padding(
                                                        horizontal = 16.dp,
                                                        vertical = 10.dp
                                                    )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            when {
                isContentLoading -> {
                    item {
                        ListingClientsSkeleton(
                            times = 5,
                            height = 120.dp,
                            shape = 30.dp
                        )
                    }
                }

                hasContentError.isNotEmpty() -> {
                    item {
                        HandlingAllStates(
                            modifier = Modifier
                                .fillParentMaxHeight(0.5f),
                            text = "Ocorreu um erro ao\n carregar as análises...",
                            buttonText = "Tentar Novamente",
                            icon = ImageVector.vectorResource(id = R.drawable.homeiconretry),
                            onClick = {}
                        )
                    }
                }

                analysis.isNotEmpty() -> {
                    items(
                        count = analysis.size,
                        key = {
                            analysis[it].id
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
                                    .fillMaxWidth(),
                                onClick = {
                                    navigationController.navigate("ClientAnalysis")
                                },
                                title = "Análise",
                                titleStyle = Typography.labelSmall,
                                nameStyle = Typography.titleMedium,
                                name = "Análise " + (if (((analysis.size - it)) < 10) "0${it + 1}" else it + 1)
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

                else -> {
                    item {
                        HandlingAllStates(
                            modifier = Modifier
                                .fillParentMaxHeight(0.5f),
                            text = "Você ainda não\nrealizou uma análise!",
                            hasButton = false
                        )
                    }
                }
            }
        }
    }
}