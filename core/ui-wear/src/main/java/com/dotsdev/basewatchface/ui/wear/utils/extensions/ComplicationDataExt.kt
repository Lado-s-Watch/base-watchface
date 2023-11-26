package com.dotsdev.basewatchface.ui.wear.utils.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import androidx.core.graphics.drawable.toBitmap
import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ShortTextComplicationData
import java.time.Instant


fun ComplicationData.provider(): ComplicationProvider {
    return ComplicationProvider.of(dataSource?.className?.split(".")?.lastOrNull())
}

fun ComplicationData.isBattery(): Boolean = this.provider() == ComplicationProvider.Battery

enum class ComplicationProvider(val provider: String) {
    Battery("BatteryProviderService"), DayAndDate("DayAndDateProviderService"), StepCount("StepsProviderService"), Notification(
        "UnreadNotificationsProviderService"
    ),
    AppShortcut("LauncherProviderService"), Unknown("Unknown");

    companion object {
        fun of(provider: String?): ComplicationProvider =
            entries.find { it.provider == provider } ?: Unknown
    }
}


context(Rect)
fun ShortTextComplicationData.getIconBitmap(
    context: Context, ratio: Float
): MonochromaticImageComplication? {
    val drawable = monochromaticImage?.image?.loadDrawable(context)
    if (drawable != null) {
        val size = (width().coerceAtMost(height()).toFloat() * ratio).toInt()
        return MonochromaticImageComplication(
            icon = drawable.toBitmap(size, size), iconBounds = Rect(0, 0, size, size)
        )
    }
    return null
}

fun ShortTextComplicationData.getText(context: Context): String {
    val now = Instant.now()
    return text.getTextAt(context.resources, now).toString().uppercase()
}

fun ShortTextComplicationData.getTitle(context: Context): String? {
    val now = Instant.now()
    if (title == null || title?.isPlaceholder() == true) return null
    return title?.getTextAt(context.resources, now).toString().uppercase()
}

data class MonochromaticImageComplication(
    val icon: Bitmap, val iconBounds: Rect
)
