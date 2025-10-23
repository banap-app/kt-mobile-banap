package com.banap.banap.app.presentation.skeleton.ui.home.screen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.banap.banap.app.presentation.skeleton.ui.field.components.AnalysisSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.CreateDetailsSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.InformationSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.LineSkeleton
import com.banap.banap.core.ui.theme.ShapeCarousel
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun HomeSkeleton(
    padding: Dp = 30.dp,
    isEngineerHome: Boolean = false
) {
    val scrollState = rememberScrollState()

    Column(
        modifier =
        if (isEngineerHome) {
            Modifier
                .fillMaxSize()
                .padding(
                    start = 30.dp,
                    end = 30.dp,
                    top = padding + 10.dp,
                    bottom = 60.dp
                )
        } else {
            Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(
                    start = 30.dp,
                    end = 30.dp,
                    top = padding + 30.dp,
                    bottom = 60.dp
                )
        },
        verticalArrangement = Arrangement.spacedBy(
            space = 60.dp
        )
    ) {
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

            Box(
                modifier = Modifier
                    .clip(
                        shape = ShapeCarousel.medium
                    )
                    .fillMaxWidth()
                    .height(10.dp)
                    .shimmerEffect(),
                content = {}
            )
        }

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
                if (!isEngineerHome) {
                    repeat(times = 3) {
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
                } else {
                    repeat(times = 3) {
                        Box(
                            modifier = Modifier
                                .clip(
                                    shape = ShapeProperty.medium
                                )
                                .height(122.dp)
                                .width(177.dp)
                                .shimmerEffect(),
                            content = {}
                        )
                    }
                }
            }
        }

        if (isEngineerHome) {
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
                        space = 15.dp
                    )
                ) {
                    repeat(times = 3) {
                        Box(
                            modifier = Modifier
                                .clip(
                                    shape = ShapeProperty.medium
                                )
                                .height(100.dp)
                                .width(100.dp)
                                .shimmerEffect(),
                            content = {}
                        )
                    }
                }
            }
        }

        if (!isEngineerHome) {
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

        if (isEngineerHome) {
            InformationSkeleton(
                isEngineerHome = true,
                space = 5.dp
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = 40.dp
                    )
                ) {
                    LineSkeleton()

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
                            CreateDetailsSkeleton()

                            AnalysisSkeleton()
                        }
                    }
                }
            }
        }

    }
}