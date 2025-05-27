package com.banap.banap.app.presentation.home.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.app.navigation.screens.Screen
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.core.ui.util.clickableText

@Composable
fun Property(
    titulo: String,
    navigationController: NavController,
    propertyId: String,
    producerId: String,
    fieldList: MutableList<String>
) {
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
                    navigationController = navigationController,
                    route = "Property",
                    text = titulo
                ),
                style = Typography.titleLarge,
                color = VERDE_ESCURO
            )

            IconButton(
                onClick = {
                    navigationController.navigate("Property")
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

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = 25.dp
            )
        ) {
            when {
                fieldList.isNotEmpty() -> {
                    fieldList.forEach {
                        FieldCard(
                            nomeTalhao = it,
                            navigationController
                        )
                    }
                }
            }

            NewFieldCard(
                onClick = {
                    navigationController.navigate(Screen.NewField.createRoute(
                        producerId = producerId,
                        propertyId = propertyId
                    ))
                }
            )
        }
    }
}