package com.banap.banap.app.presentation.producer.ui.screens.skeleton.field.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.ShapeCarousel
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun CreateDetailsSkeleton() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = 10.dp
        )
    ) {
        Box(
            modifier = Modifier
                .clip(
                    shape = CircleShape
                )
                .size(30.dp)
                .shimmerEffect(),
            content = {}
        )

        Box(
            modifier = Modifier
                .clip(
                    shape = ShapeCarousel.medium
                )
                .fillMaxWidth(0.7f)
                .height(15.dp)
                .shimmerEffect(),
            content = {}
        )
    }
}