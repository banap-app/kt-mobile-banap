package com.banap.banap.app.presentation.analysis.ui.registration.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.banap.banap.R
import com.banap.banap.core.ui.theme.BRANCO
import com.banap.banap.core.ui.theme.Typography
import com.banap.banap.core.ui.theme.VERDE_CLARO

@Composable
fun AnalysisResult(
    text: String,
    children: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            space = 40.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(
                    max(263.dp, 263.dp)
                )
                .height(
                    max(220.dp, 220.dp)
                ),
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.analysisicon),
                contentDescription = "Icone de Análise feita",
                modifier = Modifier
                    .align(Alignment.BottomStart)
            )

            Card (
                modifier = Modifier
                    .width(
                        max(200.dp, 200.dp)
                    )
                    .align(Alignment.TopEnd),
                colors = CardDefaults.cardColors(
                    containerColor = VERDE_CLARO,
                    contentColor = BRANCO
                )
            ) {
                Text(
                    text = text,
                    style = Typography.displaySmall,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(13.dp)
                )
            }
        }

        Column (
            modifier = Modifier
                .padding(
                    horizontal = 30.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 20.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
           children()
        }
    }
}