package com.banap.banap.app.presentation.skeleton.ui.field.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.ShapeCarousel
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun ActionsSkeleton() {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(
                    shape = ShapeCarousel.medium
                )
                .width(174.dp)
                .height(51.dp)
                .shimmerEffect(),
            content = {}
        )

        Box(
            modifier = Modifier
                .clip(
                    shape = ShapeCarousel.medium
                )
                .width(51.dp)
                .height(51.dp)
                .shimmerEffect(),
            content = {}
        )
    }
}