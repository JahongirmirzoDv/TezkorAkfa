package uz.algorithmgateway.tezkorakfa.presenter.windowdoordisegner.ui.door

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.windowdoordisegner.ui.BaseView
import uz.algorithmgateway.tezkorakfa.presenter.windowdoordisegner.ui.WindowDoorDesigner


class Door @JvmOverloads constructor(
    private val ctx: Context,
    private val attrs: AttributeSet? = null,
    private val defaultAttrs: Int = 0
) : BaseView(ctx, attrs, defaultAttrs) {

    var orientation: DoorOrientation = DoorOrientation.TURN_RIGHT

    var border = borderThickness * viewScale

    companion object {
        const val borderThickness = 90

        const val hingeWidth = 12//oshiq-moshiq eni
        const val hingeHeight = 100//oshiq-moshiq bo'yi

        const val handleWidth = 108 //tutqich eni
        const val handleHeight = 150 //tutqich bo'yi
        const val handleThickness = 30 //tutqichning eshikka qotiriladigan qismi eni

        enum class DoorOrientation {
            TURN_LEFT,
            TURN_RIGHT
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        border = borderThickness * viewScale
        drawDoor(canvas)
    }

    private fun drawDoor(canvas: Canvas?) {
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

    private fun drawOrientationLines(canvas: Canvas, orientation: DoorOrientation) {
        canvas.apply {
            when (orientation) {
                DoorOrientation.TURN_RIGHT -> {
                    canvas.drawLine(
                        width.toFloat(),
                        height / 6f,
                        borderThickness * viewScale / 2,
                        height / 3f,
                        WindowDoorDesigner.paintOrientationLines
                    )
                    canvas.drawLine(
                        borderThickness * viewScale / 2,
                        height / 3f,
                        borderThickness * viewScale / 2,
                        2 * height / 3f,
                        WindowDoorDesigner.paintOrientationLines
                    )
                    canvas.drawLine(
                        width.toFloat(),
                        5 * height / 6f,
                        borderThickness * viewScale / 2,
                        2 * height / 3f,
                        WindowDoorDesigner.paintOrientationLines
                    )
                }
                DoorOrientation.TURN_LEFT -> {
                    canvas.drawLine(
                        0f,
                        height / 6f,
                        width - borderThickness * viewScale / 2,
                        height / 3f,
                        WindowDoorDesigner.paintOrientationLines
                    )
                    canvas.drawLine(
                        width - borderThickness * viewScale / 2,
                        height / 3f,
                        width - borderThickness * viewScale / 2,
                        2 * height / 3f,
                        WindowDoorDesigner.paintOrientationLines
                    )
                    canvas.drawLine(
                        0f,
                        5 * height / 6f,
                        width - borderThickness * viewScale / 2,
                        2 * height / 3f,
                        WindowDoorDesigner.paintOrientationLines
                    )
                }
            }
        }
    }

    //Eshik tutqichi chizish funksiyasi
    private fun drawHandle(canvas: Canvas?, orientation: DoorOrientation) {
        canvas?.apply {
            when (orientation) {
                DoorOrientation.TURN_RIGHT -> {
                    val handleVectorRight =
                        VectorDrawableCompat.create(
                            ctx.resources,
                            R.drawable.ic_door_handle_right,
                            null
                        )
                    handleVectorRight?.setBounds(
                        ((borderThickness - handleThickness) * viewScale / 2).toInt(),
                        (height - (handleHeight * viewScale).toInt()) / 2,
                        ((handleWidth + (borderThickness - handleThickness) / 2) * viewScale).toInt(),
                        (height + (handleHeight * viewScale)).toInt() / 2
                    )
                    handleVectorRight?.draw(canvas)
                }
                DoorOrientation.TURN_LEFT -> {
                    val handleVectorLeft =
                        VectorDrawableCompat.create(
                            ctx.resources,
                            R.drawable.ic_door_handle_left,
                            null
                        )
                    handleVectorLeft?.setBounds(
                        width - ((handleWidth + (borderThickness - handleThickness) / 2) * viewScale).toInt(),
                        (height - (handleHeight * viewScale).toInt()) / 2,
                        width - ((borderThickness - handleThickness) * viewScale / 2).toInt(),
                        (height + (handleHeight * viewScale)).toInt() / 2
                    )
                    handleVectorLeft?.draw(canvas)
                }
            }
        }
    }

    //Eshik oshiq moshig'i chizish funksiyasi
    private fun drawHinge(canvas: Canvas?, orientation: DoorOrientation) {
        canvas?.apply {

            //Resursdan oshiq-moshiq vektor yasab olinyapti
            val hingeVector =
                VectorDrawableCompat.create(ctx.resources, R.drawable.ic_hinge_vertical, null)

            when (orientation) {
                DoorOrientation.TURN_RIGHT -> {
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
                        (height - (hingeHeight * viewScale).toInt()) / 2,
                        width,
                        (height + (hingeHeight * viewScale).toInt()) / 2
                    )
                    hingeVector?.draw(canvas)

                    //3
                    hingeVector?.setBounds(
                        width - (hingeWidth * viewScale).toInt(),
                        5 * height / 6 - (hingeHeight * viewScale).toInt(),
                        width,
                        5 * height / 6
                    )
                    hingeVector?.draw(canvas)
                }
                else -> {
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
                        (height - (hingeHeight * viewScale).toInt()) / 2,
                        (hingeWidth * viewScale).toInt(),
                        (height + (hingeHeight * viewScale).toInt()) / 2
                    )
                    hingeVector?.draw(canvas)

                    //3
                    hingeVector?.setBounds(
                        0,
                        5 * height / 6 - (hingeHeight * viewScale).toInt(),
                        (hingeWidth * viewScale).toInt(),
                        5 * height / 6
                    )
                    hingeVector?.draw(canvas)
                }
            }
        }
    }
}