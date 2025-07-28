package com.banap.banap.app.presentation.producer.ui.screens.skeleton.analysis.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.banap.banap.app.presentation.producer.ui.screens.skeleton.analysis.components.AnalysisDataSkeleton
import com.banap.banap.app.presentation.producer.ui.screens.skeleton.analysis.components.SetAnalysisSkeleton
import com.banap.banap.app.presentation.producer.ui.screens.skeleton.field.components.ButtonSkeleton
import com.banap.banap.app.presentation.producer.ui.screens.skeleton.field.components.DescriptionSkeleton
import com.banap.banap.app.presentation.producer.ui.screens.skeleton.field.components.ImageSkeleton
import com.banap.banap.app.presentation.producer.ui.screens.skeleton.field.components.InformationSkeleton

@Composable
fun AnalysisInformationSkeleton() {
    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 20.dp
        )
    ) {
        ImageSkeleton()
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

            AnalysisDataSkeleton(
                modifier = Modifier
                    .fillMaxWidth()
            )
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

            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 20.dp
                )
            ) {
                SetAnalysisSkeleton()
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

            ButtonSkeleton(
                horizontalArrangement = Arrangement.Start
            )
        }
    }
}