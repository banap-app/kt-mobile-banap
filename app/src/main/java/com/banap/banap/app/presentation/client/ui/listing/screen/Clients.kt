package com.banap.banap.app.presentation.client.ui.listing.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.client.ui.listing.components.FloatingActionButton
import com.banap.banap.app.presentation.client.ui.listing.components.HandlingAllStates
import com.banap.banap.app.presentation.client.ui.listing.components.ListingClientCard
import com.banap.banap.app.presentation.client.ui.listing.components.ListingTitle
import com.banap.banap.app.presentation.home.ui.components.ScaffoldCustomizedForEngineerScreens
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.skeleton.ui.clients.screen.ListingClientsSkeleton
import com.banap.banap.core.ui.components.RegistrationHeader

@Composable
fun Clients(
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

    // Clientes

    var clients: List<String> by remember {
        mutableStateOf(
            listOf(
                "Lucas",
                "Asher"
            )
        )
    }

    ScaffoldCustomizedForEngineerScreens(
        snackBarHostState = snackBarHostState,
        hasLoadingScreen = hasLoadingScreen,
        floatingActionButton = {
            if (clients.isNotEmpty() && !isContentLoading) {
                FloatingActionButton(
                    buttonText = "Agregar Cliente",
                    onClick = {
                        navigationController.navigate("ClientAggregation")
                    }
                )
            }
        }
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
                    fixedRoute = "EngineerHome"
                )
            }

            item {
                ListingTitle(
                    icon = ImageVector.vectorResource(id = R.drawable.clients),
                    space = 15.dp,
                    title = "Clientes"
                )
            }

            when {
                isContentLoading -> {
                    item {
                        ListingClientsSkeleton()
                    }
                }

                hasContentError.isNotEmpty() -> {
                    item {
                        HandlingAllStates(
                            modifier = Modifier
                                .fillParentMaxHeight(0.5f),
                            text = "Ocorreu um erro ao\n tentar carregar os clientes...",
                            buttonText = "Tentar Novamente",
                            icon = ImageVector.vectorResource(id = R.drawable.homeiconretry),
                            onClick = {}
                        )
                    }
                }

                clients.isNotEmpty() -> {
                    items(
                        count = clients.size,
                        key = {
                            clients[it]
                        }
                    ) {
                        ListingClientCard(
                            modifier = Modifier
                                .padding(
                                    start = 30.dp,
                                    end = 30.dp,
                                    bottom = if (it == (clients.size - 1)) 60.dp else 10.dp
                                ),
                            onClick = {
                                navigationController.navigate("ClientInformation")
                            },
                            isClientInformation = false,
                            textMaxSize = 7,
                            name = clients[it]
                        )
                    }
                }

                else -> {
                    item {
                        HandlingAllStates(
                            modifier = Modifier
                                .fillParentMaxHeight(0.5f),
                            text = "Você ainda não\nagregou um cliente!",
                            buttonText = "Agregar Cliente",
                            icon = Icons.Outlined.Add,
                            onClick = {}
                        )
                    }
                }
            }
        }
    }
}