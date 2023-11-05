package com.dotsdev.basewatchface.ui.wear

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.wear.watchface.editor.EditorSession
import com.dotsdev.basewatchface.ui.wear.ext.getShowComplicationsInAmbient

interface WatchFaceConfigStateHolder {
    var colorStyle: String
    var drawPips: Boolean
    var minuteHandLength: Double
    var showComplicationsInAmbient: Boolean
    suspend fun update(): Nothing
}

@Composable
fun rememberWatchFaceConfigState(
    editorSession: EditorSession
): WatchFaceConfigStateHolder {
    val scope = rememberCoroutineScope()
    return rememberSaveable(editorSession) {
        object : WatchFaceConfigStateHolder {
            override var colorStyle: String
                get() = TODO("Not yet implemented")
                set(value) {}
            override var drawPips: Boolean
                get() = TODO("Not yet implemented")
                set(value) {}
            override var minuteHandLength: Double
                get() = TODO("Not yet implemented")
                set(value) {}
            override var showComplicationsInAmbient: Boolean by mutableStateOf(
                with(editorSession.userStyleSchema) {
                    editorSession.userStyle.value.getShowComplicationsInAmbient()
                },
            )

            override suspend fun update(): Nothing {
                TODO("Not yet implemented")
            }
        }
    }
}

