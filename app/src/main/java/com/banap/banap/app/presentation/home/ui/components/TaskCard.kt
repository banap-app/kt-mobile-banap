package com.banap.banap.app.presentation.home.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_INTERMEDIARIO
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.Typography

@Composable
fun TaskCard(
    cardColor: Color,
    priority: String,
    name: String,
    time: String
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = BRANCO
        ),
        border = BorderStroke(
            color = CINZA_INTERMEDIARIO,
            width = 0.5.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    end = 25.dp,
                    top = 10.dp,
                    bottom = 10.dp
                )
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    space = 15.dp
                )
            ) {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = cardColor,
                        contentColor = BRANCO
                    )
                ) {
                    Text(
                        text = priority,
                        style = Typography.displaySmall,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier
                            .padding(
                                horizontal = 17.dp,
                                vertical = 7.dp
                            )
                    )
                }

                Text(
                    text = name,
                    style = Typography.bodyLarge,
                    fontWeight = FontWeight.Normal,
                    color = PRETO
                )
            }

            Text(
                text = time,
                style = Typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = CINZA_INTERMEDIARIO
            )
        }
    }
}