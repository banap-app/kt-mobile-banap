package com.banap.banap.app.presentation.field.ui.producer.information.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.banap.banap.R
import com.banap.banap.core.ui.components.Button
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.VERDE_CLARO
import com.banap.banap.core.ui.theme.VERMELHO

@Composable
fun FieldActions(
    navigationController: NavController,
    onClickDelete: () -> Unit,
    onClickEdit: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            texto = "Editar Talhão",
            modifier = Modifier
                .padding(15.dp),
            hasIcon = true,
            icon = ImageVector.vectorResource(id = R.drawable.fieldiconedit),
            shape = RoundedCornerShape(10.dp),
            onClick = onClickEdit,
            backgroundColor = VERDE_CLARO,
            contentColor = BRANCO,
            defaultElevetion = 3.dp
        )

        Button(
            texto = "",
            modifier = Modifier
                .padding(15.dp),
            hasIcon = true,
            icon = ImageVector.vectorResource(id = R.drawable.fieldicondelete),
            shape = RoundedCornerShape(10.dp),
            onClick = onClickDelete,
            backgroundColor = VERMELHO,
            contentColor = BRANCO,
            defaultElevetion = 3.dp
        )
    }
}