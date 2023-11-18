package com.dotsdev.basewatchface.ui.wear.utils

import android.content.Context
import androidx.wear.watchface.style.UserStyleSchema
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.WatchFaceLayer
import com.dotsdev.basewatchface.ui.R
import com.dotsdev.basewatchface.ui.wear.resources.ColorStyleId
import com.dotsdev.basewatchface.ui.wear.resources.DRAW_HOUR_PIPS_DEFAULT
import com.dotsdev.basewatchface.ui.wear.resources.MINUTE_HAND_LENGTH_FRACTION_DEFAULT
import com.dotsdev.basewatchface.ui.wear.resources.MINUTE_HAND_LENGTH_FRACTION_MAXIMUM
import com.dotsdev.basewatchface.ui.wear.resources.MINUTE_HAND_LENGTH_FRACTION_MINIMUM

// Keys to matched content in the  the user style settings. We listen for changes to these
// values in the renderer and if new, we will update the database and update the watch face
// being rendered.
const val COLOR_STYLE_SETTING = "color_style_setting"
const val DRAW_HOUR_PIPS_STYLE_SETTING = "draw_hour_pips_style_setting"
const val WATCH_HAND_LENGTH_STYLE_SETTING = "watch_hand_length_style_setting"
const val SHOW_COMPLICATIONS_IN_AMBIENT_STYLE_SETTING = "show_complications_in_ambient_style_setting"

/*
 * Creates user styles in the settings activity associated with the watch face, so users can
 * edit different parts of the watch face. In the renderer (after something has changed), the
 * watch face listens for a flow from the watch face API data layer and updates the watch face.
 */
fun createUserStylesSchema(context: Context): UserStyleSchema {
    // 1. Allows user to change the color styles of the watch face (if any are available).
    val colorStyleSetting =
        UserStyleSetting.ListUserStyleSetting(
            UserStyleSetting.Id(COLOR_STYLE_SETTING),
            context.resources,
            R.string.colors_style_setting,
            R.string.colors_style_setting_description,
            null,
            ColorStyleId.toOptionList(context),
            listOf(
                WatchFaceLayer.BASE,
                WatchFaceLayer.COMPLICATIONS,
                WatchFaceLayer.COMPLICATIONS_OVERLAY
            )
        )

    // 2. Allows user to toggle on/off the hour pips (dashes around the outer edge of the watch
    // face).
    val drawHourPipsStyleSetting = UserStyleSetting.BooleanUserStyleSetting(
        UserStyleSetting.Id(DRAW_HOUR_PIPS_STYLE_SETTING),
        context.resources,
        R.string.watchface_pips_setting,
        R.string.watchface_pips_setting_description,
        null,
        listOf(WatchFaceLayer.BASE),
        DRAW_HOUR_PIPS_DEFAULT
    )

    // 3. Allows user to change the length of the minute hand.
    val watchHandLengthStyleSetting = UserStyleSetting.DoubleRangeUserStyleSetting(
        UserStyleSetting.Id(WATCH_HAND_LENGTH_STYLE_SETTING),
        context.resources,
        R.string.watchface_hand_length_setting,
        R.string.watchface_hand_length_setting_description,
        null,
        MINUTE_HAND_LENGTH_FRACTION_MINIMUM.toDouble(),
        MINUTE_HAND_LENGTH_FRACTION_MAXIMUM.toDouble(),
        listOf(WatchFaceLayer.COMPLICATIONS_OVERLAY),
        MINUTE_HAND_LENGTH_FRACTION_DEFAULT.toDouble()
    )

    // 4. Allows user to toggle on/off show complications in ambient mode.
    val showComplicationsInAmbientSetting = UserStyleSetting.BooleanUserStyleSetting(
        UserStyleSetting.Id(SHOW_COMPLICATIONS_IN_AMBIENT_STYLE_SETTING),
        context.resources,
        R.string.watchface_complications_setting_in_ambient,
        R.string.watchface_complications_setting_in_ambient,
        null,
        listOf(WatchFaceLayer.BASE),
        true
    )

    // 5. Create style settings to hold all options.
    return UserStyleSchema(
        listOf(
            colorStyleSetting,
            drawHourPipsStyleSetting,
            watchHandLengthStyleSetting,
            showComplicationsInAmbientSetting
        )
    )
}
