package com.banap.banap.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.Typography

@Composable
fun Button(
    texto: String,
    modifier: Modifier,
    hasIcon: Boolean,
    icon: ImageVector = Icons.Outlined.Add,
    shape: Shape,
    onClick: () -> Unit,
    backgroundColor: Color,
    contentColor: Color,
    defaultElevetion: Dp,
    style: TextStyle = Typography.bodyMedium,
    fontWeight: FontWeight = FontWeight.ExtraBold
) {
    Card (
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = defaultElevetion
        ),
        onClick = onClick
    ) {
        Row (
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (hasIcon) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Icone de adicionar nova propriedade",
                    modifier = Modifier
                        .scale(scale = 1.2F)
                )

                if (texto.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }

            Text(
                text = texto,
                style = style,
                fontWeight = fontWeight
            )
        }
    }
}