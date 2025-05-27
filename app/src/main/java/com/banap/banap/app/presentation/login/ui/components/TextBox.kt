package com.banap.banap.app.presentation.login.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.banap.banap.R
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_ESCURO
import com.banap.banap.core.ui.theme.VERMELHO

@Composable
fun TextBox(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    maxLines: Int,
    keyboardType: KeyboardType,
    icon: Int?,
    iconColor: Color,
    placeholder: String,
    passwordTextBox: Boolean,
    label: String,
    labelTextStyle: TextStyle,
    labelColor: Color,
    isError: Boolean,
    lastOne: Boolean = false,
    informationIcon: Boolean = false,
    informationIconOnClick: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        var open by remember {
            mutableStateOf(false)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (informationIcon) Arrangement.SpaceBetween else Arrangement.Start
        ) {
            Text(
                text = label,
                style = labelTextStyle,
                color = labelColor
            )

            if (informationIcon) {
                IconButton(
                    onClick = informationIconOnClick
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "Icone de Informação",
                        modifier = Modifier
                            .size(22.dp)
                    )
                }

            }
        }


        if (icon != null) {

            if (passwordTextBox) {
                TextField(
                    value = value,
                    onValueChange,
                    modifier,
                    maxLines = maxLines,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VERDE_ESCURO,
                        cursorColor = VERDE_ESCURO,
                        focusedContainerColor = BRANCO,
                        unfocusedContainerColor = BRANCO,
                        unfocusedTextColor = VERDE_ESCURO,
                        focusedTextColor = VERDE_ESCURO,
                        unfocusedPlaceholderColor = VERDE_ESCURO,
                        focusedPlaceholderColor = VERDE_ESCURO,
                        errorTextColor = VERMELHO,
                        errorPlaceholderColor = VERMELHO,
                        errorCursorColor = VERMELHO
                    ),
                    shape = RoundedCornerShape(0.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = if (!lastOne) ImeAction.Next else ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        },
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    leadingIcon = {
                        Image(
                            imageVector = ImageVector.vectorResource(id = icon),
                            contentDescription = "Icone do TextBox",
                            colorFilter = ColorFilter.tint(iconColor),
                            modifier = Modifier
                                .scale(1.2F)
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                open = !open
                            }
                        ) {
                            Image(
                                imageVector = ImageVector.vectorResource(id = if (!open) R.drawable.eye_slashed else R.drawable.eye_open),
                                contentDescription = "Icone de visão",
                                modifier = Modifier
                                    .scale(1.4F)
                            )
                        }
                    },
                    placeholder = {
                        Text(placeholder)
                    },
                    visualTransformation = if (!open) PasswordVisualTransformation() else VisualTransformation.None,
                    textStyle = Typography.bodySmall,
                    isError = isError
                )
            } else {
                TextField(
                    value = value,
                    onValueChange,
                    modifier,
                    maxLines = maxLines,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VERDE_ESCURO,
                        cursorColor = VERDE_ESCURO,
                        focusedContainerColor = BRANCO,
                        unfocusedContainerColor = BRANCO,
                        unfocusedTextColor = VERDE_ESCURO,
                        focusedTextColor = VERDE_ESCURO,
                        unfocusedPlaceholderColor = VERDE_ESCURO,
                        focusedPlaceholderColor = VERDE_ESCURO,
                        errorTextColor = VERMELHO,
                        errorPlaceholderColor = VERMELHO,
                        errorCursorColor = VERMELHO
                    ),
                    shape = RoundedCornerShape(0.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = if (!lastOne) ImeAction.Next else ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        },
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    leadingIcon = {
                        Image(
                            imageVector = ImageVector.vectorResource(id = icon),
                            contentDescription = "Icone do TextBox",
                            colorFilter = ColorFilter.tint(iconColor),
                            modifier = Modifier
                                .scale(1.2F)
                        )
                    },
                    placeholder = {
                        Text(placeholder)
                    },
                    textStyle = Typography.bodySmall,
                    isError = isError
                )
            }

        } else {

            if (passwordTextBox) {
                TextField(
                    value = value,
                    onValueChange,
                    modifier,
                    maxLines = maxLines,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VERDE_ESCURO,
                        cursorColor = VERDE_ESCURO,
                        focusedContainerColor = BRANCO,
                        unfocusedContainerColor = BRANCO,
                        unfocusedTextColor = VERDE_ESCURO,
                        focusedTextColor = VERDE_ESCURO,
                        unfocusedPlaceholderColor = VERDE_ESCURO,
                        focusedPlaceholderColor = VERDE_ESCURO,
                        errorTextColor = VERMELHO,
                        errorPlaceholderColor = VERMELHO,
                        errorCursorColor = VERMELHO
                    ),
                    shape = RoundedCornerShape(0.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = if (!lastOne) ImeAction.Next else ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        },
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    placeholder = {
                        Text(placeholder)
                    },
                    textStyle = Typography.bodySmall,
                    isError = isError,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                open = !open
                            }
                        ) {
                            Image(
                                imageVector = ImageVector.vectorResource(id = if (!open) R.drawable.eye_slashed else R.drawable.eye_open),
                                contentDescription = "Icone de visão",
                                modifier = Modifier
                                    .scale(1.4F)
                            )
                        }
                    },
                    visualTransformation = if (!open) PasswordVisualTransformation() else VisualTransformation.None,
                )
            } else {
                TextField(
                    value = value,
                    onValueChange,
                    modifier,
                    maxLines = maxLines,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = VERDE_ESCURO,
                        cursorColor = VERDE_ESCURO,
                        focusedContainerColor = BRANCO,
                        unfocusedContainerColor = BRANCO,
                        unfocusedTextColor = VERDE_ESCURO,
                        focusedTextColor = VERDE_ESCURO,
                        unfocusedPlaceholderColor = VERDE_ESCURO,
                        focusedPlaceholderColor = VERDE_ESCURO,
                        errorTextColor = VERMELHO,
                        errorPlaceholderColor = VERMELHO,
                        errorCursorColor = VERMELHO
                    ),
                    shape = RoundedCornerShape(0.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = if (!lastOne) ImeAction.Next else ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        },
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    placeholder = {
                        Text(placeholder)
                    },
                    textStyle = Typography.bodySmall,
                    isError = isError
                )
            }

        }
    }
}