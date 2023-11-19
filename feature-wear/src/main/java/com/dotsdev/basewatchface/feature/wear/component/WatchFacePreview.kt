package com.dotsdev.basewatchface.feature.wear.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import com.dotsdev.basewatchface.ui.wear.utils.ComplicationConfig

@Composable
fun WatchFacePreview(bitmap: ImageBitmap, modifier: Modifier = Modifier) {
    val aspectRatio = bitmap.width.toFloat() / bitmap.height
    Box(
        modifier = modifier.aspectRatio(aspectRatio),
    ) {
        Image(
            bitmap = bitmap,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (LocalConfiguration.current.isScreenRound) {
                        Modifier.clip(CircleShape)
                    } else {
                        Modifier
                    },
                ),
        )
        //ComplicationConfig.
    }
}