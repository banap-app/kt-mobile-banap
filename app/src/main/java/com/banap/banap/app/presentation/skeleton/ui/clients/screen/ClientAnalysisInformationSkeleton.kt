package com.banap.banap.app.presentation.skeleton.ui.clients.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.banap.banap.app.presentation.skeleton.ui.analysis.components.SetAnalysisSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.ButtonSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.DescriptionSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.InformationSkeleton
import com.banap.banap.core.ui.theme.ShapeCarousel
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun ClientAnalysisInformationSkeleton() {
    Column(
        modifier = Modifier
            .padding(
                bottom = 60.dp
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            space = 60.dp
        )
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

        InformationSkeleton {
            DescriptionSkeleton(
                lines = 3
            )
        }

        InformationSkeleton {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 40.dp
                )
            ) {
                DescriptionSkeleton(
                    lines = 3
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        space = 20.dp
                    )
                ) {
                    SetAnalysisSkeleton()

                    DescriptionSkeleton(
                        lines = 3
                    )
                }
            }
        }

        InformationSkeleton {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 40.dp
                )
            ) {
                DescriptionSkeleton(
                    lines = 3
                )

                Column (
                    verticalArrangement = Arrangement.spacedBy(
                        space = 20.dp
                    )
                ) {
                    SetAnalysisSkeleton(
                        hasCreateDetails = false
                    )
                }

                ButtonSkeleton(
                    horizontalArrangement = Arrangement.Start
                )
            }
        }
    }
}