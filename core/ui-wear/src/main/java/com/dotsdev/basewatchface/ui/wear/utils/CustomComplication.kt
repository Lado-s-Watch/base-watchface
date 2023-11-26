package com.dotsdev.basewatchface.ui.wear.utils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.graphics.RectF
import androidx.core.graphics.ColorUtils
import androidx.wear.watchface.CanvasComplication
import androidx.wear.watchface.RenderParameters
import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.data.MonochromaticImageComplicationData
import androidx.wear.watchface.complications.data.NoDataComplicationData
import androidx.wear.watchface.complications.data.ShortTextComplicationData
import androidx.wear.watchface.complications.data.SmallImageComplicationData
import com.dotsdev.basewatchface.ui.wear.utils.extensions.MonochromaticImageComplication
import com.dotsdev.basewatchface.ui.wear.utils.extensions.getIconBitmap
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

    @JvmField
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

    open fun renderCircleProgressComplication(
        canvas: Canvas,
        bounds: Rect,
        progress: Float,
        imageComplication: MonochromaticImageComplication?
    ) {
        val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.GRAY
            style = Paint.Style.STROKE
            strokeWidth = 5f
        }

        val remainPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = tertiaryColor
            style = Paint.Style.STROKE
            strokeWidth = 5f
        }

        val centerX = bounds.exactCenterX()
        val centerY = bounds.exactCenterX()
        val radius = bounds.width() / 5f

        // Draw grey circle
        canvas.drawCircle(centerX, centerY, radius, circlePaint)

        // Draw percent
        val oval = RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
        canvas.drawArc(oval, -90f, 360 * progress / 100, false, remainPaint)

        val icon = imageComplication?.icon
        val iconBounds = imageComplication?.iconBounds ?: Rect()

//        val dstRect = RectF(
//            centerX - iconBounds.width() / 2,
//            centerY - iconBounds.height() / 2 - iconOffsetY,
//            centerX + iconBounds.width() / 2,
//            centerY + iconBounds.height() / 2 - iconOffsetY,
//        )
//        if (icon != null) {
//            canvas.drawBitmap(icon, iconBounds, dstRect, iconPaint)
//        }


        // Draw text
        val textWidth = textPaint.measureText(progress.toString())
        canvas.drawText(
            progress.toString(),
            centerX - textWidth / 2,
            centerY + textPaint.textSize / 4,
            textPaint.apply { textSize = 20f }
        )
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