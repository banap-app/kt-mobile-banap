package com.banap.banap.app.presentation.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.util.setColorInText

@Composable
fun Activities(
    autor: String,
    atividade: String
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 10.dp
        )
    ) {
        Icon(
            Icons.Outlined.Person,
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