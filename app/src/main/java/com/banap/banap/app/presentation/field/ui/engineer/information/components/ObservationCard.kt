package com.banap.banap.app.presentation.field.ui.engineer.information.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.banap.banap.R
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERDE_ESCURO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObservationCard(
    modifier: Modifier = Modifier,
    isObservationDropdownExpanded: MutableState<Boolean>,
    observationTitle: String,
    observationDescription: String
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isObservationDropdownExpanded.value,
        onExpandedChange = {
            isObservationDropdownExpanded.value = it
        }
    ) {
        Card(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .menuAnchor(),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = BRANCO,
                contentColor = VERDE_ESCURO
            ),
            border = BorderStroke(
                width = 1.dp,
                color = PRETO.copy(
                    alpha = 0.2f
                )
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .padding(
                            start = 10.dp,
                            top = 10.dp,
                            bottom = 10.dp
                        ),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 10.dp
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = R.drawable.pin
                        ),
                        contentDescription = "Icone de Pin"
                    )

                    Text(
                        text = observationTitle,
                        style = Typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = VERDE_CLARO
                    )
                }

                Card(
                    modifier = Modifier
                        .size(40.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = VERDE_CLARO,
                        contentColor = BRANCO
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = isObservationDropdownExpanded.value,
                            modifier = Modifier
                                .scale(1.5f)
                        )
                    }
                }
            }
        }

        ExposedDropdownMenu(
            expanded = isObservationDropdownExpanded.value,
            onDismissRequest = {
                isObservationDropdownExpanded.value = false
            },
            modifier = Modifier
                .clip(
                    RoundedCornerShape(7.dp)
                )
                .background(BRANCO)
        ) {
            DropdownMenuItem(
                onClick = {
                    isObservationDropdownExpanded.value = false
                },
                text = {
                    Text(
                        text = observationDescription,
                        style = Typography.bodySmall,
                        textAlign = TextAlign.Justify,
                        fontWeight = FontWeight.Normal,
                        color = PRETO,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }
            )
        }
    }
}