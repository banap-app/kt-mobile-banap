package com.banap.banap.app.presentation.skeleton.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.ShapeCarousel
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun LogListSkeleton() {
    Column(
        modifier = Modifier
            .padding(
                horizontal = 30.dp
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            space = 20.dp
        )
    ) {
        Box(
            modifier = Modifier
                .clip(
                    shape = ShapeCarousel.medium
                )
                .fillMaxWidth()
                .height(30.dp)
                .shimmerEffect(),
            content = {}
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 10.dp
            )
        ) {
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

            Box(
                modifier = Modifier
                    .clip(
                        shape = ShapeCarousel.medium
                    )
                    .fillMaxWidth(0.5f)
                    .height(15.dp)
                    .shimmerEffect(),
                content = {}
            )

            Box(
                modifier = Modifier
                    .clip(
                        shape = ShapeCarousel.medium
                    )
                    .fillMaxWidth(0.8f)
                    .height(15.dp)
                    .shimmerEffect(),
                content = {}
            )
        }

        Box(
            modifier = Modifier
                .clip(
                    shape = ShapeCarousel.medium
                )
                .fillMaxWidth(0.4f)
                .height(10.dp)
                .shimmerEffect(),
            content = {}
        )
    }
}