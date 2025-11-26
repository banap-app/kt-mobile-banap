package com.banap.banap.app.presentation.skeleton.ui.engineerHome

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
fun ListClientsHomeSkeleton(
    times: Int = 3
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .padding(
                bottom = 60.dp
            )
            .horizontalScroll(
                state = scrollState,
                enabled = true
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 25.dp
        )
    ) {
        repeat(times = times) {
            Box(
                modifier = Modifier
                    .padding(
                        start = if (it == 0) 30.dp else 0.dp,
                        end = if (it == (times - 1)) 30.dp else 0.dp,
                    )
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