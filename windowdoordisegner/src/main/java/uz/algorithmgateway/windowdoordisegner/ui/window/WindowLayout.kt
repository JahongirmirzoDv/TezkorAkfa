package uz.algorithmgateway.windowdoordisegner.ui.window

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.setPadding
import uz.algorithmgateway.windowdoordisegner.R
import uz.algorithmgateway.windowdoordisegner.ui.Area
import uz.algorithmgateway.windowdoordisegner.ui.door.Door

class WindowLayout @JvmOverloads constructor(
    private val ctx: Context,
    private val attrs: AttributeSet? = null,
    private val defaultAttrs: Int = 0
) : FrameLayout(ctx, attrs, defaultAttrs) {
    private var window: Window
    private var area: Area
    private var orientation: Window.Companion.WindowOrientation =
        Window.Companion.WindowOrientation.TURN_RIGHT
    private var viewScale: Float = 0f
    private var W: Int = 0
    private var H: Int = 0

    init {
        inflate(ctx, R.layout.file_window, this)
        window = findViewById(R.id.view_door)
        area = findViewById(R.id.view_area)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        window.setH(H)
        window.setW(W)
        window.setViewScale(viewScale)
        window.orientation = orientation
        area.setPadding((Window.borderThickness * viewScale).toInt())
        area.setH(H - 2 * Window.borderThickness)
        area.setW(W - 2 * Window.borderThickness)
        area.setViewScale(viewScale)
        area.canAddDoorOrWindow = false
    }

    fun setW(number: Int?) {
        if (number != null && number >= 0) {
            W = number
            invalidate()
        }
    }

    fun setH(number: Int?) {
        if (number != null && number >= 0) {
            H = number
            invalidate()
        }
    }

    fun setViewScale(number: Float?) {
        if (number != null && number >= 0f) {
            viewScale = number
            invalidate()
        }
    }

    fun setOrientation(orientation: Window.Companion.WindowOrientation) {
        this.orientation = orientation
        invalidate()
    }


}