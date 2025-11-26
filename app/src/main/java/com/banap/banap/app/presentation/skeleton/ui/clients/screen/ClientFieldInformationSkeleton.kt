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
import androidx.compose.ui.unit.dp
import com.banap.banap.app.presentation.skeleton.ui.field.components.AnalysisSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.ButtonSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.CreateDetailsSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.DescriptionSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.ImageSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.InformationSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.LineSkeleton
import com.banap.banap.core.ui.theme.ShapeCarousel
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun ClientFieldInformationSkeleton() {
    Column(
        modifier = Modifier
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
            verticalArrangement = Arrangement.spacedBy(
                space = 20.dp
            )
        ) {
            ImageSkeleton()
        }

        InformationSkeleton(
            isEngineerHome = true,
            space = 20.dp
        ) {
            DescriptionSkeleton(
                lines = 3
            )
        }

        InformationSkeleton(
            isEngineerHome = true,
            space = 20.dp
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(
                    space = 10.dp
                )
            ) {
                CreateDetailsSkeleton(
                    profilePicture = false,
                    dataSize = 0.5f
                )

                Box(
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(5.dp)
                        )
                        .fillMaxWidth()
                        .height(250.dp)
                        .shimmerEffect(),
                    content = {}
                )
            }
        }

        InformationSkeleton(
            isEngineerHome = true,
            space = 40.dp
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 10.dp
                )
            ) {
                CreateDetailsSkeleton(
                    profilePicture = false,
                    dataSize = 0.5f
                )

                Box(
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(5.dp)
                        )
                        .fillMaxWidth()
                        .height(131.dp)
                        .shimmerEffect(),
                    content = {}
                )
            }

            ButtonSkeleton()
        }

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