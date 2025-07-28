package com.banap.banap.app.presentation.analysis.ui.registration.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO

@Composable
fun ResultCard(
    nutrient: String,
    result: String
) {
    Box (
        modifier = Modifier
            .height(85.dp)
    ) {
        Card (
            modifier = Modifier
                .align(Alignment.TopCenter),
            shape = RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = VERDE_CLARO,
                contentColor = BRANCO
            )
        ) {
            Text(
                text = nutrient,
                style = Typography.labelSmall,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = 7.dp,
                        bottom = 21.dp
                    )
            )
        }

        Card (
            modifier = Modifier
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = BRANCO,
                contentColor = VERDE_CLARO
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )
        ) {
            Text(
                text = result,
                style = Typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier
                    .padding(
                        vertical = 10.dp,
                        horizontal = 10.dp
                    )
            )
        }
    }
}