package com.dotsdev.basewatchface.ui.wear.utils.extensions

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
import com.dotsdev.basewatchface.ui.wear.UserStyles
import com.dotsdev.basewatchface.ui.wear.utils.COLOR_STYLE_SETTING
import com.dotsdev.basewatchface.ui.wear.utils.DRAW_HOUR_PIPS_STYLE_SETTING
import com.dotsdev.basewatchface.ui.wear.utils.SHOW_COMPLICATIONS_IN_AMBIENT_STYLE_SETTING
import com.dotsdev.basewatchface.ui.wear.utils.WATCH_HAND_LENGTH_STYLE_SETTING

context(UserStyleSchema)
@OptIn(ExperimentalHierarchicalStyle::class)
fun EditorSession.setUserStyleOption(
    userStyles: UserStyles
): UserStyle {
    val mutableUserStyle = this.userStyle.value.toMutableUserStyle()
    // 1. showComplicationsInAmbientSetting
    val showComplicationsInAmbientSetting = rootUserStyleSettings.find {
        it.id == UserStyleSetting.Id(SHOW_COMPLICATIONS_IN_AMBIENT_STYLE_SETTING)
    } as UserStyleSetting.BooleanUserStyleSetting

    mutableUserStyle[showComplicationsInAmbientSetting] =
        UserStyleSetting.BooleanUserStyleSetting.BooleanOption.from(userStyles.showComplicationsInAmbient)

    // 2. colorStyleSetting
    val colorStyleSetting = rootUserStyleSettings.find {
        it.id == UserStyleSetting.Id(COLOR_STYLE_SETTING)
    } as UserStyleSetting.ListUserStyleSetting
    colorStyleSetting.options.find { it.id.toString() == userStyles.colorStyleId }?.let { options ->
        mutableUserStyle[colorStyleSetting] = options
    }

    // 3. minuteHandLengthStyleSetting
    val newMinuteHandLengthRatio = userStyles.minuteHandLength.toDouble() / 1000f
    val minuteHandLengthStyleSetting = rootUserStyleSettings.find {
        it.id == UserStyleSetting.Id(WATCH_HAND_LENGTH_STYLE_SETTING)
    } as UserStyleSetting.DoubleRangeUserStyleSetting

    mutableUserStyle[minuteHandLengthStyleSetting] =
        UserStyleSetting.DoubleRangeUserStyleSetting.DoubleRangeOption(newMinuteHandLengthRatio)

    // 4. showComplicationsInAmbientSetting
    val tickEnableSetting = rootUserStyleSettings.find {
        it.id == UserStyleSetting.Id(DRAW_HOUR_PIPS_STYLE_SETTING)
    } as UserStyleSetting.BooleanUserStyleSetting

    mutableUserStyle[tickEnableSetting] =
        UserStyleSetting.BooleanUserStyleSetting.BooleanOption.from(userStyles.ticksEnabled)

    return mutableUserStyle.toUserStyle()
}

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