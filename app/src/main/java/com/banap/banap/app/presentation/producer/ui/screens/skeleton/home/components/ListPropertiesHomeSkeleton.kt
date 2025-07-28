package com.banap.banap.app.presentation.producer.ui.screens.skeleton.home.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.ShapeCarousel
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun ListPropertiesHomeSkeleton() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 60.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(
                space = 20.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .clip(
                        shape = ShapeCarousel.medium
                    )
                    .fillMaxWidth()
                    .height(156.dp)
                    .shimmerEffect(),
                content = {}
            )

            Box(
                modifier = Modifier
                    .clip(
                        shape = ShapeCarousel.medium
                    )
                    .width(64.dp)
                    .height(8.dp)
                    .shimmerEffect(),
                content = {}
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(
                space = 35.dp
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

            Row(
                modifier = Modifier
                    .horizontalScroll(
                        state = scrollState,
                        enabled = false
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 25.dp
                )
            ) {
                Box(
                    modifier = Modifier
                        .clip(
                            shape = ShapeProperty.medium
                        )
                        .height(178.dp)
                        .width(124.dp)
                        .shimmerEffect(),
                    content = {}
                )

                Box(
                    modifier = Modifier
                        .clip(
                            shape = ShapeProperty.medium
                        )
                        .height(178.dp)
                        .width(124.dp)
                        .shimmerEffect(),
                    content = {}
                )

                Box(
                    modifier = Modifier
                        .clip(
                            shape = ShapeProperty.medium
                        )
                        .height(178.dp)
                        .width(124.dp)
                        .shimmerEffect(),
                    content = {}
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Box(
                modifier = Modifier
                    .clip(
                        shape = ShapeProperty.medium
                    )
                    .height(60.dp)
                    .fillMaxWidth(0.7f)
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

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(
                space = 40.dp
            )
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 5.dp
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

                Box(
                    modifier = Modifier
                        .clip(
                            shape = ShapeCarousel.medium
                        )
                        .fillMaxWidth(0.7f)
                        .height(20.dp)
                        .shimmerEffect(),
                    content = {}
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 60.dp
                )
            ) {
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
                            .fillMaxWidth(0.5f)
                            .height(15.dp)
                            .shimmerEffect(),
                        content = {}
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            space = 5.dp
                        )
                    ) {
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

                        Box(
                            modifier = Modifier
                                .clip(
                                    shape = ShapeProperty.medium
                                )
                                .height(50.dp)
                                .fillMaxWidth()
                                .shimmerEffect(),
                            content = {}
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .clip(
                                shape = ShapeProperty.medium
                            )
                            .height(60.dp)
                            .fillMaxWidth(0.7f)
                            .shimmerEffect(),
                        content = {}
                    )
                }
            }
        }
    }
}