package com.banap.banap.app.presentation.skeleton.ui.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.banap.banap.core.ui.theme.ShapeProperty
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun SettingsSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 30.dp
                ),
            verticalArrangement = Arrangement.spacedBy(
                space = 40.dp
            )
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 15.dp
                )
            ) {
                Box(
                    modifier = Modifier
                        .clip(
                            shape = ShapeProperty.medium
                        )
                        .height(20.dp)
                        .fillMaxWidth(0.6f)
                        .shimmerEffect(),
                    content = {}
                )

                Box(
                    modifier = Modifier
                        .clip(
                            shape = ShapeProperty.medium
                        )
                        .height(50.dp)
                        .fillMaxWidth()
                        .shimmerEffect(),
                    content = {}
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(
                    space = 15.dp
                )
            ) {
                Box(
                    modifier = Modifier
                        .clip(
                            shape = ShapeProperty.medium
                        )
                        .height(20.dp)
                        .fillMaxWidth(0.6f)
                        .shimmerEffect(),
                    content = {}
                )

                Box(
                    modifier = Modifier
                        .clip(
                            shape = ShapeProperty.medium
                        )
                        .height(50.dp)
                        .fillMaxWidth()
                        .shimmerEffect(),
                    content = {}
                )
            }
        }

        Box(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(0.dp)
                )
                .height(98.dp)
                .fillMaxWidth()
                .shimmerEffect(),
            content = {}
        )
    }
}