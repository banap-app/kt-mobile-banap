package com.banap.banap.app.presentation.skeleton.ui.field.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.ShapeCarousel
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun InformationSkeleton(
    space: Dp = 20.dp,
    isEngineerHome: Boolean = false,
    child: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                horizontal = if (isEngineerHome) 0.dp else 30.dp
            ),
        verticalArrangement = Arrangement.spacedBy(
            space = space
        )
    ) {
        Box(
            modifier = Modifier
                .clip(
                    shape = ShapeCarousel.medium
                )
                .fillMaxWidth(0.6f)
                .height(25.dp)
                .shimmerEffect(),
            content = {}
        )

        child()
    }
}