package uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.sizeview


import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import uz.algorithmgateway.tezkorakfa.R

class SizeViewHorizontal @JvmOverloads constructor(
    private val ctx: Context,
    private val attrs: AttributeSet? = null,
    private val defaultAttrs: Int = 0
) : ConstraintLayout(ctx, attrs, defaultAttrs) {

    var textView: TextView
    private var number: Int = 0

    init {
        inflate(ctx, R.layout.layout_horizontal_size_view, this)
        textView = findViewById(R.id.tv_size)
    }


    fun setNumber(newNumber: Int) {
        if (newNumber >= 0) {
            number = newNumber
            textView.text = number.toString()

            val text = textView.text.toString()
            Log.d("TAG777", "text : $text")

        }
    }

    fun getNumber(): Int = number


}