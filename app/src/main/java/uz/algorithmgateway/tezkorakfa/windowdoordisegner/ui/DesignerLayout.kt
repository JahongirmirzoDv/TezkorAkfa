package uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.sizeview.SizeViewHorizontal
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.sizeview.SizeViewVertical


class DesignerLayout @JvmOverloads constructor(
    private val ctx: Context,
    attrs: AttributeSet? = null,
    defaultAttrs: Int = 0
) : ConstraintLayout(ctx, attrs, defaultAttrs) {
    private var H: Int = 0
    private var W: Int = 0
    private var viewSizeHorizontal: SizeViewHorizontal
    private var viewSizeVertical: SizeViewVertical
    private var viewDesigner: WindowDoorDesigner
    private lateinit var onSizeViewClick: () -> Unit


    init {
        inflate(ctx, R.layout.layout_designer, this)

        viewSizeHorizontal = findViewById(R.id.view_size_horizontal)
        viewSizeHorizontal.textView.setOnClickListener { onSizeViewClick.invoke() }
        viewSizeVertical = findViewById(R.id.view_size_vertical)
        viewSizeVertical.textView.setOnClickListener { onSizeViewClick.invoke() }
        viewDesigner = findViewById(R.id.view_designer)

    }

    fun setFunctionOnSizeViewClick(function: () -> Unit) {
        onSizeViewClick = function
    }

    fun setW(number: Int?) {
        if (number != null && number >= 0) {
            W = number
            viewSizeHorizontal.setNumber(W)
            viewDesigner.setW(W)
            invalidate()
        }
    }

    fun setH(number: Int?) {
        if (number != null && number >= 0) {
            H = number
            viewSizeVertical.setNumber(H)
            viewDesigner.setH(H)
            invalidate()
        }
    }

}