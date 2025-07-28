package com.banap.banap.app.presentation.producer.ui.screens.analysis.information.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.banap.banap.R
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO

@Composable
fun AnalysisCard(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    fillMaxWidth: Boolean = false,
    child: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(
                shape = RoundedCornerShape(20.dp)
            )
            .background(VERDE_CLARO)
    ) {
        if (!fillMaxWidth) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.analysisimagecardbottomsmall),
                contentDescription = "Imagem de fundo do card",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            )

            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.analysisimagecardtopsmall),
                contentDescription = "Imagem de fundo do card",
                modifier = Modifier
                    .align(Alignment.TopStart)
            )
        } else {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.analysisimagecardbottombigger),
                contentDescription = "Imagem de fundo do card",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            )

            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.analysisimagecardtopbigger),
                contentDescription = "Imagem de fundo do card",
                modifier = Modifier
                    .align(Alignment.TopStart)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(
                    horizontal = 20.dp,
                    vertical = 15.dp
                ),
            verticalArrangement = Arrangement.spacedBy(
                space = 4.dp
            )
        ) {
            Text(
                text = title,
                style = Typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = BRANCO
            )

            Text(
                text = subTitle,
                style = Typography.displaySmall,
                color = BRANCO
            )
        }

        Row (
            modifier = Modifier
                .padding(
                    end = 20.dp
                )
                .align(Alignment.CenterEnd),
        ) {
            child()
        }
    }
}