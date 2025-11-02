package com.banap.banap.app.presentation.client.ui.listing.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_ESCURO

@Composable
fun ListingTitle(
    icon: ImageVector? = null,
    image: Int? = null,
    space: Dp,
    title: String
) {
    Column(
        modifier = Modifier
            .padding(
                bottom = 60.dp
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            space = space,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = "Icone de propriedade",
                tint = VERDE_ESCURO,
                modifier = Modifier
                    .scale(1.5f)
            )
        }

        image?.let {
            Image(
                painter = painterResource(id = image),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(55.dp)
            )
        }

        Text(
            text = title,
            style = Typography.titleLarge,
            color = VERDE_ESCURO,
            textAlign = TextAlign.Center
        )
    }
}