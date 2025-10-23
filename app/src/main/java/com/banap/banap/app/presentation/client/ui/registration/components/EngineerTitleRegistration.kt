package com.banap.banap.app.presentation.client.ui.registration.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.core.ui.util.setColorInText

@Composable
fun EngineerTitleRegistration(
    title: String,
    highlightedTitle: String,
    description: String
) {
    Column(
        modifier = Modifier
            .padding(
                start = 30.dp,
                end = 30.dp,
                bottom = 60.dp
            ),
        verticalArrangement = Arrangement.spacedBy(
            space = 10.dp
        )
    ) {
        Text(
            text = setColorInText(
                texto = "$title ",
                textoASerDestacado = "${highlightedTitle}...",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 36.sp,
                corEmDestaque = VERDE_ESCURO,
                ordemInversa = false
            ),
            style = Typography.titleLarge
        )

        Text(
            text = description,
            style = Typography.bodyLarge,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Justify
        )
    }
}