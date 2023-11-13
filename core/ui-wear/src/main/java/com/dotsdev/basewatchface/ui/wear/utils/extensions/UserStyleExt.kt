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
import com.dotsdev.basewatchface.ui.wear.utils.COLOR_STYLE_SETTING
import com.dotsdev.basewatchface.ui.wear.utils.DRAW_HOUR_PIPS_STYLE_SETTING
import com.dotsdev.basewatchface.ui.wear.utils.SHOW_COMPLICATIONS_IN_AMBIENT_STYLE_SETTING
import com.dotsdev.basewatchface.ui.wear.utils.WATCH_HAND_LENGTH_STYLE_SETTING

context(UserStyleSchema)
@OptIn(ExperimentalHierarchicalStyle::class)
fun UserStyle.getShowComplicationsInAmbient(): Boolean {
    val id = UserStyleSetting.Id(SHOW_COMPLICATIONS_IN_AMBIENT_STYLE_SETTING)
    val showComplicationsInAmbientSetting = rootUserStyleSettings.find { it.id == id }
            as UserStyleSetting.BooleanUserStyleSetting
    return (this[showComplicationsInAmbientSetting]
            as UserStyleSetting.BooleanUserStyleSetting.BooleanOption).value
}

context(UserStyleSchema)
@OptIn(ExperimentalHierarchicalStyle::class)
fun UserStyle.getColorStyle(): String {
    val id = UserStyleSetting.Id(COLOR_STYLE_SETTING)
    val colorStyleSetting = rootUserStyleSettings.find { it.id == id }
            as UserStyleSetting.ListUserStyleSetting
    return (this[colorStyleSetting]
            as UserStyleSetting.ListUserStyleSetting.ListOption).id.toString()
}

context(UserStyleSchema)
@OptIn(ExperimentalHierarchicalStyle::class)
fun UserStyle.getTickEnabledStyle(): Boolean {
    val id = UserStyleSetting.Id(DRAW_HOUR_PIPS_STYLE_SETTING)
    val styleSetting = rootUserStyleSettings.find { it.id == id }
            as UserStyleSetting.BooleanUserStyleSetting
    return (this[styleSetting]
            as UserStyleSetting.BooleanUserStyleSetting.BooleanOption).value
}

context(UserStyleSchema)
@OptIn(ExperimentalHierarchicalStyle::class)
fun UserStyle.getMinuteHandStyle(): Double {
    val id = UserStyleSetting.Id(WATCH_HAND_LENGTH_STYLE_SETTING)
    val styleSetting = rootUserStyleSettings.find { it.id == id }
            as UserStyleSetting.DoubleRangeUserStyleSetting
    return (this[styleSetting]
            as UserStyleSetting.DoubleRangeUserStyleSetting.DoubleRangeOption).value
}
