package com.banap.banap.app.presentation.skeleton.ui.field.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.ShapeCarousel
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun ImageSkeleton() {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 13.dp
        )
    ) {
        Box(
            modifier = Modifier
                .clip(
                    shape = ShapeCarousel.medium
                )
                .fillMaxWidth()
                .height(270.dp)
                .shimmerEffect(),
            content = {}
        )

        Box(
            modifier = Modifier
                .clip(
                    shape = ShapeCarousel.medium
                )
                .fillMaxWidth()
                .height(20.dp)
                .shimmerEffect(),
            content = {}
        )
    }
}