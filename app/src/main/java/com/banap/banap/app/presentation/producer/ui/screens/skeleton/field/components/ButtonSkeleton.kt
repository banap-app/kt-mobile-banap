package com.banap.banap.app.presentation.producer.ui.screens.skeleton.field.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun ButtonSkeleton(
    horizontalArrangement: Arrangement.Horizontal = Arrangement.End
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = horizontalArrangement
    ) {
        Box(
            modifier = Modifier
                .clip(
                    shape = ShapeProperty.medium
                )
                .height(60.dp)
                .fillMaxWidth(0.5f)
                .shimmerEffect(),
            content = {}
        )
    }
}