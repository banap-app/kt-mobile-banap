package com.banap.banap.app.presentation.client.ui.listing.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.banap.banap.R
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO

@Composable
fun ListingClientCard(
    modifier: Modifier,
    onClick: () -> Unit = {},
    isClientInformation: Boolean,
    textMaxSize: Int,
    name: String
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = VERDE_CLARO,
            contentColor = BRANCO
        ),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 25.dp,
                    top = 15.dp,
                    end = 30.dp,
                    bottom = 15.dp
                )
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    space = 15.dp
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isClientInformation) {
                    Image(
                        painter = painterResource(id = R.drawable.user_error),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(39.dp)
                    )
                }

                Text(
                    text = if (name.length > textMaxSize) name.take(textMaxSize) + "..." else name,
                    style = Typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    space = 10.dp
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isClientInformation) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = R.drawable.analysisiconliming
                        ),
                        contentDescription = "Icone de Calagem",
                        modifier = Modifier
                            .scale(1f),
                        tint = BRANCO
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = R.drawable.analysisiconnpk
                        ),
                        contentDescription = "Icone de Npk",
                        modifier = Modifier
                            .scale(1f),
                        tint = BRANCO
                    )
                }

                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.field
                    ),
                    contentDescription = "Icone de Talhao",
                    modifier = Modifier
                        .scale(1f),
                    tint = BRANCO
                )
            }
        }
    }
}