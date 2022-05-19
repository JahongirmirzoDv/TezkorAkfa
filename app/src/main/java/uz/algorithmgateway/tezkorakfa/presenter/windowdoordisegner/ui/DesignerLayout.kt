package uz.algorithmgateway.tezkorakfa.presenter.windowdoordisegner.ui

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.presenter.windowdoordisegner.ui.sizeview.SizeViewHorizontal
import uz.algorithmgateway.tezkorakfa.presenter.windowdoordisegner.ui.sizeview.SizeViewVertical


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
    private lateinit var onSizeViewClick2: () -> Unit


    init {
        inflate(ctx, R.layout.layout_designer, this)

        viewSizeHorizontal = findViewById(R.id.view_size_horizontal)
        viewSizeHorizontal.textView.setOnClickListener {
            onSizeViewClick.invoke() }
        viewSizeVertical = findViewById(R.id.view_size_vertical)
        viewSizeVertical.textView.setOnClickListener {
            onSizeViewClick2.invoke() }
        viewDesigner = findViewById(R.id.view_designer)

    }

    fun setFunctionOnSizeViewClickH(function: () -> Unit) {
        onSizeViewClick = function
    }
    fun setFunctionOnSizeViewClickV(function: () -> Unit) {
        onSizeViewClick2 = function
    }

    fun setW(number: Int?,text:Int?) {
        if (number != null && number >= 0 && text != null && text >= 0) {
            W = number
            viewSizeHorizontal.setNumber(text)
            viewDesigner.setW(W)
            invalidate()
        }
    }

    fun setH(number: Int?,text:Int?) {
        if (number != null && number >= 0 && text != null && text >= 0) {
            H = number
            viewSizeVertical.setNumber(text)
            viewDesigner.setH(H)
            invalidate()
        }
    }

    fun clearViews() {
        viewDesigner.clearView()
    }


}