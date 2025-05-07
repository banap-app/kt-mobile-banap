package com.banap.banap.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.AZUL_ESCURO
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownTextField(
    label: String,
    value: String,
    placeholder: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(
                text = label,
                style = Typography.titleMedium,
                color = PRETO
            )

            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = {
                    isExpanded = it
                }
            ) {
                OutlinedTextField(
                    value = value,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                        unfocusedContainerColor = VERDE_CLARO,
                        focusedContainerColor = VERDE_CLARO,
                        unfocusedTrailingIconColor = BRANCO,
                        focusedTrailingIconColor = BRANCO,
                        unfocusedTextColor = BRANCO,
                        focusedTextColor = BRANCO,
                        focusedPlaceholderColor = BRANCO,
                        unfocusedPlaceholderColor = BRANCO,
                        focusedBorderColor = AZUL_ESCURO,
                        unfocusedBorderColor = BRANCO
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    placeholder = {
                        Text(
                            text = placeholder,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    shape = RoundedCornerShape(7.dp),
                    textStyle = Typography.headlineSmall
                )

                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = {
                        isExpanded = false
                    },
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(7.dp)
                        )
                        .background(BRANCO)
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                onOptionSelected(option)
                                isExpanded = false
                            },
                            text = {
                                Text(
                                    text = option,
                                    style = Typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    color = VERDE_CLARO
                                )
                            }
                        )
                    }

                }
            }
        }
    }
}