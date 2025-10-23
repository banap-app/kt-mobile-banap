package com.banap.banap.app.presentation.client.ui.information.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.client.ui.listing.components.HandlingAllStates
import com.banap.banap.app.presentation.client.ui.listing.components.ListingClientCard
import com.banap.banap.app.presentation.client.ui.listing.components.ListingTitle
import com.banap.banap.app.presentation.home.ui.components.ScaffoldCustomizedForEngineerScreens
import com.banap.banap.app.presentation.home.ui.components.TitleSection
import com.banap.banap.app.presentation.session.viewmodel.TokenViewModel
import com.banap.banap.app.presentation.skeleton.ui.clients.screen.ClientInformationSkeleton
import com.banap.banap.core.ui.components.Button
import com.banap.banap.core.ui.components.RegistrationHeader
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO

@Composable
fun ClientInformation(
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

    // Propriedades

    var propertys: List<String> by remember {
        mutableStateOf(
            listOf(
                "Propriedade 01"
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
                    fixedRoute = "Clients"
                )
            }

            when {
                isContentLoading -> {
                    item {
                        ClientInformationSkeleton()
                    }
                }

                hasContentError.isNotEmpty() -> {
                    item {
                        HandlingAllStates(
                            modifier = Modifier
                                .fillParentMaxHeight(0.7f),
                            text = "Ocorreu um erro ao carregar\nas informações do cliente...",
                            buttonText = "Tentar Novamente",
                            icon = ImageVector.vectorResource(id = R.drawable.homeiconretry),
                            onClick = {}
                        )
                    }
                }

                else -> {
                    item {
                        ListingTitle(
                            image = R.drawable.user_error,
                            space = 10.dp,
                            title = "Gilmar"
                        )
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .padding(
                                    start = 30.dp,
                                    end = 30.dp,
                                    bottom = 60.dp
                                ),
                            verticalArrangement = Arrangement.spacedBy(
                                space = 20.dp
                            )
                        ) {
                            TitleSection(
                                title = "Contato",
                                isRowList = true,
                                hasArrowButton = false
                            )

                            Column(
                                verticalArrangement = Arrangement.spacedBy(
                                    space = 10.dp
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.Bottom,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(
                                            space = 10.dp
                                        )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Email,
                                            contentDescription = "Icone de Email"
                                        )

                                        Text(
                                            text = "gilmar@gmail.com",
                                            style = Typography.bodyLarge,
                                            fontWeight = FontWeight.Normal,
                                            color = PRETO
                                        )
                                    }

                                    Button(
                                        texto = "",
                                        modifier = Modifier
                                            .padding(vertical = 10.dp, horizontal = 10.dp),
                                        hasIcon = true,
                                        icon = Icons.AutoMirrored.Outlined.Send,
                                        shape = RoundedCornerShape(12.dp),
                                        onClick = {},
                                        backgroundColor = VERDE_CLARO,
                                        contentColor = BRANCO,
                                        defaultElevetion = 3.dp,
                                        isClientInformation = true
                                    )
                                }

                                HorizontalDivider(
                                    thickness = 1.dp,
                                    color = PRETO.copy(alpha = 0.3F)
                                )
                            }

                            Column(
                                verticalArrangement = Arrangement.spacedBy(
                                    space = 10.dp
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.Bottom,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(
                                            space = 10.dp
                                        )
                                    ) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(id = R.drawable.whatsapp),
                                            contentDescription = "Icone de Whatsapp"
                                        )

                                        Text(
                                            text = "(13) 99732-2925",
                                            style = Typography.bodyLarge,
                                            fontWeight = FontWeight.Normal,
                                            color = PRETO
                                        )
                                    }

                                    Button(
                                        texto = "",
                                        modifier = Modifier
                                            .padding(vertical = 10.dp, horizontal = 10.dp),
                                        hasIcon = true,
                                        icon = Icons.AutoMirrored.Outlined.Send,
                                        shape = RoundedCornerShape(12.dp),
                                        onClick = {},
                                        backgroundColor = VERDE_CLARO,
                                        contentColor = BRANCO,
                                        defaultElevetion = 3.dp,
                                        isClientInformation = true
                                    )
                                }

                                HorizontalDivider(
                                    thickness = 1.dp,
                                    color = PRETO.copy(alpha = 0.3F)
                                )
                            }
                        }
                    }

                    item {
                        TitleSection(
                            title = "Propriedades",
                            isRowList = false,
                            hasArrowButton = false,
                            bottomSpace = 40.dp
                        )
                    }

                    items(
                        count = propertys.size,
                        key = {
                            propertys[it]
                        }
                    ) {
                        ListingClientCard(
                            modifier = Modifier
                                .padding(
                                    start = 30.dp,
                                    end = 30.dp,
                                    bottom = if (it == (propertys.size - 1)) 60.dp else 10.dp
                                ),
                            onClick = {
                                navigationController.navigate("ClientProperty")
                            },
                            isClientInformation = true,
                            textMaxSize = 16,
                            name = propertys[it]
                        )
                    }
                }
            }
        }
    }
}