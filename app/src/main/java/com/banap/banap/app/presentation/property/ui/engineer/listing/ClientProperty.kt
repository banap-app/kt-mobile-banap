package com.banap.banap.app.presentation.property.ui.engineer.listing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
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
import com.banap.banap.app.presentation.client.ui.listing.components.HandlingAllStates
import com.banap.banap.app.presentation.client.ui.listing.components.ListingTitle
import com.banap.banap.app.presentation.home.ui.components.ScaffoldCustomizedForEngineerScreens
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.skeleton.ui.clients.screen.ListingClientsSkeleton
import com.banap.banap.core.ui.components.ListItemCard
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.theme.Typography

@Composable
fun ClientProperty(
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

    // Talhões

    var fields: List<String> by remember {
        mutableStateOf(
            listOf(
                "Talhão 01",
                "Talhão 02"
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
                    fixedRoute = "ClientInformation"
                )
            }

            when {
                isContentLoading -> {
                    item {
                        ListingClientsSkeleton(
                            times = 5,
                            isClientProperty = true,
                            height = 150.dp,
                            shape = 30.dp
                        )
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
                            icon = Icons.Outlined.Home,
                            space = 15.dp,
                            title = "Propriedade 01"
                        )
                    }

                    items(
                        count = fields.size,
                        key = {
                            fields[it]
                        }
                    ) {
                        ListItemCard(
                            modifier = Modifier
                                .padding(
                                    start = 30.dp,
                                    end = 30.dp,
                                    bottom = if (it == (fields.size - 1)) 60.dp else 10.dp
                                )
                                .height(150.dp)
                                .fillMaxWidth(),
                            onClick = {
                                navigationController.navigate("ClientField")
                            },
                            title = "Talhão",
                            titleStyle = Typography.titleMedium,
                            nameStyle = Typography.displayLarge,
                            name = fields[it]
                        ) {
                            Image(
                                imageVector = ImageVector.vectorResource(id = R.drawable.propertyimagefield),
                                contentDescription = "Imagem do Talhao da Propriedade"
                            )
                        }
                    }
                }
            }
        }
    }
}