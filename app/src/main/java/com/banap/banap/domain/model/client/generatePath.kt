package com.banap.banap.domain.model.client

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path

fun generatePath(
    data: List<Float>,
    size: Size
): Path {
    val path = Path()
    if (data.isEmpty()) return path

    val spacing = size.width / (data.size - 1)
    val maxY = data.maxOrNull() ?: 0f
    val minY = data.minOrNull() ?: 0f
    val rangeY = (maxY - minY).takeIf { it != 0f } ?: 1f

    val points = data.mapIndexed { i, value ->
        val x = spacing * i
        val y = size.height - ((value - minY) / rangeY) * size.height
        Offset(x, y)
    }

    path.moveTo(points.first().x, points.first().y)

    for (i in 1 until points.size) {
        val prev = points[i - 1]
        val current = points[i]

        val midX = (prev.x + current.x) / 2f

        path.cubicTo(
            midX, prev.y,
            midX, current.y,
            current.x, current.y
        )
    }

    return path
}