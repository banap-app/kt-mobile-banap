package com.banap.banap.app.presentation.field.ui.engineer.information.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERMELHO

@Composable
fun BreakdownByQuarter(
    text: String,
    color: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = 5.dp
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Card(
                modifier = Modifier
                    .align(
                        alignment = Alignment.Center
                    )
                    .height(2.dp)
                    .width(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = color
                ),
                content = {}
            )

            Card(
                modifier = Modifier
                    .align(
                        alignment = Alignment.Center
                    )
                    .size(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = color
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = BRANCO
                ),
                content = {}
            )
        }

        Text(
            text = text,
            style = Typography.displaySmall,
            color = PRETO
        )
    }
}