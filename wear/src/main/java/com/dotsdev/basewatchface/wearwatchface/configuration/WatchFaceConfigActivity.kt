package com.dotsdev.basewatchface.wearwatchface.configuration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.lifecycleScope
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.watchface.editor.EditorSession
import com.dotsdev.basewatchface.feature.wear.screens.WatchFaceConfigScreen
import com.dotsdev.basewatchface.ui.wear.rememberWatchFaceConfigState
import kotlinx.coroutines.launch

class WatchFaceConfigActivity : ComponentActivity() {
    private lateinit var editorSession: EditorSession
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            editorSession = EditorSession.createOnWatchEditorSession(this@WatchFaceConfigActivity)
        }
        setContent {
            val watchFaceConfigState = rememberWatchFaceConfigState(editorSession)
            LaunchedEffect(watchFaceConfigState) {
                watchFaceConfigState.update()
            }
            MaterialTheme {
                WatchFaceConfigScreen(state = watchFaceConfigState)
            }
        }
    }

    companion object {
        const val TAG = "WatchFaceConfigActivity"
    }
}
