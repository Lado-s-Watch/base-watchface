package com.dotsdev.basewatchface.ui.wear.ext

import android.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.wear.watchface.DrawMode
import androidx.wear.watchface.RenderParameters
import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.editor.EditorSession
import androidx.wear.watchface.style.ExperimentalHierarchicalStyle
import androidx.wear.watchface.style.UserStyle
import androidx.wear.watchface.style.UserStyleSchema
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.WatchFaceLayer

context(UserStyleSchema)
@OptIn(ExperimentalHierarchicalStyle::class)
fun UserStyle.getShowComplicationsInAmbient(): Boolean {
    val id = UserStyleSetting.Id("show_complications_in_ambient")
    val showComplicationsInAmbientSetting = rootUserStyleSettings.find { it.id == id }
            as UserStyleSetting.BooleanUserStyleSetting
    return (this[showComplicationsInAmbientSetting]
            as UserStyleSetting.BooleanUserStyleSetting.BooleanOption).value
}

context(UserStyleSchema)
@OptIn(ExperimentalHierarchicalStyle::class)
fun UserStyle.getColorStyle(): String {
    val id = UserStyleSetting.Id("color_style")
    val colorStyleSetting = rootUserStyleSettings.find { it.id == id }
            as UserStyleSetting.ListUserStyleSetting
    return (this[colorStyleSetting]
            as UserStyleSetting.ListUserStyleSetting.ListOption).id.toString()
}

context(UserStyleSchema)
@OptIn(ExperimentalHierarchicalStyle::class)
fun UserStyle.getTickEnabledStyle(): Boolean {
    val id = UserStyleSetting.Id("tick_enabled_style")
    val styleSetting = rootUserStyleSettings.find { it.id == id }
            as UserStyleSetting.BooleanUserStyleSetting
    return (this[styleSetting]
            as UserStyleSetting.BooleanUserStyleSetting.BooleanOption).value
}

context(UserStyleSchema)
@OptIn(ExperimentalHierarchicalStyle::class)
fun UserStyle.getMinuteHandStyle(): Double {
    val id = UserStyleSetting.Id("minute_hand_style")
    val styleSetting = rootUserStyleSettings.find { it.id == id }
            as UserStyleSetting.DoubleRangeUserStyleSetting
    return (this[styleSetting]
            as UserStyleSetting.DoubleRangeUserStyleSetting.DoubleRangeOption).value
}

context(UserStyleSchema)
fun EditorSession.createWatchFacePreview(
    complicationsPreviewData: Map<Int, ComplicationData>
): ImageBitmap {
    return this.renderWatchFaceToBitmap(
        RenderParameters(
            DrawMode.INTERACTIVE,
            WatchFaceLayer.ALL_WATCH_FACE_LAYERS,
            RenderParameters.HighlightLayer(
                RenderParameters.HighlightedElement.AllComplicationSlots,
                Color.RED, // Red complication highlight.
                Color.argb(128, 0, 0, 0) // Darken everything else.
            )
        ),
        this.previewReferenceInstant,
        complicationsPreviewData
    ).asImageBitmap()
}