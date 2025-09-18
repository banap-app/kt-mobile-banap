package com.banap.banap.app.presentation.skeleton.ui.home.components

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
fun HeaderHomeSkeleton() {
    Box(
        modifier = Modifier
            .clip(
                shape = ShapeCarousel.medium
            )
            .fillMaxWidth()
            .height(40.dp)
            .shimmerEffect(),
        content = {}
    )
}