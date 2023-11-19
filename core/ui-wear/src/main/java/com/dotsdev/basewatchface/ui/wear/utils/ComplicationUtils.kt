package com.dotsdev.basewatchface.ui.wear.utils

import android.content.Context
import android.graphics.RectF
import androidx.wear.watchface.CanvasComplicationFactory
import androidx.wear.watchface.ComplicationSlot
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.complications.ComplicationSlotBounds
import androidx.wear.watchface.complications.DefaultComplicationDataSourcePolicy
import androidx.wear.watchface.complications.SystemDataSources
import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.rendering.CanvasComplicationDrawable
import androidx.wear.watchface.complications.rendering.ComplicationDrawable
import androidx.wear.watchface.style.CurrentUserStyleRepository
import com.dotsdev.basewatchface.ui.R

// Information needed for complications.
// Creates bounds for the locations of both right and left complications. (This is the
// location from 0.0 - 1.0.)
// Both left and right complications use the same top and bottom bounds.
private const val LEFT_AND_RIGHT_COMPLICATIONS_TOP_BOUND = 0.4f
private const val LEFT_AND_RIGHT_COMPLICATIONS_BOTTOM_BOUND = 1f - LEFT_AND_RIGHT_COMPLICATIONS_TOP_BOUND

private const val HORIZONTAL_COMPLICATION_LEFT_BOUND = 0.3f
private const val HORIZONTAL_COMPLICATION_RIGHT_BOUND = 1f - HORIZONTAL_COMPLICATION_LEFT_BOUND

private const val TOP_COMPLICATION_TOP_BOUND = 0.0625f
private const val TOP_COMPLICATION_BOTTOM_BOUND = 3 * TOP_COMPLICATION_TOP_BOUND

private const val LEFT_COMPLICATION_LEFT_BOUND = 0.2f
private const val LEFT_COMPLICATION_RIGHT_BOUND = 0.4f

private const val RIGHT_COMPLICATION_LEFT_BOUND = 0.6f
private const val RIGHT_COMPLICATION_RIGHT_BOUND = 0.8f

sealed class ComplicationConfig(val id: Int, val supportedTypes: List<ComplicationType>) {
    data object Left : ComplicationConfig(
        LEFT_COMPLICATION_ID,
        listOf(
            ComplicationType.RANGED_VALUE,
            ComplicationType.MONOCHROMATIC_IMAGE,
            ComplicationType.SHORT_TEXT,
            ComplicationType.SMALL_IMAGE
        )
    )

    data object Right : ComplicationConfig(
        RIGHT_COMPLICATION_ID,
        listOf(
            ComplicationType.RANGED_VALUE,
            ComplicationType.MONOCHROMATIC_IMAGE,
            ComplicationType.SHORT_TEXT,
            ComplicationType.SMALL_IMAGE
        )
    )

    data object Top : ComplicationConfig(
        TOP_COMPLICATION_ID,
        listOf(ComplicationType.SHORT_TEXT)
    )
    companion object {
        // Unique IDs for each complication. The settings activity that supports allowing users
        // to select their complication data provider requires numbers to be >= 0.
        private const val LEFT_COMPLICATION_ID = 100
        private const val RIGHT_COMPLICATION_ID = 101
        private const val TOP_COMPLICATION_ID = 102
    }
}

fun createComplicationSlotManager(
    context: Context,
    currentUserStyleRepository: CurrentUserStyleRepository
): ComplicationSlotsManager {

    val leftComplication = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Left.id,
        canvasComplicationFactory = createVerticalComplicationFactory(context),
        supportedTypes = ComplicationConfig.Left.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_DAY_OF_WEEK,
            ComplicationType.SHORT_TEXT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                LEFT_COMPLICATION_LEFT_BOUND,
                LEFT_AND_RIGHT_COMPLICATIONS_TOP_BOUND,
                LEFT_COMPLICATION_RIGHT_BOUND,
                LEFT_AND_RIGHT_COMPLICATIONS_BOTTOM_BOUND
            )
        )
    ).build()

    val rightComplication = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Right.id,
        canvasComplicationFactory = createVerticalComplicationFactory(context),
        supportedTypes = ComplicationConfig.Right.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_STEP_COUNT,
            ComplicationType.SHORT_TEXT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                RIGHT_COMPLICATION_LEFT_BOUND,
                LEFT_AND_RIGHT_COMPLICATIONS_TOP_BOUND,
                RIGHT_COMPLICATION_RIGHT_BOUND,
                LEFT_AND_RIGHT_COMPLICATIONS_BOTTOM_BOUND
            )
        )
    ).build()

    val topComplication = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Top.id,
        canvasComplicationFactory = createHorizontalComplicationFactory(context),
        supportedTypes = ComplicationConfig.Top.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_WATCH_BATTERY,
            ComplicationType.SHORT_TEXT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                HORIZONTAL_COMPLICATION_LEFT_BOUND,
                TOP_COMPLICATION_TOP_BOUND,
                HORIZONTAL_COMPLICATION_RIGHT_BOUND,
                TOP_COMPLICATION_BOTTOM_BOUND
            )
        )
    ).build()

    return ComplicationSlotsManager(
        listOf(leftComplication, rightComplication, topComplication),
        currentUserStyleRepository
    )
}

fun ComplicationData.isBattery(): Boolean {
    return dataSource?.className == "com.google.android.clockwork.sysui.experiences.complications.providers.BatteryProviderService"
}
