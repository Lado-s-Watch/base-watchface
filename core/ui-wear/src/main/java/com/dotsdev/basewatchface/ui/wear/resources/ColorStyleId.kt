package com.dotsdev.basewatchface.ui.wear.resources

import android.content.Context
import android.graphics.drawable.Icon
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.UserStyleSetting.ListUserStyleSetting
import com.dotsdev.basewatchface.ui.R

const val AMBIENT_COLOR_STYLE_ID = "ambient_style_id"
const val RED_COLOR_STYLE_ID = "red_style_id"
const val GREEN_COLOR_STYLE_ID = "green_style_id"
const val BLUE_COLOR_STYLE_ID = "blue_style_id"
const val WHITE_COLOR_STYLE_ID = "white_style_id"

enum class ColorStyleId(
    val id: String,
    @StringRes val nameResourceId: Int,
    @DrawableRes val iconResourceId: Int,
    @DrawableRes val complicationStyleDrawableId: Int,
    @ColorRes val primaryColorId: Int,
    @ColorRes val secondaryColorId: Int,
    @ColorRes val backgroundColorId: Int,
    @ColorRes val outerElementColorId: Int
) {
    AMBIENT(
        id = AMBIENT_COLOR_STYLE_ID,
        nameResourceId = R.string.ambient_style_name,
        iconResourceId = R.drawable.white_style,
        complicationStyleDrawableId = R.drawable.complication_white_style,
        primaryColorId = R.color.ambient_primary_color,
        secondaryColorId = R.color.ambient_secondary_color,
        backgroundColorId = R.color.ambient_background_color,
        outerElementColorId = R.color.ambient_outer_element_color
    ),

    RED(
        id = RED_COLOR_STYLE_ID,
        nameResourceId = R.string.red_style_name,
        iconResourceId = R.drawable.red_style,
        complicationStyleDrawableId = R.drawable.complication_red_style,
        primaryColorId = R.color.red_primary_color,
        secondaryColorId = R.color.red_secondary_color,
        backgroundColorId = R.color.red_background_color,
        outerElementColorId = R.color.red_outer_element_color
    ),

    GREEN(
        id = GREEN_COLOR_STYLE_ID,
        nameResourceId = R.string.green_style_name,
        iconResourceId = R.drawable.green_style,
        complicationStyleDrawableId = R.drawable.complication_green_style,
        primaryColorId = R.color.green_primary_color,
        secondaryColorId = R.color.green_secondary_color,
        backgroundColorId = R.color.green_background_color,
        outerElementColorId = R.color.green_outer_element_color
    ),

    BLUE(
        id = BLUE_COLOR_STYLE_ID,
        nameResourceId = R.string.blue_style_name,
        iconResourceId = R.drawable.blue_style,
        complicationStyleDrawableId = R.drawable.complication_blue_style,
        primaryColorId = R.color.blue_primary_color,
        secondaryColorId = R.color.blue_secondary_color,
        backgroundColorId = R.color.blue_background_color,
        outerElementColorId = R.color.blue_outer_element_color
    ),

    WHITE(
        id = WHITE_COLOR_STYLE_ID,
        nameResourceId = R.string.white_style_name,
        iconResourceId = R.drawable.white_style,
        complicationStyleDrawableId = R.drawable.complication_white_style,
        primaryColorId = R.color.white_primary_color,
        secondaryColorId = R.color.white_secondary_color,
        backgroundColorId = R.color.white_background_color,
        outerElementColorId = R.color.white_outer_element_color
    );

    companion object {

        fun getColorStyleConfig(id: String): ColorStyleId {
            return when (id) {
                AMBIENT.id -> AMBIENT
                RED.id -> RED
                GREEN.id -> GREEN
                BLUE.id -> BLUE
                WHITE.id -> WHITE
                else -> WHITE
            }
        }

        fun toOptionList(context: Context): List<ListUserStyleSetting.ListOption> {
            return ColorStyleId.values().map { styles ->
                ListUserStyleSetting.ListOption(
                    UserStyleSetting.Option.Id(styles.id),
                    context.resources,
                    styles.nameResourceId,
                    Icon.createWithResource(context, styles.iconResourceId)
                )
            }
        }
    }
}
