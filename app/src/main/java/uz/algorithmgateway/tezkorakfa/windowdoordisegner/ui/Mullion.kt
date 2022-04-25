package uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.windowdoordisegner.ui.BaseView

class Mullion @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultAttrs: Int = 0
) : BaseView(context, attrs, defaultAttrs) {

    var viewOrientation: ViewOrientation = ViewOrientation.HORIZONTAL
    var length: Int = 0


    companion object {
        const val thickness: Int = 40

        enum class ViewOrientation {
            HORIZONTAL,
            VERTICAL;

            companion object {
                fun getOrientation(o: Int): ViewOrientation =
                    when (o) {
                        1 -> VERTICAL
                        else -> HORIZONTAL
                    }
            }
        }
    }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.Mullion,
            0, 0
        ).apply {

            try {
                viewOrientation = ViewOrientation.getOrientation(
                    getInt(R.styleable.Mullion_view_orientation, 0)
                )
            } finally {
                recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val reqWidth = MeasureSpec.getSize(widthMeasureSpec)
        val reqHeight = MeasureSpec.getSize(heightMeasureSpec)
        if (viewOrientation == ViewOrientation.HORIZONTAL) {
            setMeasuredDimension(reqWidth, (viewScale * thickness).toInt())
            length = reqWidth
        } else {
            setMeasuredDimension((viewScale * thickness).toInt(), reqHeight)
            length = reqHeight
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {
            drawRect(0f, 0f, width.toFloat(), height.toFloat(), WindowDoorDesigner.paintStroke)
        }
    }
}