package com.banap.banap.app.presentation.skeleton.ui.clients.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.banap.banap.app.presentation.skeleton.ui.field.components.AnalysisSkeleton
import com.banap.banap.app.presentation.skeleton.ui.field.components.CreateDetailsSkeleton

@Composable
fun ClientFieldInformationAnalysisSectionSkeleton(
    times: Int = 2
) {
    Column(
        modifier = Modifier
            .padding(
                start = 30.dp,
                end = 30.dp,
                bottom = 60.dp
            ),
        verticalArrangement = Arrangement.spacedBy(
            space = 10.dp
        )
    ) {
        CreateDetailsSkeleton()

        repeat(times = times) {
            AnalysisSkeleton()
        }
    }
}