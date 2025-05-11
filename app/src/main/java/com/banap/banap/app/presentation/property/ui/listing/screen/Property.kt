package com.banap.banap.app.presentation.property.ui.listing.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.presentation.property.ui.listing.components.Container
import com.banap.banap.core.ui.components.ListItemCard
import com.banap.banap.core.ui.components.Button
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO

@Composable
fun Property(
    navigationController: NavController
) {
    val isLoading by remember {
        mutableStateOf(false)
    }

    val propertyList = mutableListOf<String>(
        "Talhão 01",
        "Talhão 02",
        "Talhão 03",
        "Talhão 04",
        "Talhão 05"
    )

    Container(
        navigationController = navigationController,
        titulo = "Propriedade 01",
        children = {
            when {
                propertyList.isEmpty() && !isLoading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            space = 40.dp,
                            alignment = Alignment.CenterVertically
                        )
                    ) {
                        Text(
                            text = "Ainda não há um\ntalhão cadastrado!",
                            textAlign = TextAlign.Center,
                            style = Typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = CINZA_ESCURO
                        )

                        Button(
                            texto = "Novo Talhão",
                            modifier = Modifier
                                .padding(vertical = 18.dp, horizontal = 15.dp),
                            hasIcon = true,
                            shape = ShapeProperty.small,
                            onClick = {
                                propertyList.add("Talhao 01")
                            },
                            backgroundColor = VERDE_CLARO,
                            contentColor = BRANCO,
                            defaultElevetion = 3.dp
                        )
                    }
                }

                else -> {
                    LazyColumn (
                        modifier = Modifier
                            .padding(
                                start = 30.dp,
                                end = 30.dp,
                                bottom = 30.dp
                            )
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(propertyList.size) {
                            ListItemCard(
                                modifier = Modifier
                                    .height(150.dp)
                                    .fillMaxWidth(),
                                title = "Talhão",
                                titleStyle = Typography.titleMedium,
                                nameStyle = Typography.displayLarge,
                                name = propertyList[it]
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
        },
        buttonValue = "Novo Talhão",
        isLoading = isLoading,
        propertyList = propertyList
    )
}
