package com.dotsdev.basewatchface.ui.wear

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.wear.watchface.editor.EditorSession
import com.dotsdev.basewatchface.ui.wear.utils.extensions.createWatchFacePreview
import com.dotsdev.basewatchface.ui.wear.utils.extensions.getColorStyle
import com.dotsdev.basewatchface.ui.wear.utils.extensions.getMinuteHandStyle
import com.dotsdev.basewatchface.ui.wear.utils.extensions.getShowComplicationsInAmbient
import com.dotsdev.basewatchface.ui.wear.utils.extensions.getTickEnabledStyle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.plus
import kotlinx.coroutines.yield

interface WatchFaceConfigState {
    val editWatchFaceUiState: EditWatchFaceUiState
    var userStyles: UserStyles
    suspend fun update()
}

@Composable
fun rememberWatchFaceConfigState(
    editorSession: EditorSession
): WatchFaceConfigState {
    val scope = rememberCoroutineScope()
    var editWatchFaceUiState: EditWatchFaceUiState by remember {
        mutableStateOf(EditWatchFaceUiState.Loading("initial"))
    }
    return rememberSaveable(editorSession) {
        object : WatchFaceConfigState {
            override val editWatchFaceUiState: EditWatchFaceUiState
                get() = editWatchFaceUiState

            override var userStyles: UserStyles by mutableStateOf(
                with(editorSession.userStyleSchema) {
                    val showComplicationsInAmbientStyle =
                        editorSession.userStyle.value.getShowComplicationsInAmbient()
                    val colorStyle = editorSession.userStyle.value.getColorStyle()
                    val ticksEnabledStyle = editorSession.userStyle.value.getTickEnabledStyle()
                    val minuteHandStyle = editorSession.userStyle.value.getMinuteHandStyle()
                    UserStyles(
                        showComplicationsInAmbient = showComplicationsInAmbientStyle,
                        colorStyleId = colorStyle,
                        ticksEnabled = ticksEnabledStyle,
                        minuteHandLength = (minuteHandStyle * 1000f).toFloat()
                    )
                },
            )

            override suspend fun update(): Unit = coroutineScope {
                combine(
                    editorSession.userStyle,
                    editorSession.complicationsPreviewData
                ) { userStyle, complicationsPreviewData ->
                    yield()
                    with(editorSession.userStyleSchema) {
                        editWatchFaceUiState = EditWatchFaceUiState.Success(
                            editorSession.createWatchFacePreview(
                                complicationsPreviewData
                            )
                        )
                    }
                }.launchIn(scope + Dispatchers.Main.immediate)

                snapshotFlow { userStyles }.distinctUntilChanged().onEach {
                    editorSession.userStyle.value =
                        editorSession.userStyle.value.toMutableUserStyle().apply {
                            with(editorSession.userStyleSchema) {
                                // TODO: set UserStyle
                            }
                        }.toUserStyle()
                }.launchIn(scope)
            }
        }
    }
}

sealed class EditWatchFaceUiState {
    data class Success(val imageBitmap: ImageBitmap) : EditWatchFaceUiState()
    data class Loading(val message: String) : EditWatchFaceUiState()
    data class Error(val exception: Throwable) : EditWatchFaceUiState()
}

data class UserStyles(
    val colorStyleId: String,
    val ticksEnabled: Boolean,
    val minuteHandLength: Float,
    val showComplicationsInAmbient: Boolean,
)

