package com.banap.banap.app.presentation.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.CINZA_ESCURO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.data.model.producer.LogList

@Composable
fun RecentActivities(
    title: String,
    list: MutableList<LogList>
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            space = 20.dp
        )
    ) {
        Text(
            text = title,
            style = Typography.titleLarge,
            color = VERDE_ESCURO
        )

        when {
            list.isEmpty() -> {
                Activities(
                    icone = Icons.Outlined.Info,
                    autor = "Você",
                    atividade = "não realizou nenhuma atividade recentemente."
                )
            }

            else -> {
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = 10.dp
                    )
                ) {
                    list.take(5).forEach { activity ->
                        Activities(
                            autor = activity.author,
                            atividade = activity.activity
                        )
                    }
                }

                Text(
                    text = "10 de Maio 2025 ás 17:54",
                    style = Typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = CINZA_ESCURO
                )
            }
        }
    }
}