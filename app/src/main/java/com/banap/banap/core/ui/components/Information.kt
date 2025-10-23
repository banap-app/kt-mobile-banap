package com.banap.banap.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_ESCURO

@Composable
fun Information (
    space: Dp = 20.dp,
    paddingTop: Dp = 0.dp,
    icon: Int,
    title: String,
    child: @Composable () -> Unit = {}
) {
    Column (
        modifier = Modifier
            .padding(
                top = paddingTop,
                start = 30.dp,
                end = 30.dp
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            space = space
        )
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(
                space = 10.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = icon),
                contentDescription = "Icone de Informação",
                tint = VERDE_ESCURO
            )

            Text(
                text = title,
                style = Typography.titleMedium,
                color = VERDE_ESCURO,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Justify
            )
        }

        child()
    }
}