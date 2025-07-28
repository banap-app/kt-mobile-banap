package com.banap.banap.app.presentation.producer.ui.screens.skeleton.field.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.ShapeCarousel
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun LineSkeleton() {
    Box(
        modifier = Modifier
            .clip(
                shape = ShapeCarousel.medium
            )
            .fillMaxWidth()
            .height(10.dp)
            .shimmerEffect(),
        content = {}
    )
}