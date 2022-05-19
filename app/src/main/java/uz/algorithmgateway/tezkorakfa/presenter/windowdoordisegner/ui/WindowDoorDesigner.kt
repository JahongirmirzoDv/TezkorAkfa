package uz.algorithmgateway.tezkorakfa.presenter.windowdoordisegner.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.setPadding
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.core.util.layoutInflater
import uz.algorithmgateway.tezkorakfa.databinding.ActivityDesignerBinding


class WindowDoorDesigner @JvmOverloads constructor(
    private val ctx: Context,
    private val attrs: AttributeSet? = null,
    private val defaultAttrs: Int = 0
) : LinearLayout(ctx, attrs, defaultAttrs) {

    private var W: Int = 0
    private var H: Int = 0
    private var viewScale: Float = 0.0f
    private var childLayoutDesigner: LinearLayout
//    private lateinit var binding: ActivityDesignerBinding
//    val designer: DesignerLayout = binding.layoutDesigner

    companion object {
        val paintStroke = Paint().apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeWidth = 1f
        }
        val paintFill = Paint().apply {
            color = Color.WHITE
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        val paintOrientationLines = Paint().apply {
            color = Color.BLUE
            style = Paint.Style.STROKE
            isAntiAlias = true
            strokeWidth = 1f
        }
    }

    init {
//        binding = ActivityDesignerBinding.inflate(layoutInflater)
        inflate(ctx, R.layout.file_disegner, this)

        setBackgroundColor(Color.WHITE)

        childLayoutDesigner = findViewById(R.id.child_layout)
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

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        viewScale = width.toFloat() / W
        drawFrame(canvas)
        addArea()
    }

    private fun drawFrame(canvas: Canvas?) {

        setPadding((viewScale * Frame.thickness).toInt())

        canvas?.apply {
            drawColor(Color.WHITE)
            drawRect(
                0f,
                0f,
                width.toFloat(),
                height.toFloat(),
                paintStroke
            )
            drawRect(
                Frame.thickness * viewScale,
                Frame.thickness * viewScale,
                width.toFloat() - Frame.thickness * viewScale,
                height.toFloat() - Frame.thickness * viewScale,
                paintStroke
            )
            drawLine(
                0f,
                0f,
                Frame.thickness * viewScale,
                Frame.thickness * viewScale,
                paintStroke
            )
            drawLine(
                width.toFloat(),
                0f,
                width.toFloat() - Frame.thickness * viewScale,
                Frame.thickness * viewScale,
                paintStroke
            )
            drawLine(
                0f,
                height.toFloat(),
                Frame.thickness * viewScale,
                height.toFloat() - Frame.thickness * viewScale,
                paintStroke
            )
            drawLine(
                width.toFloat(),
                height.toFloat(),
                width.toFloat() - Frame.thickness * viewScale,
                height.toFloat() - Frame.thickness * viewScale,
                paintStroke
            )
        }

    }

    private fun addArea() {
        val area = Area(ctx)
        area.setH(H)
        area.setW(W)
        area.setViewScale(viewScale)
        area.canAddDoorOrWindow = true

        childLayoutDesigner.addView(area)
    }

    fun clearView() {
        childLayoutDesigner.removeAllViews()
        childLayoutDesigner.removeAllViewsInLayout()
    }


}
