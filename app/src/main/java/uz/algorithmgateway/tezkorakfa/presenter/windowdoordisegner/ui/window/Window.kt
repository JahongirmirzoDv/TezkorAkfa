package uz.algorithmgateway.tezkorakfa.presenter.windowdoordisegner.ui.window

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.windowdoordisegner.ui.BaseView
import uz.algorithmgateway.tezkorakfa.presenter.windowdoordisegner.ui.WindowDoorDesigner


class Window @JvmOverloads constructor(
    private val ctx: Context,
    private val attrs: AttributeSet? = null,
    private val defaultAttrs: Int = 0
) : BaseView(ctx, attrs, defaultAttrs) {

    var orientation: WindowOrientation = WindowOrientation.TURN_RIGHT

    var border = borderThickness * viewScale

    companion object {
        const val borderThickness = 70

        const val hingeWidth = 12//oshiq-moshiq eni
        const val hingeHeight = 100//oshiq-moshiq bo'yi

        const val handleWidth = 25 //tutqich eni
        const val handleHeight = 140 //tutqich bo'yi

        enum class WindowOrientation {
            TURN_LEFT,
            TURN_RIGHT,
            TILT_TOP_AND_TURN_LEFT,
            TILT_TOP_AND_TURN_RIGHT,
            TILT_TOP
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        border = borderThickness * viewScale
        drawWindow(canvas)
    }

    private fun drawWindow(canvas: Canvas?) {
        canvas?.apply {
            drawColor(Color.TRANSPARENT)

            val path = Path()
            path.moveTo(0f, 0f)
            path.lineTo(width.toFloat(), 0f)
            path.lineTo(width.toFloat(), height.toFloat())
            path.lineTo(0f, height.toFloat())
            path.lineTo(0f, 1f)
            path.lineTo(border, border + 1)
            path.lineTo(border, height - border)
            path.lineTo(width - border, height - border)
            path.lineTo(width - border, border)
            path.lineTo(border, border)
            path.lineTo(0f, 0f)

            drawPath(path, WindowDoorDesigner.paintFill)

            drawRect(
                0f,
                0f,
                width.toFloat(),
                height.toFloat(),
                WindowDoorDesigner.paintStroke
            )

            drawRect(
                border,
                border,
                width - border,
                height - border,
                WindowDoorDesigner.paintStroke
            )
            drawLine(
                0f,
                0f,
                border,
                border,
                WindowDoorDesigner.paintStroke
            )
            drawLine(
                width.toFloat(),
                0f,
                width - border,
                border,
                WindowDoorDesigner.paintStroke
            )
            drawLine(
                0f,
                height.toFloat(),
                border,
                height - border,
                WindowDoorDesigner.paintStroke
            )
            drawLine(
                width.toFloat(),
                height.toFloat(),
                width - border,
                height - border,
                WindowDoorDesigner.paintStroke
            )

            drawOrientationLines(canvas, orientation)
            drawHandle(canvas, orientation)
            drawHinge(canvas, orientation)
        }
    }

    private fun drawOrientationLines(canvas: Canvas?, orientation: WindowOrientation) {
        canvas?.apply {
            when (orientation) {
                WindowOrientation.TURN_RIGHT,
                WindowOrientation.TILT_TOP_AND_TURN_RIGHT
                -> {
                    canvas.drawLine(
                        width.toFloat(),
                        height / 6f,
                        borderThickness * viewScale / 2,
                        height / 2f,
                        WindowDoorDesigner.paintOrientationLines
                    )
                    canvas.drawLine(
                        width.toFloat(),
                        5 * height / 6f,
                        borderThickness * viewScale / 2,
                        height / 2f,
                        WindowDoorDesigner.paintOrientationLines
                    )
                }
                WindowOrientation.TURN_LEFT,
                WindowOrientation.TILT_TOP_AND_TURN_LEFT
                -> {
                    canvas.drawLine(
                        0f,
                        height / 6f,
                        width - borderThickness * viewScale / 2,
                        height / 2f,
                        WindowDoorDesigner.paintOrientationLines
                    )
                    canvas.drawLine(
                        0f,
                        5 * height / 6f,
                        width - borderThickness * viewScale / 2,
                        height / 2f,
                        WindowDoorDesigner.paintOrientationLines
                    )
                }
            }
            when (orientation) {
                WindowOrientation.TILT_TOP,
                WindowOrientation.TILT_TOP_AND_TURN_LEFT,
                WindowOrientation.TILT_TOP_AND_TURN_RIGHT
                -> {
                    canvas.drawLine(
                        width / 6f,
                        height.toFloat(),
                        width / 2f,
                        border / 2,
                        WindowDoorDesigner.paintOrientationLines
                    )
                    canvas.drawLine(
                        width / 2f,
                        border / 2,
                        5 * width / 6f,
                        height.toFloat(),
                        WindowDoorDesigner.paintOrientationLines
                    )
                }
            }
        }
    }

    //Deraza tutqichi chizish funksiyasi
    private fun drawHandle(canvas: Canvas?, orientation: WindowOrientation) {
        canvas?.apply {
            val handleVectorVertical =
                VectorDrawableCompat.create(
                    ctx.resources,
                    R.drawable.ic_window_handle_vertical,
                    null
                )
            when (orientation) {
                WindowOrientation.TURN_RIGHT,
                WindowOrientation.TILT_TOP_AND_TURN_RIGHT
                -> {
                    handleVectorVertical?.setBounds(
                        ((borderThickness - handleWidth) * viewScale / 2).toInt(),
                        (height - (handleHeight * viewScale).toInt()) / 2,
                        ((borderThickness + handleWidth) * viewScale / 2).toInt(),
                        (height + (handleHeight * viewScale)).toInt() / 2
                    )
                    handleVectorVertical?.draw(canvas)
                }
                WindowOrientation.TURN_LEFT,
                WindowOrientation.TILT_TOP_AND_TURN_LEFT
                -> {
                    handleVectorVertical?.setBounds(
                        width - ((borderThickness + handleWidth) * viewScale / 2).toInt(),
                        (height - (handleHeight * viewScale).toInt()) / 2,
                        width - ((borderThickness - handleWidth) * viewScale / 2).toInt(),
                        (height + (handleHeight * viewScale)).toInt() / 2
                    )
                    handleVectorVertical?.draw(canvas)
                }
                else -> {
                    val handleVectorTop =
                        VectorDrawableCompat.create(
                            ctx.resources,
                            R.drawable.ic_window_handle_horizontal,
                            null
                        )
                    handleVectorTop?.setBounds(
                        ((width - handleHeight * viewScale) / 2).toInt(),
                        ((border - handleWidth * viewScale) / 2).toInt(),
                        ((width + handleHeight * viewScale) / 2).toInt(),
                        ((border + handleWidth * viewScale) / 2).toInt()
                    )
                    handleVectorTop?.draw(canvas)
                }
            }
        }
    }

    //Deraza oshiq moshig'i chizish funksiyasi
    private fun drawHinge(canvas: Canvas?, orientation: WindowOrientation) {
        canvas?.apply {

            //Resursdan oshiq-moshiq vektor yasab olinyapti
            val hingeVector =
                VectorDrawableCompat.create(ctx.resources, R.drawable.ic_hinge_vertical, null)

            when (orientation) {
                WindowOrientation.TURN_RIGHT,
                WindowOrientation.TILT_TOP_AND_TURN_RIGHT
                -> {
                    //1
                    hingeVector?.setBounds(
                        width - (hingeWidth * viewScale).toInt(),
                        height / 6,
                        width,
                        height / 6 + (hingeHeight * viewScale).toInt()
                    )
                    hingeVector?.draw(canvas)

                    //2
                    hingeVector?.setBounds(
                        width - (hingeWidth * viewScale).toInt(),
                        5 * height / 6 - (hingeHeight * viewScale).toInt(),
                        width,
                        5 * height / 6
                    )
                    hingeVector?.draw(canvas)
                }
                WindowOrientation.TURN_LEFT,
                WindowOrientation.TILT_TOP_AND_TURN_LEFT
                -> {
                    //1
                    hingeVector?.setBounds(
                        0,
                        height / 6,
                        (hingeWidth * viewScale).toInt(),
                        height / 6 + (hingeHeight * viewScale).toInt()
                    )
                    hingeVector?.draw(canvas)

                    //2
                    hingeVector?.setBounds(
                        0,
                        (5 * height / 6 - hingeHeight * viewScale).toInt(),
                        (hingeWidth * viewScale).toInt(),
                        5 * height / 6
                    )
                    hingeVector?.draw(canvas)
                }
                else -> {
                    val hingeVectorHorizontal =
                        VectorDrawableCompat.create(
                            ctx.resources,
                            R.drawable.ic_hinge_horizontal,
                            null
                        )

                    //1
                    hingeVectorHorizontal?.setBounds(
                        width / 6,
                        (height - hingeWidth * viewScale).toInt(),
                        (width / 6 + hingeHeight * viewScale).toInt(),
                        height
                    )
                    hingeVectorHorizontal?.draw(canvas)

                    //2
                    hingeVectorHorizontal?.setBounds(
                        (5 * width / 6 - hingeHeight * viewScale).toInt(),
                        (height - hingeWidth * viewScale).toInt(),
                        5 * width / 6,
                        height
                    )
                    hingeVectorHorizontal?.draw(canvas)
                }
            }
        }
    }
}