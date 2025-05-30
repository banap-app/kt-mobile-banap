package com.banap.banap.app.presentation.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.app.navigation.screens.Screen
import com.banap.banap.app.presentation.skeleton.ui.home.components.ListFieldsHomeSkeleton
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.core.ui.util.clickableText
import com.banap.banap.data.model.field.FieldResponse
import com.banap.banap.domain.model.field.ListFieldsState

@Composable
fun Property(
    navigationController: NavController,
    titulo: String,
    propertyId: String,
    producerId: String,
    fields: List<FieldResponse>,
    fieldsState: ListFieldsState,
    withSpacing: Boolean,
) {
    var error: String by remember {
        mutableStateOf("")
    }

    var loading: Boolean by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(fieldsState.error) {
        error = fieldsState.error
    }

    LaunchedEffect(fieldsState.isLoading) {
        loading = fieldsState.isLoading
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = clickableText(
                    onClick = {
                        navigationController.navigate(
                            Screen.Property.createRoute(
                                name = titulo,
                                propertyId = propertyId,
                                producerId = producerId
                            )
                        )
                    },
                    text = titulo
                ),
                style = Typography.titleLarge,
                color = VERDE_ESCURO
            )

            IconButton(
                onClick = {
                    navigationController.navigate(
                        Screen.Property.createRoute(
                            name = titulo,
                            propertyId = propertyId,
                            producerId = producerId
                        )
                    )
                }
            ) {
                Icon(
                    Icons.Outlined.KeyboardArrowUp,
                    contentDescription = "Flecha clicavel",
                    modifier = Modifier
                        .rotate(90.0F)
                        .scale(scale = 1.2F),
                    tint = VERDE_ESCURO
                )
            }

        }

        when {
            loading -> {
                ListFieldsHomeSkeleton()
            }

            fields.isNotEmpty() -> {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 25.dp
                    )
                ) {
                    items(
                        count = fields.take(5).size,
                        key = {
                            fields[it].id
                        }
                    ) {
                        FieldCard(
                            nomeTalhao = fields[it].name,
                            onClick = {
                                navigationController.navigate(
                                    Screen.Information.createRoute(
                                        fieldId = fields[it].id
                                    )
                                )
                            }
                        )
                    }

                    item {
                        NewFieldCard(
                            onClick = {
                                navigationController.navigate(
                                    Screen.NewField.createRoute(
                                        producerId = producerId,
                                        propertyId = propertyId
                                    )
                                )
                            }
                        )
                    }
                }
            }

            error.isNotEmpty() -> {
                Column (
                    modifier = Modifier
                        .padding(
                            top = 35.dp
                        )
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 20.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_wifi_off_24),
                        contentDescription = "Sem conexão com a internet",
                        modifier = Modifier
                            .scale(1.4f)
                    )

                    Text(
                        text = "Ocorreu um erro ao\n carregar os campos...",
                        textAlign = TextAlign.Center,
                        style = Typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = CINZA_ESCURO
                    )
                }
            }

            else -> {
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 25.dp
                    )
                ) {
                    NewFieldCard(
                        onClick = {
                            navigationController.navigate(
                                Screen.NewField.createRoute(
                                    producerId = producerId,
                                    propertyId = propertyId
                                )
                            )
                        }
                    )
                }
            }
        }
    }

    if (withSpacing) {
        Spacer(modifier = Modifier.height(40.dp))
    }
}