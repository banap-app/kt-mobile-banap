package com.banap.banap.app.presentation.analysis.ui.producer.information.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.banap.banap.R
import com.banap.banap.core.ui.theme.CINZA_INTERMEDIARIO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO

@Composable
fun CreateDetails(
    modifier: Modifier = Modifier,
    alignment: Alignment.Vertical = Alignment.CenterVertically,
    image: Int = R.drawable.user_error,
    size: Dp = 45.dp,
    username: String? = null,
    createdAt: String
) {
    Row (
        horizontalArrangement = Arrangement.spacedBy(
            space = 10.dp
        ),
        verticalAlignment = alignment
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(size)
        )

        Column (
            verticalArrangement = Arrangement.spacedBy(
                space = 1.dp
            )
        ) {
            Text(
                text = username ?: "Usuário",
                style = Typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = VERDE_CLARO
            )

            Text(
                text = createdAt,
                style = Typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = CINZA_INTERMEDIARIO
            )
        }
    }
}