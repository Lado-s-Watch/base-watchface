package com.dotsdev.basewatchface.wearwatchface.face.compose

import android.content.res.Configuration
import android.view.SurfaceHolder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.LayoutDirection
import androidx.wear.watchface.CanvasType
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.Renderer
import androidx.wear.watchface.WatchFace
import androidx.wear.watchface.WatchFaceService
import androidx.wear.watchface.WatchFaceType
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.style.CurrentUserStyleRepository
import androidx.wear.watchface.style.UserStyleSchema
import com.dotsdev.basewatchface.ui.wear.utils.createComplicationSlotManager
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

abstract class ComposeWatchFaceService(
    private val watchFaceType: Int = WatchFaceType.ANALOG,
    private val invalidationMode: InvalidationMode = InvalidationMode.DrawAsap,
    complicationSlotIds: Set<Int>
) : WatchFaceService() {
    enum class InvalidationMode {
        WaitForInvalidation, DrawAsap;
    }

    @Composable
    abstract fun Content(complicationData: Map<Int, StateFlow<ComplicationData>>)

    open fun referenceTime(): ZonedDateTime = createReferenceTime(
        hour = 10, minute = 10, second = 37
    )

    private fun createReferenceTime(hour: Int, minute: Int, second: Int): ZonedDateTime {
        val refTime = LocalTime.of(hour, minute, second)
        return ZonedDateTime.of(LocalDate.now(), refTime, ZoneOffset.UTC)
    }

    override fun createUserStyleSchema(): UserStyleSchema = UserStyleSchema(emptyList())

    override fun createComplicationSlotsManager(
        currentUserStyleRepository: CurrentUserStyleRepository
    ): ComplicationSlotsManager = createComplicationSlotManager(
        context = applicationContext, currentUserStyleRepository = currentUserStyleRepository
    )

    final override suspend fun createWatchFace(
        surfaceHolder: SurfaceHolder,
        watchState: WatchState,
        complicationSlotsManager: ComplicationSlotsManager,
        currentUserStyleRepository: CurrentUserStyleRepository
    ): WatchFace {
        layoutDirection.value = layoutDirectionFromInt(resources.configuration.layoutDirection)
        //configuration.value = resources.configuration
        val complications =
            complicationSlotsManager.complicationSlots.mapValues { it.value.complicationData }
        val renderer = ComposeWatchRenderer(surfaceHolder = surfaceHolder,
            currentUserStyleRepository = currentUserStyleRepository,
            watchState = watchState,
            canvasType = CanvasType.HARDWARE,
//            interactiveDrawModeUpdateDelayMillis = 60_000,
//            layoutDirection = layoutDirection,
//            configuration = configuration,
//            layerContainer = layerContainer,
//            invalidationMode = invalidationMode,
            service = this,
            complicationSlotsManager = complicationSlotsManager,
            content = { Content(complications) }).also { renderer = it }

        return WatchFace(
            watchFaceType = watchFaceType, renderer = renderer
        ).also {
            //it.setTapListener(object : WatchFace.TapListener)
            it.setOverridePreviewReferenceInstant(referenceTime().toInstant())
        }
    }

//    private var pendingWatchFaceColors: WatchFaceColors? = null

    private var renderer: Renderer? = null
        set(value) {
            field = value
            if (value == null) return
//            if (pendingWatchFaceColors != null) {
//                value.watchfaceColors = pendingWatchFaceColors
//                pendingWatchFaceColors = null
//            }
        }

    override fun onConfigurationChanged(newConfig: Configuration) {
        layoutDirection.value = layoutDirectionFromInt(newConfig.layoutDirection)
        //configuration.value = newConfig
    }

    private val layoutDirection = mutableStateOf(LayoutDirection.Ltr)
    //private val configuration = mutableStateOf(appCtx.resources.configuration)

    private fun layoutDirectionFromInt(layoutDirection: Int): LayoutDirection {
        return when (layoutDirection) {
            android.util.LayoutDirection.LTR -> LayoutDirection.Ltr
            android.util.LayoutDirection.RTL -> LayoutDirection.Rtl
            else -> LayoutDirection.Ltr
        }
    }
}