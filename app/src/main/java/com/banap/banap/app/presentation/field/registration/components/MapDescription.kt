package com.banap.banap.app.presentation.field.ui.registration.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.Typography

@Composable
fun MapDescription () {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Localização",
            style = Typography.titleMedium
        )

        Text(
            text = "Você deve clicar em pelo menos 3 pontos do mapa para que uma área seja delimitada, demonstrando assim, a localização do talhão.",
            style = Typography.displaySmall,
            textAlign = TextAlign.Justify
        )
    }
}