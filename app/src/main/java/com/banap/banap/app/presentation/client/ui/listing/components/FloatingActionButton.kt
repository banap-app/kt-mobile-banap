package com.banap.banap.app.presentation.client.ui.listing.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.components.Button
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.theme.VERDE_CLARO

@Composable
fun FloatingActionButton(
    buttonText: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(
                start = 45.dp,
                end = 15.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Button(
            texto = buttonText,
            modifier = Modifier
                .padding(
                    vertical = 18.dp,
                    horizontal = 15.dp
                ),
            hasIcon = true,
            shape = ShapeProperty.small,
            onClick = onClick,
            backgroundColor = BRANCO,
            contentColor = VERDE_CLARO,
            defaultElevetion = 3.dp
        )
    }
}