package com.dotsdev.basewatchface.feature.wear.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.dotsdev.basewatchface.feature.wear.screens.colorpicker.colorPickerScreen
import com.dotsdev.basewatchface.preview.WearPreview
import com.dotsdev.basewatchface.ui.R
import com.dotsdev.basewatchface.ui.wear.EditWatchFaceUiState
import com.dotsdev.basewatchface.ui.wear.UserStyles
import com.dotsdev.basewatchface.ui.wear.WatchFaceConfigState
import com.dotsdev.basewatchface.ui.wear.resources.ColorStyleId
import kotlinx.coroutines.awaitCancellation

@Composable
fun WatchFaceConfigScreen(
    state: WatchFaceConfigState,
    modifier: Modifier = Modifier,
) {
    val navController = rememberSwipeDismissableNavController()
    Scaffold(
        modifier = modifier,
    ) {
        SwipeDismissableNavHost(
            navController = navController,
            startDestination = settingRoute
        ) {
            settingScreen(state, modifier)
            colorPickerScreen(state.userStyles.colorStyleId) {
                state.userStyles = state.userStyles.copy(colorStyleId = it)
            }
        }
    }
}


@WearPreview
@Composable
fun WatchFaceConfigScreenPreview() {
    MaterialTheme {
        val preview = ImageBitmap.imageResource(id = R.drawable.watch_preview)
        WatchFaceConfigScreen(
            state = object : WatchFaceConfigState {
                override val editWatchFaceUiState: EditWatchFaceUiState
                    get() = EditWatchFaceUiState.Success(preview)
                override var userStyles: UserStyles
                    get() = UserStyles(
                        colorStyleId = ColorStyleId.AMBIENT.id,
                        ticksEnabled = true,
                        minuteHandLength = 60f,
                        showComplicationsInAmbient = true
                    )
                    set(_) {}

                override suspend fun update(): Nothing = awaitCancellation()
            },
        )
    }
}