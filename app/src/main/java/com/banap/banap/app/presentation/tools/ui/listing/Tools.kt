package com.banap.banap.app.presentation.tools.ui.listing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.client.ui.listing.components.ListingTitle
import com.banap.banap.app.presentation.home.ui.components.ScaffoldCustomizedForEngineerScreens
import com.banap.banap.app.presentation.home.ui.components.ToolsCard
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.core.ui.components.RegistrationHeader

@Composable
fun Tools(
    navigationController: NavController,
    tokenViewModel: TokenViewModel
) {
    // Variaveis do Scaffold

    val snackBarHostState = remember { SnackbarHostState() }

    ScaffoldCustomizedForEngineerScreens(
        snackBarHostState = snackBarHostState,
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
                    icon = ImageVector.vectorResource(id = R.drawable.analysisicontitle),
                    space = 15.dp,
                    title = "Ferramentas"
                )
            }

            item {
                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = 30.dp
                        )
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 15.dp
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 15.dp
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ToolsCard(
                            name = "Calagem",
                            icon = R.drawable.analysisiconliming,
                            onClick = {
                                navigationController.navigate("")
                            }
                        )

                        ToolsCard(
                            name = "N.P.K",
                            icon = R.drawable.analysisiconnpk,
                            onClick = {
                                navigationController.navigate("")
                            }
                        )

                        ToolsCard(
                            name = "Escanear",
                            icon = R.drawable.baseline_crop_free_24,
                            onClick = {}
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 15.dp
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ToolsCard(
                            name = "Espaçamento\nSimples",
                            icon = R.drawable.singlespacing,
                            iconSize = 1f,
                            space = 10.dp,
                            onClick = {}
                        )

                        ToolsCard(
                            name = "Medidas e\nConversões",
                            icon = R.drawable.pencilandruler,
                            iconSize = 1f,
                            space = 10.dp,
                            onClick = {}
                        )
                    }
                }
            }
        }
    }
}