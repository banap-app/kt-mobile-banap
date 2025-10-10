package com.banap.banap.app.presentation.skeleton.ui.field.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.banap.banap.app.presentation.skeleton.ui.field.components.ActionsSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.AnalysisSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.ButtonSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.CreateDetailsSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.DescriptionSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.ImageSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.InformationSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.LineSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.TaskSkeleton

@Composable
fun FieldInformationSkeleton() {
    Column(
        modifier = Modifier
            .padding(
                bottom = 60.dp
            ),
        verticalArrangement = Arrangement.spacedBy(
            space = 60.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 30.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 20.dp
            )
        ) {
            ImageSkeleton()

            ActionsSkeleton()
        }

        InformationSkeleton(
            space = 20.dp
        ) {
            DescriptionSkeleton(
                lines = 3
            )
        }

        InformationSkeleton(
            space = 40.dp
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 60.dp
                )
            ) {
                TaskSkeleton()

                ButtonSkeleton()
            }
        }

        InformationSkeleton(
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

                    ButtonSkeleton()
                }
            }
        }
    }
}