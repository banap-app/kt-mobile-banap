package com.banap.banap.app.presentation.skeleton.ui.analysis.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.banap.banap.app.presentation.skeleton.ui.field.components.CreateDetailsSkeleton

@Composable
fun SetAnalysisSkeleton(
    hasCreateDetails: Boolean = true
) {
    if (hasCreateDetails) {
        CreateDetailsSkeleton()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AnalysisDataSkeleton(
            modifier = Modifier
                .width(155.dp)
        )

        AnalysisDataSkeleton(
            modifier = Modifier
                .width(155.dp)
        )
    }

    AnalysisDataSkeleton(
        modifier = Modifier
            .fillMaxWidth()
    )
}