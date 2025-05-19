package com.banap.banap.app.presentation.skeleton.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.banap.banap.R
import com.banap.banap.core.ui.theme.ShapeCarousel
import com.banap.banap.core.ui.util.shimmerEffect

@Composable
fun WeatherDataHomeSkeleton() {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 12.dp
        )
    ) {
        Box(
            modifier = Modifier
                .clip(
                    shape = ShapeCarousel.medium
                )
                .width(80.dp)
                .height(25.dp)
                .shimmerEffect(),
            content = {}
        )

        Box(
            modifier = Modifier
                .clip(
                    shape = ShapeCarousel.medium
                )
                .width(140.dp)
                .height(15.dp)
                .shimmerEffect(),
            content = {}
        )

        Column {
            Box(
                modifier = Modifier
                    .clip(
                        shape = ShapeCarousel.medium
                    )
                    .width(80.dp)
                    .height(10.dp)
                    .shimmerEffect(),
                content = {}
            )
        }
    }

    Icon(
        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_cloud_24),
        contentDescription = "Erro no carregamento",
        modifier = Modifier
            .size(70.dp),
        tint = Color(0xFFB8B5B5)
    )
}