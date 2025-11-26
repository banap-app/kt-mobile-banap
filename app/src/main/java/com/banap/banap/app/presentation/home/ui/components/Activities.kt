package com.banap.banap.app.presentation.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.util.setColorInText

@Composable
fun Activities(
    icone: ImageVector = Icons.Outlined.Person,
    autor: String,
    atividade: String,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 10.dp
        )
    ) {
        Icon(
            imageVector = icone,
            contentDescription = "Icone de pessoa"
        )

        Text(
            text = setColorInText(
                texto = atividade,
                textoASerDestacado = "$autor ",
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                corEmDestaque = VERDE_CLARO,
                ordemInversa = true
            ),
            style = Typography.bodyLarge,
            fontWeight = FontWeight.Normal
        )
    }
}