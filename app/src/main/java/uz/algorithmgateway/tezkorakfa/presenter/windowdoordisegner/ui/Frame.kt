package uz.algorithmgateway.tezkorakfa.presenter.windowdoordisegner.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.windowdoordisegner.ui.BaseView

class Frame @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultAttrs: Int = 0
) : BaseView(context, attrs, defaultAttrs) {


    companion object {
        const val thickness: Int = 40

    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Frame,
            0, 0
        ).apply {

            try {
                setViewScale(getFloat(R.styleable.Frame_android_layout_scale, 0f))
            } finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {
            drawColor(Color.WHITE)
            drawRect(
                0f,
                0f,
                width.toFloat(),
                height.toFloat(),
                WindowDoorDesigner.paintStroke
            )
            drawRect(
                thickness * viewScale,
                thickness * viewScale,
                width.toFloat() - thickness * viewScale,
                height.toFloat() - thickness * viewScale,
                WindowDoorDesigner.paintStroke
            )
            drawLine(
                0f,
                0f,
                thickness * viewScale,
                thickness * viewScale,
                WindowDoorDesigner.paintStroke
            )
            drawLine(
                width.toFloat(),
                0f,
                width.toFloat() - thickness * viewScale,
                thickness * viewScale,
                WindowDoorDesigner.paintStroke
            )
            drawLine(
                0f,
                height.toFloat(),
                thickness * viewScale,
                height.toFloat() - thickness * viewScale,
                WindowDoorDesigner.paintStroke
            )
            drawLine(
                width.toFloat(),
                height.toFloat(),
                width.toFloat() - thickness * viewScale,
                height.toFloat() - thickness * viewScale,
                WindowDoorDesigner.paintStroke
            )
        }
    }
}