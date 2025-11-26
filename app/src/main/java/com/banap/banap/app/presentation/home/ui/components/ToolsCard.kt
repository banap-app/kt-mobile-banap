package com.banap.banap.app.presentation.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO

@Composable
fun ToolsCard(
    hasPaddingStart: Boolean = false,
    hasPaddingEnd: Boolean = false,
    name: String,
    icon: Int,
    iconSize: Float = 2f,
    space: Dp = 15.dp,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .clip(
                shape = ShapeProperty.medium
            )
            .padding(
                start = if (hasPaddingStart) 30.dp else 0.dp,
                end = if (hasPaddingEnd) 30.dp else 0.dp,
                top = 15.dp
            )
            .height(100.dp)
            .width(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = VERDE_CLARO
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                space = space,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = icon
                ),
                contentDescription = "Icone de Calagem",
                modifier = Modifier
                    .scale(iconSize),
                tint = BRANCO
            )

            Text(
                text = name,
                style = Typography.displaySmall,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                color = BRANCO
            )
        }
    }
}