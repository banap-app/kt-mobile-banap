package com.banap.banap.app.presentation.analysis.ui.listing.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
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
import com.banap.banap.core.ui.theme.Typography

@Composable
fun Analysis(
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

    // Análises

    var analysis: List<String> by remember {
        mutableStateOf(
            listOf(
//                "Análise 01",
//                "Análise 02",
//                "Análise 03",
//                "Análise 04",
//                "Análise 05",
//                "Análise 06",
//                "Análise 07",
//                "Análise 08",
//                "Análise 09",
//                "Análise 10",
//                "Análise 11",
//                "Análise 12",
//                "Análise 13",
//                "Análise 14",
//                "Análise 15"
            )
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
                                    .fillMaxWidth(),
                                onClick = {
                                    navigationController.navigate("ClientAnalysis")
                                },
                                title = "Análise",
                                titleStyle = Typography.labelSmall,
                                nameStyle = Typography.titleMedium,
                                name = analysis[it] // "Análise " + (if (((analysis.size - it)) < 10) "0${it + 1}" else it + 1)
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