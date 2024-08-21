package com.dotsdev.basewatchface.wearwatchface.face.compose.analog

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import androidx.wear.watchface.complications.data.ComplicationData
import com.dotsdev.basewatchface.wearwatchface.face.compose.ComposeWatchFaceService
import kotlinx.coroutines.flow.StateFlow

class AnalogComposeWatchFaceService : ComposeWatchFaceService(
    complicationSlotIds = emptySet(), invalidationMode = InvalidationMode.WaitForInvalidation
) {

    @Composable
    override fun Content(complicationData: Map<Int, StateFlow<ComplicationData>>) {
        Box {
            Button(
                modifier = Modifier.align(Alignment.Center),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
            ) {
                Text(text = "Button")
            }
        }
    }
}