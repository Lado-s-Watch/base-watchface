package com.dotsdev.basewatchface.ui.wear.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.wear.watchface.CanvasComplicationFactory
import androidx.wear.watchface.complications.data.MonochromaticImageComplicationData
import androidx.wear.watchface.complications.data.ShortTextComplicationData
import androidx.wear.watchface.complications.data.SmallImageComplicationData
import com.dotsdev.basewatchface.ui.R
import com.dotsdev.basewatchface.ui.wear.utils.extensions.ComplicationProvider
import com.dotsdev.basewatchface.ui.wear.utils.extensions.getIconBitmap
import com.dotsdev.basewatchface.ui.wear.utils.extensions.getText
import com.dotsdev.basewatchface.ui.wear.utils.extensions.getTitle
import com.dotsdev.basewatchface.ui.wear.utils.extensions.provider
import java.time.Instant

class HorizontalComplication(private val context: Context): CustomComplication() {
    override fun renderShortTextComplication(
        canvas: Canvas,
        bounds: Rect,
        data: ShortTextComplicationData
    ) {
        val text = data.getText(context)
        if (text == "--") return

        val title = data.getTitle(context)
        val icon: Bitmap?
        val iconBounds: Rect
        when (data.provider()) {
            ComplicationProvider.Battery -> {
                val progress = try {
                    text.padStart(3, ' ').trim().toFloat()
                } catch (e: Exception) {
                    0f
                }
                val monochromaticImage = with(bounds) { data.getIconBitmap(context, 0.55f) }
                renderCircleProgressComplication(canvas, bounds, progress, monochromaticImage)
                return
            }

            ComplicationProvider.StepCount -> {
                val drawable = ContextCompat.getDrawable(context, R.drawable.step_count)
                icon = drawable?.toBitmap(
                    (32f / 48f * bounds.height()).toInt(),
                    (32f / 48f * bounds.height()).toInt()
                )
                iconBounds =
                    Rect(
                        0,
                        0,
                        (32f / 48f * bounds.height()).toInt(),
                        (32f / 48f * bounds.height()).toInt()
                    )
            }

            else -> {
                val monochromaticImage = with(bounds) { data.getIconBitmap(context, 0.55f) }
                icon = monochromaticImage?.icon
                iconBounds = monochromaticImage?.iconBounds ?: Rect()
            }
        }

        textPaint.textSize = 24F / 48f * bounds.height()

        val textBounds = Rect()
        textPaint.getTextBounds(text, 0, text.length, textBounds)

        val titleBounds = Rect()

        if (title != null) {
            titlePaint.textSize = textPaint.textSize
            titlePaint.getTextBounds(title, 0, title.length, titleBounds)
        }

        var iconOffsetX = 0f
        var titleOffsetX = 0f
        var textOffsetX = 0f

        if (title != null) {
            val width = titleBounds.width() + textBounds.width()

            titleOffsetX = (width - titleBounds.width()).toFloat() / 2f
            textOffsetX = (width - textBounds.width()).toFloat() / 2f

            titleOffsetX += 6f / 156f * bounds.width()
            textOffsetX += 6f / 156f * bounds.width()
        } else if (icon != null) {
            val width = iconBounds.width() + textBounds.width()

            iconOffsetX = (width - iconBounds.width()).toFloat() / 2f
            textOffsetX = (width - textBounds.width()).toFloat() / 2f

            iconOffsetX += 9f / 156f * bounds.width()
            textOffsetX += 9f / 156f * bounds.width()
        }

        if (title != null) {
            canvas.drawText(
                title,
                bounds.exactCenterX() - titleBounds.width() / 2 - titleOffsetX,
                bounds.exactCenterY() + titleBounds.height() / 2,
                titlePaint
            )
        } else if (icon != null) {
            val dstRect = RectF(
                bounds.exactCenterX() - iconBounds.width() / 2f - iconOffsetX,
                bounds.exactCenterY() - iconBounds.height() / 2f,
                bounds.exactCenterX() + iconBounds.width() / 2f - iconOffsetX,
                bounds.exactCenterY() + iconBounds.height() / 2f,
            )

            canvas.drawBitmap(icon, iconBounds, dstRect, iconPaint)
        }

        canvas.drawText(
            text,
            bounds.exactCenterX() - textBounds.width() / 2 + textOffsetX,
            bounds.exactCenterY() + textBounds.height() / 2,
            textPaint
        )
    }

    override fun renderMonochromaticImageComplication(
        canvas: Canvas,
        bounds: Rect,
        data: MonochromaticImageComplicationData
    ) {
        // TODO("Not yet implemented")
    }

    override fun renderSmallImageComplication(
        canvas: Canvas,
        bounds: Rect,
        data: SmallImageComplicationData
    ) {
        // TODO("Not yet implemented")
    }
}

fun createHorizontalComplicationFactory(context: Context) = CanvasComplicationFactory { _, _ ->
    HorizontalComplication(context)
}