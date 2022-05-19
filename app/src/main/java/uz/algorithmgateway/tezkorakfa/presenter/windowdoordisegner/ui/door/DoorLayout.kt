package uz.algorithmgateway.tezkorakfa.presenter.windowdoordisegner.ui.door

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.setPadding
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.presenter.windowdoordisegner.ui.Area
import uz.algorithmgateway.tezkorakfa.presenter.windowdoordisegner.ui.door.Door

class DoorLayout @JvmOverloads constructor(
    private val ctx: Context,
    private val attrs: AttributeSet? = null,
    private val defaultAttrs: Int = 0
) : FrameLayout(ctx, attrs, defaultAttrs) {
    private var door: Door
    private var area: Area
    private var orientation: Door.Companion.DoorOrientation = Door.Companion.DoorOrientation.TURN_RIGHT
    private var viewScale: Float = 0f
    private var W: Int = 0
    private var H: Int = 0

    init {
        inflate(ctx, R.layout.file_door, this)
        door = findViewById(R.id.view_door)
        area = findViewById(R.id.view_area)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        door.setW(W)
        door.setH(H)
        door.setViewScale(viewScale)
        door.orientation = orientation
        area.setPadding((Door.borderThickness * viewScale).toInt())
        area.setH(H - 2 * Door.borderThickness)
        area.setW(W - 2 * Door.borderThickness)
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

    fun setOrientation(orientation: Door.Companion.DoorOrientation) {
        this.orientation = orientation
        invalidate()
    }


}