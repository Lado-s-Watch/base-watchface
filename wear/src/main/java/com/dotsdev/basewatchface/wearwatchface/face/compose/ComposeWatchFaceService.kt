package com.dotsdev.basewatchface.wearwatchface.face.compose

import android.view.SurfaceHolder
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.WatchFace
import androidx.wear.watchface.WatchFaceService
import androidx.wear.watchface.WatchFaceType
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.style.CurrentUserStyleRepository

class ComposeWatchFaceService1(
    private val watchFaceType: Int = WatchFaceType.ANALOG,
    private val invalidationMode: InvalidationMode = InvalidationMode.DrawAsap,
    complicationSlotIds: Set<Int>
): WatchFaceService() {
    enum class InvalidationMode {
        WaitForInvalidation,

        /**
         * Avoid the extra frame delay that invalidate()/postInvalidate() would add
         * on top of our existing delay in waiting for a frame to update currentTime.
         *
         * Currently relies on internal APIs.
         */
        DrawAsap;
    }

    override suspend fun createWatchFace(
        surfaceHolder: SurfaceHolder,
        watchState: WatchState,
        complicationSlotsManager: ComplicationSlotsManager,
        currentUserStyleRepository: CurrentUserStyleRepository
    ): WatchFace {
        TODO("Not yet implemented")
    }
}