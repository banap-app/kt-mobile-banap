package com.banap.banap.app.presentation.home.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.banap.banap.R
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO

@Composable
fun ClientCard(
    name: String,
    hasPaddingStart: Boolean = false,
    onClick: () -> Unit = {}
) {
    Card (
        onClick = onClick,
        modifier = Modifier
            .clip(
                shape = ShapeProperty.medium
            )
            .padding(
                start = if (hasPaddingStart) 30.dp else 0.dp,
                top = 15.dp
            )
            .height(122.dp)
            .width(177.dp),
        colors = CardDefaults.cardColors(
            containerColor = VERDE_CLARO
        ),
    ) {
        Column (
            modifier = Modifier
                .padding(
                    start = 15.dp,
                    top = 15.dp,
                    end = 20.dp,
                    bottom = 20.dp
                )
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                space = 20.dp
            )
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row (
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 10.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.user_error),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(30.dp)
                    )

                    Column {
                        Text(
                            text = if (name.length > 9) name.take(9) + "..." else name,
                            style = Typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = BRANCO,
                            softWrap = false,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = "Bananudo",
                            style = Typography.displayMedium,
                            color = BRANCO
                        )
                    }
                }

                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = "Icone de menu do cliente",
                    modifier = Modifier
                        .rotate(
                            degrees = 90F
                        ),
                    tint = BRANCO
                )
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = 5.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "10+",
                        style = Typography.bodySmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = BRANCO
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = R.drawable.analysisiconliming
                        ),
                        contentDescription = "Icone de Calagem",
                        modifier = Modifier
                            .scale(1.3f),
                        tint = BRANCO
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = 5.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "5+",
                        style = Typography.bodySmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = BRANCO
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = R.drawable.analysisiconnpk
                        ),
                        contentDescription = "Icone de Npk",
                        modifier = Modifier
                            .scale(1.3f),
                        tint = BRANCO
                    )
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = 5.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "1k+",
                        style = Typography.bodySmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = BRANCO
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = R.drawable.field
                        ),
                        contentDescription = "Icone de Talhao",
                        modifier = Modifier
                            .scale(1.3f),
                        tint = BRANCO
                    )
                }
            }
        }
    }
}