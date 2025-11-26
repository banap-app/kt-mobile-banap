package com.banap.banap.app.presentation.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.core.ui.util.clickableText

@Composable
fun TitleSection(
    title: String,
    isRowList: Boolean = true,
    hasSpaceTop: Boolean = false,
    hasArrowButton: Boolean = true,
    bottomSpace: Dp = 20.dp,
    onClickClickableText: (LinkAnnotation) -> Unit = {},
    onClickArrowButton: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(
                start = if (isRowList) 0.dp else 30.dp,
                end = if (isRowList) 0.dp else 30.dp,
                bottom = if (isRowList) 0.dp else bottomSpace,
                top = if (hasSpaceTop) 60.dp else 0.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = clickableText(
                onClick = onClickClickableText,
                text = title
            ),
            style = Typography.titleLarge,
            color = VERDE_ESCURO,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        if (hasArrowButton) {
            IconButton(
                onClick = onClickArrowButton
            ) {
                Icon(
                    Icons.Outlined.KeyboardArrowUp,
                    contentDescription = "Flecha clicavel",
                    modifier = Modifier
                        .rotate(90.0F)
                        .scale(scale = 1.2F),
                    tint = VERDE_ESCURO
                )
            }
        }
    }
}