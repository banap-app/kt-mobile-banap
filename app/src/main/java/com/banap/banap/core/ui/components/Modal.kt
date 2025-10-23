package com.banap.banap.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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

@Composable
fun Modal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    icon: ImageVector,
    iconColor: Color,
    title: String,
    description: String,
    disableOnConfirmButton: Boolean = false,
    onConfirmText: String,
    onConfirmButtonBackgroundColor: Color,
    onConfirmButtonContentColor: Color,
    onDismissText: String = "Cancelar",
    onDismissButtonBackgroundColor: Color = CINZA_INTERMEDIARIO,
    onDismissButtonContentColor: Color = PRETO,
    hasDismissButton: Boolean = true,
    space: Dp = 40.dp,
    child: @Composable () -> Unit = {}
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
                            top = 25.dp,
                            bottom = if (description.isNotEmpty()) 40.dp else 0.dp
                        )
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = 20.dp
                    )
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Icone do Modal",
                        modifier = Modifier
                            .size(30.dp),
                        tint = iconColor
                    )

                    Text(
                        text = title,
                        style = Typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = PRETO,
                        textAlign = TextAlign.Center
                    )

                    if (description.isNotEmpty()) {
                        Text(
                            text = description,
                            style = Typography.bodySmall,
                            fontWeight = FontWeight.Normal,
                            color = PRETO,
                            textAlign = TextAlign.Justify
                        )
                    }
                }

                child()

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
                        disableButton = disableOnConfirmButton,
                        onClick = onConfirm,
                        backgroundColor = onConfirmButtonBackgroundColor,
                        contentColor = onConfirmButtonContentColor,
                        defaultElevetion = 0.dp
                    )

                    if (hasDismissButton) {
                        Button(
                            texto = onDismissText,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            hasIcon = false,
                            shape = ShapeProperty.small,
                            onClick = onDismiss,
                            backgroundColor = onDismissButtonBackgroundColor,
                            contentColor = onDismissButtonContentColor,
                            defaultElevetion = 0.dp,
                            style = Typography.bodyLarge,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}