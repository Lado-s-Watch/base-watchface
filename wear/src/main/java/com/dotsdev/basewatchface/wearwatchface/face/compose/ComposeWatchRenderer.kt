package com.dotsdev.basewatchface.wearwatchface.face.compose

import android.annotation.SuppressLint
import android.app.ActionBar.LayoutParams
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.Rect
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.graphics.applyCanvas
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.DrawMode
import androidx.wear.watchface.Renderer
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.style.CurrentUserStyleRepository
import com.dotsdev.basewatchface.ui.wear.resources.WatchFaceColorPalette
import com.dotsdev.basewatchface.ui.wear.resources.WatchFaceData
import com.dotsdev.basewatchface.wearwatchface.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

private const val FRAME_PERIOD_MS_DEFAULT: Long = 16L

class ComposeWatchRenderer(
    private val service: ComposeWatchFaceService,
    surfaceHolder: SurfaceHolder,
    watchState: WatchState,
    private val complicationSlotsManager: ComplicationSlotsManager,
    currentUserStyleRepository: CurrentUserStyleRepository,
    canvasType: Int,
    private val content: @Composable () -> Unit,
) :Renderer.CanvasRenderer2<ComposeWatchRenderer.WatchSharedAssets>(
    surfaceHolder,
    currentUserStyleRepository,
    watchState,
    canvasType,
    FRAME_PERIOD_MS_DEFAULT,
    clearWithBackgroundTintBeforeRenderingHighlightLayer = false
) {
    private val windowManager get() = service.getSystemService(WINDOW_SERVICE) as WindowManager

    class WatchSharedAssets : SharedAssets {
        override fun onDestroy() {}
    }


    // Lazy initialization of the bitmap
    private val composeBitmap: Bitmap by lazy {
        Bitmap.createBitmap(
            surfaceHolder.surfaceFrame.width(),
            surfaceHolder.surfaceFrame.height(),
            Bitmap.Config.ARGB_8888
        )
    }
    private lateinit var composeView: ComposeView

    override suspend fun createSharedAssets(): WatchSharedAssets {
        return WatchSharedAssets()
    }


    override fun renderHighlightLayer(
        canvas: Canvas, bounds: Rect, zonedDateTime: ZonedDateTime, sharedAssets: WatchSharedAssets
    ) {
        canvas.drawColor(renderParameters.highlightLayer!!.backgroundTint)
        for ((_, complication) in complicationSlotsManager.complicationSlots) {
            if (complication.enabled) {
                complication.renderHighlightLayer(canvas, zonedDateTime, renderParameters)
            }
        }
    }

    override fun render(
        canvas: Canvas, bounds: Rect, zonedDateTime: ZonedDateTime, sharedAssets: WatchSharedAssets
    ) {
        //trickTheComposeViewTrackingLifeCycle()
        renderComposeToBitmap(canvas)

    }


    private fun renderComposeToBitmap(canvas: Canvas) {
        //composeView.setContent(content)

        val widthMeasureSpec =
            View.MeasureSpec.makeMeasureSpec(composeBitmap.width, View.MeasureSpec.EXACTLY)
        val heightMeasureSpec =
            View.MeasureSpec.makeMeasureSpec(composeBitmap.height, View.MeasureSpec.EXACTLY)

        val frameLayout = ComposeHostingView(service)



        frameLayout.measure(widthMeasureSpec, heightMeasureSpec)
        frameLayout.layout(0, 0, composeBitmap.width, composeBitmap.height)
        frameLayout.setContent(content)

        composeBitmap.applyCanvas {
            val backgroundColor = if (renderParameters.drawMode == DrawMode.AMBIENT) {
                watchFaceColors.ambientBackgroundColor
            } else {
                watchFaceColors.activeBackgroundColor
            }
            drawColor(backgroundColor)
            // Draw the ComposeView content onto the Bitmap
            frameLayout.draw(this)
            drawBitmap(composeBitmap, 0f, 0f, null)
        }
        canvas.drawBitmap(composeBitmap, 0f, 0f, null)

    }


    @SuppressLint("MissingInflatedId")
    fun addXmlToFrameLayout(context: Context, frameLayout: FrameLayout) {
        val inflater = LayoutInflater.from(context)
        val childView = inflater.inflate(R.layout.view_test, frameLayout, false)
        val composeView = childView.findViewById<ComposeView>(R.id.compose_view)
        composeView.setContent { content() }
        frameLayout.addView(childView)
    }

    private fun trickTheComposeViewTrackingLifeCycle() {
        val lifecycleOwner = CustomViewLifecycleOwner()
        val scope = CoroutineScope(Dispatchers.Main)
        composeView = ComposeView(service).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
        }
        scope.launch {
            lifecycleOwner.performRestore(null)
            lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        }
        composeView.setViewTreeLifecycleOwner(lifecycleOwner)
        composeView.setViewTreeSavedStateRegistryOwner(lifecycleOwner)
        val text = TextView(service).apply { text = "Hello World\nHello World\nHello World" }
        //windowManager.addView(text, getLayoutPrams())
    }

    private fun getLayoutPrams() = WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        PixelFormat.TRANSLUCENT
    )


    private var watchFaceData: WatchFaceData = WatchFaceData()

    // Converts resource ids into Colors and ComplicationDrawable.
    private var watchFaceColors = WatchFaceColorPalette.convertToWatchFaceColorPalette(
        service, watchFaceData.activeColorStyle, watchFaceData.ambientColorStyle
    )
}

class ComposeHostingView(context: Context) : FrameLayout(context) {
    private val composeView = ComposeView(context)

    init {
        this.addView(composeView)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        composeView.setContent {
            // Your Compose UI content here
        }
    }

    fun setContent(content: @Composable () -> Unit) {
        //composeView.setContent(content)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        composeView.measure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(composeView.measuredWidth, composeView.measuredHeight)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        composeView.layout(0, 0, width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        composeView.draw(canvas)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        composeView.disposeComposition() // Optional, depending on your use case
    }
}