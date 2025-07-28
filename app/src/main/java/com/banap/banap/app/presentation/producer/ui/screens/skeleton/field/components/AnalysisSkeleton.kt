package com.banap.banap.app.presentation.producer.ui.screens.skeleton.field.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun AnalysisSkeleton() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(30.dp)
                )
                .fillMaxWidth(0.9f)
                .height(117.dp)
                .shimmerEffect(),
            content = {}
        )
    }
}