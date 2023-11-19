package com.dotsdev.basewatchface.feature.wear.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
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
    onPickColorClick: () -> Unit,
) {
    composable(settingRoute) {
        SettingScreen(state, onPickColorClick, modifier)
    }
}

@Composable
fun SettingScreen(
    state: WatchFaceConfigState,
    onPickColorClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ScalingLazyColumn(modifier) {
        item {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                when (val edtState = state.editWatchFaceUiState) {
                    is EditWatchFaceUiState.Success -> {
                        WatchFacePreview(
                            bitmap = edtState.imageBitmap,
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                    }

                    else -> CircularProgressIndicator()
                }
            }
        }
        item {
            Chip(
                label = {
                    Text(text = stringResource(id = R.string.colors_style_setting))
                },
                icon = {
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                            .background(state.userStyles.getBackgroundColor(), CircleShape),
                    )
                },
                colors = ChipDefaults.secondaryChipColors(),
                onClick = onPickColorClick,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        item {
            ToggleChip(
                checked = state.userStyles.showComplicationsInAmbient,
                onCheckedChange = {
                    state.userStyles = state.userStyles.copy(showComplicationsInAmbient = it)
                },
                label = {
                    Text(text = stringResource(id = R.string.watchface_complications_setting))
                },
                toggleControl = {
                    Switch(
                        checked = state.userStyles.showComplicationsInAmbient,
                        onCheckedChange = {
                            state.userStyles = state.userStyles.copy(showComplicationsInAmbient = it)
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )
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
            onPickColorClick = {}
        )
    }
}
