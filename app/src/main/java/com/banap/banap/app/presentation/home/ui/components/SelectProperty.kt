package com.banap.banap.app.presentation.home.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.CINZA_INTERMEDIARIO
import com.banap.banap.core.ui.theme.PRETO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO

@Composable
fun SelectProperty(
    name: String,
    id: String,
    onOptionSelected: (String) -> Unit,
    selectedOption: String? = null
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if(id == selectedOption) VERDE_CLARO else BRANCO,
            contentColor = if(id == selectedOption) BRANCO else PRETO
        ),
        border = BorderStroke(
            color = CINZA_INTERMEDIARIO,
            width = 0.5.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .selectable(
                    selected = (id == selectedOption),
                    onClick = {
                        onOptionSelected(id)
                    },
                    role = Role.RadioButton
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        10.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 10.dp
                )
            ) {
                RadioButton(
                    selected = (id == selectedOption),
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = BRANCO,
                        unselectedColor = CINZA_INTERMEDIARIO
                    )
                )

                Text(
                    text = name,
                    style = Typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}