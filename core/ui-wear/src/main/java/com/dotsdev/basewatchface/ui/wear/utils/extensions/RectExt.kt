package com.dotsdev.basewatchface.ui.wear.utils.extensions

import android.graphics.Path
import android.graphics.Rect

fun Rect.createClockHand(
    length: Float,
    thickness: Float,
    gapBetweenHandAndCenter: Float,
    roundedCornerXRadius: Float,
    roundedCornerYRadius: Float
): Path {
    val width = width()
    val centerX = exactCenterX()
    val centerY = exactCenterY()
    val left = centerX - thickness / 2 * width
    val top = centerY - (gapBetweenHandAndCenter + length) * width
    val right = centerX + thickness / 2 * width
    val bottom = centerY - gapBetweenHandAndCenter * width
    val path = Path()

    if (roundedCornerXRadius != 0.0f || roundedCornerYRadius != 0.0f) {
        path.addRoundRect(
            left,
            top,
            right,
            bottom,
            roundedCornerXRadius,
            roundedCornerYRadius,
            Path.Direction.CW
        )
    } else {
        path.addRect(
            left,
            top,
            right,
            bottom,
            Path.Direction.CW
        )
    }
    return path
}