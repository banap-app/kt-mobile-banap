package com.banap.banap.app.presentation.skeleton.ui.clients.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.banap.banap.app.presentation.skeleton.ui.field.components.CreateDetailsSkeleton
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun ClientFieldInformationGraphicSectionSkeleton() {
    Column(
        modifier = Modifier
            .padding(
                start = 30.dp,
                end = 30.dp,
                top = 20.dp
            ),
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