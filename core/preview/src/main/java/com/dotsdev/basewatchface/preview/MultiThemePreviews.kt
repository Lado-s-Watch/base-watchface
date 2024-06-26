package com.dotsdev.basewatchface.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@ShowcaseMultiplePreviewsWorkaround
object MultiThemePreviewDefinition {
    const val Group = "Theme"

    object DarkMode {
        const val Name = "DarkMode"
        const val UiMode = Configuration.UI_MODE_NIGHT_YES
    }

    object LightMode {
        const val Name = "LightMode"
        const val UiMode = Configuration.UI_MODE_NIGHT_NO
    }
}

@Preview(
    name = MultiThemePreviewDefinition.LightMode.Name,
    group = MultiThemePreviewDefinition.Group,
    uiMode = MultiThemePreviewDefinition.LightMode.UiMode,
)
@Preview(
    name = MultiThemePreviewDefinition.DarkMode.Name,
    group = MultiThemePreviewDefinition.Group,
    uiMode = MultiThemePreviewDefinition.DarkMode.UiMode,
)
annotation class MultiThemePreviews
