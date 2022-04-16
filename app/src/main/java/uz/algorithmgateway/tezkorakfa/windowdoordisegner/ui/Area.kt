package uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.get
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.windowdoordisegner.DragAndDropListener
import uz.algorithmgateway.windowdoordisegner.ui.Square
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.door.Door
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.door.DoorLayout
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.window.Window
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.window.WindowLayout

class Area @JvmOverloads constructor(
    private val ctx: Context,
    private val attrs: AttributeSet? = null,
    private val defaultAttrs: Int = 0
) : LinearLayout(ctx, attrs, defaultAttrs) {

    private var childLayout: LinearLayout? = null
    private var viewScale: Float = 0f
    private var W: Int = 0
    private var H: Int = 0
    var canAddDoorOrWindow = false

    init {

        inflate(ctx, R.layout.file_disegner, this)

        setBackgroundColor(Color.WHITE)

        childLayout = findViewById<View>(R.id.child_layout) as LinearLayout

        setOnDragListener(DragAndDropListener())

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addSquare()
    }


    private fun addSquare() {
        val square = Square(ctx)
        square.setH(H)
        square.setW(W)
        square.setViewScale(viewScale)
        square.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        childLayout?.addView(square, 0)
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

    fun addDoor(orientation: Door.Companion.DoorOrientation) {
        if(canAddDoorOrWindow){
            val door = DoorLayout(ctx)
            door.setViewScale(viewScale)
            door.setH(H)
            door.setW(W)
            door.setOrientation(orientation)
            door.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

            childLayout?.removeAllViews()
            childLayout?.addView(door)
        }
    }

    fun addWindow(orientation: Window.Companion.WindowOrientation) {
        if(canAddDoorOrWindow){
            val window = WindowLayout(ctx)
            window.setViewScale(viewScale)
            window.setW(W)
            window.setH(H)
            window.setOrientation(orientation)
            window.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

            childLayout?.removeAllViews()
            childLayout?.addView(window)
        }
    }


    fun divideHorizontalTwo() {
        val layout = LayoutInflater.from(ctx)
            .inflate(R.layout.item_two_square_horizontal, this, false)

        val subArea1 = layout.findViewById<View>(R.id.sub_layout_1) as Area
        val subArea2 = layout.findViewById<View>(R.id.sub_layout_2) as Area

        val mullion = layout.findViewById<View>(R.id.mullion) as Mullion

        val halfW: Int = (W - Mullion.thickness) / 2
        subArea1.setViewScale(this.viewScale)
        subArea2.setViewScale(this.viewScale)
        subArea1.setW(halfW)
        subArea2.setW(W - Mullion.thickness - halfW)
        subArea1.setH(this.H)
        subArea2.setH(this.H)
        subArea1.canAddDoorOrWindow = canAddDoorOrWindow
        subArea2.canAddDoorOrWindow = canAddDoorOrWindow

        mullion.setViewScale(viewScale)

        childLayout?.removeAllViews()
        childLayout?.addView(layout)
    }

    fun divideVerticalTwo() {
        val layout = LayoutInflater.from(ctx)
            .inflate(R.layout.item_two_square_vertical, this, false)

        val subArea1 = layout.findViewById<View>(R.id.sub_layout_1) as Area
        val subArea2 = layout.findViewById<View>(R.id.sub_layout_2) as Area

        val mullion = layout.findViewById<View>(R.id.mullion) as Mullion

        val halfH: Int = (H - Mullion.thickness) / 2
        subArea1.setViewScale(viewScale)
        subArea2.setViewScale(viewScale)
        subArea1.setW(W)
        subArea2.setW(W)
        subArea1.setH(halfH)
        subArea2.setH(H - halfH - Mullion.thickness)
        subArea1.canAddDoorOrWindow = canAddDoorOrWindow
        subArea2.canAddDoorOrWindow = canAddDoorOrWindow

        mullion.setViewScale(viewScale)

        childLayout?.removeAllViews()
        childLayout?.addView(layout)
    }

    fun divideHorizontalThree() {
        val layout = LayoutInflater.from(ctx)
            .inflate(R.layout.item_three_square_horizontal, this, false)

        val subArea1 = layout.findViewById<View>(R.id.sub_layout_1) as Area
        val subArea2 = layout.findViewById<View>(R.id.sub_layout_2) as Area
        val subArea3 = layout.findViewById<View>(R.id.sub_layout_3) as Area

        val mullion1 = layout.findViewById<View>(R.id.mullion_1) as Mullion
        val mullion2 = layout.findViewById<View>(R.id.mullion_2) as Mullion

        val squareW: Int = (W - 2 * Mullion.thickness) / 3
        subArea1.setViewScale(viewScale)
        subArea2.setViewScale(viewScale)
        subArea3.setViewScale(viewScale)
        subArea1.setW(squareW)
        subArea2.setW(squareW)
        subArea3.setW(W - 2 * Mullion.thickness - 2 * squareW)
        subArea1.setH(H)
        subArea2.setH(H)
        subArea3.setH(H)
        subArea1.canAddDoorOrWindow = canAddDoorOrWindow
        subArea2.canAddDoorOrWindow = canAddDoorOrWindow
        subArea3.canAddDoorOrWindow = canAddDoorOrWindow


        mullion1.setViewScale(viewScale)
        mullion2.setViewScale(viewScale)

        childLayout?.removeAllViews()
        childLayout?.addView(layout)
    }

    fun changeInsideSquare(insideType: Square.Companion.InsideTypes) {
        val square = childLayout?.get(0) as Square?
        square?.changeInsideType(insideType)
    }
}