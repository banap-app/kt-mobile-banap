package com.banap.banap.app.presentation.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.CINZA_CLARO
import com.banap.banap.core.ui.theme.CINZA_INTERMEDIARIO
import com.banap.banap.core.ui.theme.ShapeProperty

@Composable
fun NewFieldCard (
    onClick: () -> Unit
) {
    Card (
        onClick = onClick,
        modifier = Modifier
            .clip(
                shape = ShapeProperty.medium
            )
            .padding(top = 35.dp)
            .height(178.dp)
            .width(124.dp),
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
                Icons.Outlined.Add,
                contentDescription = "Icone de adicionar novo talhão",
                modifier = Modifier
                    .scale(scale = 2F),
                tint = CINZA_INTERMEDIARIO
            )
        }
    }
}