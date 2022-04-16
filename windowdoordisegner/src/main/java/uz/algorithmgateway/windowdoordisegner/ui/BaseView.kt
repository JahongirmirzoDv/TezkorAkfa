package uz.algorithmgateway.windowdoordisegner.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View

open class BaseView @JvmOverloads constructor(
    private val ctx: Context,
    private val attrs: AttributeSet? = null,
    private val defaultAttrs: Int = 0
) : View(ctx, attrs, defaultAttrs) {
    protected var W: Int = 0
    protected var H: Int = 0
    protected var viewScale: Float = 1f

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

}