package com.banap.banap.app.presentation.skeleton.ui.field.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun TaskSkeleton() {
    Box(
        modifier = Modifier
            .clip(
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxWidth()
            .height(50.dp)
            .shimmerEffect(),
        content = {}
    )
}