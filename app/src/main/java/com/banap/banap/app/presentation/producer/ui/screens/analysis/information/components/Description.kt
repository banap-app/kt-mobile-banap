package com.banap.banap.app.presentation.producer.ui.screens.analysis.information.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.Typography

@Composable
fun Description(
    text: String,
    color: Color = PRETO,
    style: TextStyle = Typography.bodyLarge
) {
    Text(
        text = text,
        style = style,
        color = color,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Justify
    )
}