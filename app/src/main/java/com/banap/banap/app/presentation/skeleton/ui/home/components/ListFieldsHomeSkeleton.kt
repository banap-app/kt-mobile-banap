package com.banap.banap.app.presentation.skeleton.ui.home.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun ListFieldsHomeSkeleton() {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .padding(top = 35.dp)
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