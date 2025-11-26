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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.ShapeCarousel
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun ListingClientsSkeleton(
    isClientProperty: Boolean = false,
    times: Int = 10,
    height: Dp = 69.dp,
    shape: Dp = 15.dp
) {
    Column(
        modifier = Modifier
            .padding(
                start = 30.dp,
                end = 30.dp,
                bottom = 60.dp
            ),
        verticalArrangement = Arrangement.spacedBy(60.dp)
    ) {
        if (isClientProperty) {
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
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 10.dp
            )
        ) {
            repeat(times = times) {
                Box(
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(shape)
                        )
                        .fillMaxWidth()
                        .height(height)
                        .shimmerEffect(),
                    content = {}
                )
            }
        }
    }
}