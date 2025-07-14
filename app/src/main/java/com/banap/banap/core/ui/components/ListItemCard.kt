package com.banap.banap.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.VERDE_CLARO

@Composable
fun ListItemCard(
    modifier: Modifier,
    onClick: () -> Unit = {},
    title: String,
    titleStyle: TextStyle,
    name: String,
    nameStyle: TextStyle,
    child: @Composable () -> Unit
) {
    Card (
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = VERDE_CLARO,
            contentColor = BRANCO
        ),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Row (
            modifier = Modifier
                .padding(
                    start = 30.dp
                )
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    style = titleStyle,
                    fontWeight = FontWeight.Normal
                )

                Text(
                    text = if (name.length > 9) name.substring(0, 9) + "..." else name,
                    style = nameStyle,
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis
                )
            }

            child()
        }
    }
}