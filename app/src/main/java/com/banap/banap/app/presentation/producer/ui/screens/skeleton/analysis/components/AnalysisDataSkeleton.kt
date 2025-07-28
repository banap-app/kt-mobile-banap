package com.banap.banap.app.presentation.producer.ui.screens.skeleton.analysis.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun AnalysisDataSkeleton(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(20.dp)
            )
            .height(80.dp)
            .shimmerEffect(),
        content = {}
    )
}