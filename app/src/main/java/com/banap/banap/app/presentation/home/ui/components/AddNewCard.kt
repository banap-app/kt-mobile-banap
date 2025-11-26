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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_INTERMEDIARIO
import com.banap.banap.core.ui.theme.ShapeProperty

@Composable
fun AddNewCard (
    height: Dp,
    width: Dp,
    icon: ImageVector,
    isEngineerHome: Boolean = false,
    onClick: () -> Unit = {},
) {
    Card (
        onClick = onClick,
        modifier = Modifier
            .clip(
                shape = ShapeProperty.medium
            )
            .padding(
                end = 30.dp,
                top = if (isEngineerHome) 15.dp else 35.dp,
            )
            .height(height)
            .width(width),
        colors = CardDefaults.cardColors(
            containerColor = CINZA_CLARO
        ),
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Icone de adicionar novo dado",
                tint = CINZA_INTERMEDIARIO
            )
        }
    }
}