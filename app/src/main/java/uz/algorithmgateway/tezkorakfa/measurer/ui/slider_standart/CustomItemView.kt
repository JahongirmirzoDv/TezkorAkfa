package uz.algorithmgateway.measurer.ui.slider_standart

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View

class CustomItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    override fun setLayerPaint(paint: Paint?) {
        super.setLayerPaint(paint)
        paint?.alpha = 0
    }

    override fun setAutofillHints(vararg autofillHints: String?) {
        super.setAutofillHints(*autofillHints)
    }

    override fun setOnGenericMotionListener(l: OnGenericMotionListener?) {
        super.setOnGenericMotionListener(l)

    }

}