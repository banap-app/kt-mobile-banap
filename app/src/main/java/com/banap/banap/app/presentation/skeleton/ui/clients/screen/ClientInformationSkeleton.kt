package com.banap.banap.app.presentation.skeleton.ui.clients.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.ShapeCarousel
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun ClientInformationSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 30.dp,
                end = 30.dp,
                bottom = 60.dp
            ),
        verticalArrangement = Arrangement.spacedBy(60.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 10.dp
            )
        ) {
            Box(
                modifier = Modifier
                    .clip(
                        shape = CircleShape
                    )
                    .size(60.dp)
                    .shimmerEffect(),
                content = {}
            )

            Box(
                modifier = Modifier
                    .clip(
                        shape = ShapeCarousel.medium
                    )
                    .width(80.dp)
                    .height(20.dp)
                    .shimmerEffect(),
                content = {}
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(
                space = 20.dp
            )
        ) {
            Box(
                modifier = Modifier
                    .clip(
                        shape = CircleShape
                    )
                    .fillMaxWidth(0.5F)
                    .height(30.dp)
                    .shimmerEffect(),
                content = {}
            )

            repeat(times = 2) {
                Box(
                    modifier = Modifier
                        .clip(
                            shape = ShapeCarousel.medium
                        )
                        .fillMaxWidth()
                        .height(25.dp)
                        .shimmerEffect(),
                    content = {}
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(
                space = 40.dp
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

            Column (
                verticalArrangement = Arrangement.spacedBy(
                    space = 10.dp
                )
            ) {
                repeat(times = 5) {
                    Box(
                        modifier = Modifier
                            .clip(
                                shape = ShapeCarousel.medium
                            )
                            .fillMaxWidth()
                            .height(50.dp)
                            .shimmerEffect(),
                        content = {}
                    )
                }
            }
        }
    }
}