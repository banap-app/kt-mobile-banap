package com.banap.banap.app.presentation.producer.ui.screens.skeleton.field.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun DescriptionSkeleton(
    lines: Int
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 5.dp
        )
    ) {
        repeat(lines) {
            LineSkeleton()
        }
    }
}