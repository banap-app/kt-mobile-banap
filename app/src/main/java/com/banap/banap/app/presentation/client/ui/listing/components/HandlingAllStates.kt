package com.banap.banap.app.presentation.client.ui.listing.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.components.Button
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO

@Composable
fun HandlingAllStates(
    modifier: Modifier = Modifier,
    text: String,
    buttonText: String = "Agregar Cliente",
    icon: ImageVector = Icons.Outlined.Add,
    hasButton: Boolean = true,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 40.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = Typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = CINZA_ESCURO
        )

        if (hasButton) {
            Button(
                texto = buttonText,
                modifier = Modifier
                    .padding(vertical = 18.dp, horizontal = 15.dp),
                hasIcon = true,
                icon = icon,
                shape = ShapeProperty.small,
                onClick = onClick,
                backgroundColor = VERDE_CLARO,
                contentColor = BRANCO,
                defaultElevetion = 3.dp
            )
        }
    }
}