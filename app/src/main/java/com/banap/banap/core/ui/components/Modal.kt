package com.banap.banap.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_INTERMEDIARIO
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERMELHO

@Composable
fun Modal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    icon: Int,
    title: String,
    description: String,
    onConfirmText: String,
    onDismissText: String,
    space: Dp = 40.dp
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            tonalElevation = 6.dp,
            modifier = modifier
                .padding(horizontal = 50.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            color = BRANCO
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = space
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            start = 20.dp,
                            end = 20.dp,
                            top = 25.dp
                        )
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 20.dp
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = icon),
                        contentDescription = "Icone do Modal",
                        modifier = Modifier
                            .size(30.dp),
                        tint = VERMELHO
                    )

                    Text(
                        text = title,
                        style = Typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = PRETO,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = description,
                        style = Typography.bodySmall,
                        fontWeight = FontWeight.Normal,
                        color = PRETO,
                        textAlign = TextAlign.Justify
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(
                            start = 20.dp,
                            end = 20.dp,
                            bottom = 25.dp
                        )
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 5.dp
                    )
                ) {
                    Button(
                        texto = onConfirmText,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        hasIcon = false,
                        shape = ShapeProperty.small,
                        onClick = onConfirm,
                        backgroundColor = VERMELHO,
                        contentColor = BRANCO,
                        defaultElevetion = 0.dp
                    )

                    Button(
                        texto = onDismissText,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        hasIcon = false,
                        shape = ShapeProperty.small,
                        onClick = onDismiss,
                        backgroundColor = CINZA_INTERMEDIARIO,
                        contentColor = PRETO,
                        defaultElevetion = 0.dp,
                        style = Typography.bodyLarge,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}