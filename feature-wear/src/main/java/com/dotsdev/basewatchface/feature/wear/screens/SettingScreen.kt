package com.dotsdev.basewatchface.feature.wear.screens

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.navigation.NavGraphBuilder
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.navigation.composable
import com.dotsdev.basewatchface.feature.wear.component.WatchFacePreview
import com.dotsdev.basewatchface.preview.WearPreview
import com.dotsdev.basewatchface.ui.R
import com.dotsdev.basewatchface.ui.wear.EditWatchFaceUiState
import com.dotsdev.basewatchface.ui.wear.UserStyles
import com.dotsdev.basewatchface.ui.wear.WatchFaceConfigState
import com.dotsdev.basewatchface.ui.wear.resources.ColorStyleId
import kotlinx.coroutines.awaitCancellation

const val settingRoute = "setting"
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
    ScalingLazyColumn(modifier) {
        item {
            when (val edtState = state.editWatchFaceUiState) {
                is EditWatchFaceUiState.Success -> {
                    WatchFacePreview(bitmap = edtState.imageBitmap)
                }

                else -> CircularProgressIndicator()
            }
        }
    }
}

@WearPreview
@Composable
fun SettingScreenPreview() {
    MaterialTheme {
        val preview = ImageBitmap.imageResource(id = R.drawable.watch_preview)
        SettingScreen(
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