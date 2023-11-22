package com.dotsdev.basewatchface.ui.wear.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.wear.watchface.CanvasComplicationFactory
import androidx.wear.watchface.complications.data.MonochromaticImageComplicationData
import androidx.wear.watchface.complications.data.ShortTextComplicationData
import androidx.wear.watchface.complications.data.SmallImageComplicationData
import com.dotsdev.basewatchface.ui.R
import java.time.Instant

class VerticalComplication(private val context: Context) : CustomComplication() {
    override fun renderShortTextComplication(
        canvas: Canvas,
        bounds: Rect,
        data: ShortTextComplicationData,
    ) {
        val now = Instant.now()

        var text = data.text.getTextAt(context.resources, now).toString().uppercase()
        if (text == "--") return

        var title: String? = null
        var icon: Bitmap? = null
        var iconBounds = Rect()
        var prefixLen = 0

        when (data.provider()) {
            ComplicationProvider.Battery -> {
                val drawable = ContextCompat.getDrawable(context, R.drawable.ic_battery)!!
                icon = drawable.toBitmap(
                    (32f / 78f * bounds.width()).toInt(), (32f / 78f * bounds.width()).toInt()
                )
                iconBounds = Rect(
                    0, 0, (32f / 78f * bounds.width()).toInt(), (32f / 78f * bounds.width()).toInt()
                )
                prefixLen = 3 - text.length
                text = text.padStart(3, ' ')
            }

            ComplicationProvider.StepCount -> {
                val drawable = ContextCompat.getDrawable(context, R.drawable.step_count)!!
                icon = drawable.toBitmap(
                    (32f / 78f * bounds.width()).toInt(), (32f / 78f * bounds.width()).toInt()
                )
                iconBounds = Rect(
                    0, 0, (32f / 78f * bounds.width()).toInt(), (32f / 78f * bounds.width()).toInt()
                )
            }

            else -> {
                if (data.monochromaticImage != null) {
                    val drawable = data.monochromaticImage!!.image.loadDrawable(context)
                    if (drawable != null) {
                        val size = (bounds.width().coerceAtMost(bounds.height()).toFloat() / 2f).toInt()
                        icon = drawable.toBitmap(size, size)
                        iconBounds = Rect(0, 0, size, size)
                    }
                }
            }
        }

        if (data.title != null && !data.title!!.isPlaceholder()) {
            title = data.title!!.getTextAt(context.resources, now).toString().uppercase()
        }

        if (text.length <= 3) {
            textPaint.textSize = 24F / 78F * bounds.width()
        } else if (text.length <= 6) {
            textPaint.textSize = 16F / 78F * bounds.width()
        } else {
            textPaint.textSize = 12F / 78F * bounds.width()
        }

        val textBounds = Rect()

        if (data.isBattery()) {
            textPaint.getTextBounds("000", 0, 3, textBounds)
        } else {
            textPaint.getTextBounds(text, 0, text.length, textBounds)
        }

        val titleBounds = Rect()

        if (title != null) {
            if (title.length <= 3) {
                titlePaint.textSize = 24F / 78F * bounds.width()
            } else if (title.length <= 6) {
                titlePaint.textSize = 16F / 78F * bounds.width()
            } else {
                titlePaint.textSize = 12F / 78F * bounds.width()
            }

            titlePaint.getTextBounds(title, 0, title.length, titleBounds)
        }

        var iconOffsetY = 0f
        var titleOffsetY = 0f
        var textOffsetY = 0f

        if (icon != null) {
            val height = iconBounds.height() + textBounds.height()

            iconOffsetY = (height - iconBounds.height()).toFloat() / 2f
            textOffsetY = (height - textBounds.height()).toFloat() / 2f

            iconOffsetY += 9f / 132f * bounds.height()
            if (data.isBattery()) {
                iconOffsetY = iconOffsetY.toInt().toFloat()
            }

            textOffsetY += 9f / 132f * bounds.height()
        } else if (title != null) {
            val height = titleBounds.height() + textBounds.height()

            titleOffsetY = (height - titleBounds.height()).toFloat() / 2f
            textOffsetY = (height - textBounds.height()).toFloat() / 2f

            titleOffsetY += 9f / 132f * bounds.height()
            textOffsetY += 9f / 132f * bounds.height()
        }

        if (icon != null) {
            val dstRect = RectF(
                bounds.exactCenterX() - iconBounds.width() / 2,
                bounds.exactCenterY() - iconBounds.height() / 2 - iconOffsetY,
                bounds.exactCenterX() + iconBounds.width() / 2,
                bounds.exactCenterY() + iconBounds.height() / 2 - iconOffsetY,
            )

            canvas.drawBitmap(icon, iconBounds, dstRect, iconPaint)
        } else if (title != null) {
            canvas.drawText(
                title,
                bounds.exactCenterX() - titleBounds.width() / 2,
                bounds.exactCenterY() + titleBounds.height() / 2 - titleOffsetY,
                titlePaint
            )
        }

        if (prefixLen > 0) {
            val prefix = "".padStart(prefixLen, '0')
            prefixPaint.textSize = textPaint.textSize

            canvas.drawText(
                prefix,
                bounds.exactCenterX() - textBounds.width() / 2,
                bounds.exactCenterY() + textBounds.height() / 2 + textOffsetY,
                prefixPaint
            )
        }

        canvas.drawText(
            text,
            bounds.exactCenterX() - textBounds.width() / 2,
            bounds.exactCenterY() + textBounds.height() / 2 + textOffsetY,
            textPaint
        )
    }

    override fun renderMonochromaticImageComplication(
        canvas: Canvas,
        bounds: Rect,
        data: MonochromaticImageComplicationData,
    ) {
        val icon: Bitmap
        val iconBounds: Rect

        val drawable = data.monochromaticImage.image.loadDrawable(context) ?: return

        val size = (bounds.width().coerceAtMost(bounds.height()).toFloat() * 0.8f).toInt()

        icon = drawable.toBitmap(size, size)
        iconBounds = Rect(0, 0, size, size)

        val dstRect = RectF(
            bounds.exactCenterX() - iconBounds.width() / 2,
            bounds.exactCenterY() - iconBounds.height() / 2,
            bounds.exactCenterX() + iconBounds.width() / 2,
            bounds.exactCenterY() + iconBounds.height() / 2,
        )

        canvas.drawBitmap(icon, iconBounds, dstRect, iconPaint)
    }

    override fun renderSmallImageComplication(
        canvas: Canvas,
        bounds: Rect,
        data: SmallImageComplicationData,
    ) {
        val icon: Bitmap
        val iconBounds: Rect
        val drawable = data.smallImage.image.loadDrawable(context) ?: return

        val size = (bounds.width().coerceAtMost(bounds.height()).toFloat() * 0.75f).toInt()

        icon = drawable.toBitmap(size, size)
        iconBounds = Rect(0, 0, size, size)

        val dstRect = RectF(
            bounds.exactCenterX() - iconBounds.width() / 2,
            bounds.exactCenterY() - iconBounds.height() / 2,
            bounds.exactCenterX() + iconBounds.width() / 2,
            bounds.exactCenterY() + iconBounds.height() / 2,
        )

        canvas.drawBitmap(icon, iconBounds, dstRect, imagePaint)
    }
}

fun createVerticalComplicationFactory(context: Context) = CanvasComplicationFactory { _, _ ->
    VerticalComplication(context)
}