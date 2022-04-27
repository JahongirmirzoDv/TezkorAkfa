package uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.sizeview


import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import uz.algorithmgateway.tezkorakfa.R

class SizeViewVertical @JvmOverloads constructor(
    private val ctx: Context,
    private val attrs: AttributeSet? = null,
    private val defaultAttrs: Int = 0
) : ConstraintLayout(ctx, attrs, defaultAttrs) {

    val textView: TextView
    private var number: Int = 0

    fun setNumber(newNumber: Int) {
        if (newNumber >= 0) {
            number = newNumber
            textView.text = number.toString()
        }
    }

    fun getNumber(): Int = number

    init {
        inflate(ctx, R.layout.layout_vertical_size_view, this)
        textView = findViewById(R.id.tv_size2)
    }
}