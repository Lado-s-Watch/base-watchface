package com.dotsdev.basewatchface.ui.wear.ext

import androidx.wear.watchface.style.ExperimentalHierarchicalStyle
import androidx.wear.watchface.style.UserStyle
import androidx.wear.watchface.style.UserStyleSchema
import androidx.wear.watchface.style.UserStyleSetting

context(UserStyleSchema)
@OptIn(ExperimentalHierarchicalStyle::class)
fun UserStyle.getShowComplicationsInAmbient(): Boolean {
    val id = UserStyleSetting.Id("show_complications_in_ambient")
    val showComplicationsInAmbientSetting = rootUserStyleSettings.find { it.id == id }
            as UserStyleSetting.BooleanUserStyleSetting
    return (this[showComplicationsInAmbientSetting]
            as UserStyleSetting.BooleanUserStyleSetting.BooleanOption).value
}