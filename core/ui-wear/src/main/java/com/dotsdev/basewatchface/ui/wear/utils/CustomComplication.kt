package com.dotsdev.basewatchface.ui.wear.utils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import androidx.core.graphics.ColorUtils
import androidx.wear.watchface.CanvasComplication
import androidx.wear.watchface.RenderParameters
import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.data.MonochromaticImageComplicationData
import androidx.wear.watchface.complications.data.NoDataComplicationData
import androidx.wear.watchface.complications.data.ShortTextComplicationData
import androidx.wear.watchface.complications.data.SmallImageComplicationData
import java.time.ZonedDateTime

abstract class CustomComplication : CanvasComplication {
    var tertiaryColor: Int = Color.parseColor("#8888bb")
        set(tertiaryColor) {
            field = tertiaryColor
            textPaint.color = tertiaryColor
            titlePaint.color = tertiaryColor
            iconPaint.colorFilter = PorterDuffColorFilter(tertiaryColor, PorterDuff.Mode.SRC_IN)
            prefixPaint.color = tertiaryColor
            prefixPaint.alpha = 100
        }

    var opacity: Float = 1f
        set(opacity) {
            field = opacity

            val color = ColorUtils.blendARGB(Color.TRANSPARENT, tertiaryColor, opacity)
            textPaint.color = color
            titlePaint.color = color

            iconPaint.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
            imagePaint.alpha = (opacity * 255).toInt()

            prefixPaint.color = color
            prefixPaint.alpha = 100
        }

    protected val textPaint = Paint().apply {
        isAntiAlias = true
        textAlign = Paint.Align.LEFT
        color = tertiaryColor
    }

    protected val titlePaint = Paint().apply {
        isAntiAlias = true
        textAlign = Paint.Align.LEFT
        color = tertiaryColor
    }

    protected val iconPaint = Paint().apply {
        colorFilter = PorterDuffColorFilter(tertiaryColor, PorterDuff.Mode.SRC_IN)
    }

    protected val imagePaint = Paint()

    protected val prefixPaint = Paint().apply {
        isAntiAlias = true
        textAlign = Paint.Align.LEFT
        color = Color.parseColor("#343434")
        alpha = 127
    }

    protected var data: ComplicationData = NoDataComplicationData()

    override fun getData(): ComplicationData = data

    override fun loadData(
        complicationData: ComplicationData, loadDrawablesAsynchronous: Boolean
    ) {
        data = complicationData
    }

    override fun render(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        renderParameters: RenderParameters,
        slotId: Int
    ) {
        if (bounds.isEmpty) return

        when (data.type) {
            ComplicationType.SHORT_TEXT -> {
                renderShortTextComplication(canvas, bounds, data as ShortTextComplicationData)
            }

            ComplicationType.MONOCHROMATIC_IMAGE -> {
                renderMonochromaticImageComplication(
                    canvas, bounds, data as MonochromaticImageComplicationData
                )
            }

            ComplicationType.SMALL_IMAGE -> {
                renderSmallImageComplication(canvas, bounds, data as SmallImageComplicationData)
            }

            else -> return
        }
    }

    abstract fun renderShortTextComplication(
        canvas: Canvas,
        bounds: Rect,
        data: ShortTextComplicationData,
    )

    abstract fun renderMonochromaticImageComplication(
        canvas: Canvas,
        bounds: Rect,
        data: MonochromaticImageComplicationData,
    )

    abstract fun renderSmallImageComplication(
        canvas: Canvas,
        bounds: Rect,
        data: SmallImageComplicationData,
    )

    override fun drawHighlight(
        canvas: Canvas, bounds: Rect, boundsType: Int, zonedDateTime: ZonedDateTime, color: Int
    ) {
    }

}