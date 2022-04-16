package uz.algorithmgateway.windowdoordisegner.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.WindowDoorDesigner


class Square @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultAttrs: Int = 0
) : BaseView(context, attrs, defaultAttrs) {

    private var myInsideType: InsideTypes = InsideTypes.GLASS


    companion object {
        const val borderThickness: Int = 30
        const val panelThickness: Int = 100


        enum class InsideTypes {
            GLASS,
            VERTICAL_PANEL,
            HORIZONTAL_PANEL
        }
    }

    fun changeInsideType(insideType: InsideTypes) {
        myInsideType = insideType
        invalidate()
    }


    @SuppressLint("DrawAllocation")
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
                borderThickness * viewScale,
                borderThickness * viewScale,
                width.toFloat() - borderThickness * viewScale,
                height.toFloat() - borderThickness * viewScale,
                WindowDoorDesigner.paintStroke
            )
            drawLine(
                0f,
                0f,
                borderThickness * viewScale,
                borderThickness * viewScale,
                WindowDoorDesigner.paintStroke
            )
            drawLine(
                width.toFloat(),
                0f,
                width.toFloat() - borderThickness * viewScale,
                borderThickness * viewScale,
                WindowDoorDesigner.paintStroke
            )
            drawLine(
                0f,
                height.toFloat(),
                borderThickness * viewScale,
                height.toFloat() - borderThickness * viewScale,
                WindowDoorDesigner.paintStroke
            )
            drawLine(
                width.toFloat(),
                height.toFloat(),
                width.toFloat() - borderThickness * viewScale,
                height.toFloat() - borderThickness * viewScale,
                WindowDoorDesigner.paintStroke
            )

            when (myInsideType) {
                InsideTypes.HORIZONTAL_PANEL -> {
                    val x0 = borderThickness * viewScale
                    val x1 = width.toFloat() - borderThickness * viewScale
                    var yStart = borderThickness * viewScale
                    val yEnd = height.toFloat() - borderThickness * viewScale
                    while (yEnd > yStart) {
                        drawLine(
                            x0,
                            yStart,
                            x1,
                            yStart,
                            WindowDoorDesigner.paintStroke
                        )
                        yStart += panelThickness * viewScale
                    }
                }
                InsideTypes.VERTICAL_PANEL -> {
                    val y0 = borderThickness * viewScale
                    val y1 = height.toFloat() - borderThickness * viewScale
                    var xStart = borderThickness * viewScale
                    val xEnd = width.toFloat() - borderThickness * viewScale
                    while (xEnd > xStart) {
                        drawLine(
                            xStart,
                            y0,
                            xStart,
                            y1,
                            WindowDoorDesigner.paintStroke
                        )
                        xStart += panelThickness * viewScale
                    }
                }
                else -> {
                    val paint = Paint().apply {
                        shader = LinearGradient(
                            0f,
                            0f,
                            width.toFloat(),
                            height.toFloat(),
                            Color.rgb(221, 242, 247),
                            Color.rgb(131, 213, 237),
                            Shader.TileMode.CLAMP
                        )
                    }
                    drawRect(
                        borderThickness * viewScale + 1f,
                        borderThickness * viewScale + 1f,
                        width.toFloat() - borderThickness * viewScale - 1f,
                        height.toFloat() - borderThickness * viewScale - 1f,
                        paint
                    )
                }
            }

        }
    }
}