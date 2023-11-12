package com.dotsdev.basewatchface.feature.wear.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.navigation.composable
import com.dotsdev.basewatchface.ui.wear.WatchFaceConfigState

const val settingRoute = "color_picker"
fun NavGraphBuilder.settingScreen(
    state: WatchFaceConfigState,
    modifier: Modifier = Modifier,
) {
    composable(settingRoute) {
        SettingScreen(state, modifier)
    }
}

@Composable
fun SettingScreen(
    state: WatchFaceConfigState,
    modifier: Modifier = Modifier,
) {
    ScalingLazyColumn {

    }
}