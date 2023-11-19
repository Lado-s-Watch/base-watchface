package com.dotsdev.basewatchface.feature.wear.screens.colorpicker

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.wear.compose.navigation.composable

const val colorPickerRoute = "color_picker"
fun NavGraphBuilder.colorPickerScreen(
    colorId: String,
    onSetColor: (String) -> Unit
) {
    composable(colorPickerRoute) {
        ColorPickerScreen(colorId = colorId, onSetColor = onSetColor)
    }
}

@Composable
fun ColorPickerScreen(
    colorId: String,
    onSetColor: (String) -> Unit
) {

}
