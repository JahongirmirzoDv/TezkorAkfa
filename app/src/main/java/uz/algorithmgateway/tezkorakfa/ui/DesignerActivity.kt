package uz.algorithmgateway.tezkorakfa.ui

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import uz.algorithmgateway.tezkorakfa.databinding.ActivityDesignerBinding
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.DragAndDropListener
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.DesignerLayout
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.sizeview.SizeDialog


class DesignerActivity : AppCompatActivity() {
    var lastView: View? = null

    private lateinit var binding: ActivityDesignerBinding
    var H: Int = 1400
    var W: Int = 2020

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDesignerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setUpDesignerLayout()

        setDragAndDropToViews()
    }

     fun setUpDesignerLayout() {
        val designer: DesignerLayout = binding.layoutDesigner

        designer.apply {
            setH(H)
            setW(W)
//            setFunctionOnSizeViewClick {
//                showSizeDialog()
//            }
        }

        val myLayoutParams = designer.layoutParams as ConstraintLayout.LayoutParams
        myLayoutParams.dimensionRatio = "$W:$H"

    }

    private fun showSizeDialog() {
        SizeDialog().show(supportFragmentManager, "SizeDialog")
    }

    private fun sizeH(H: Int) {
        val designer: DesignerLayout = binding.layoutDesigner
        designer.setH(H)
    }


    private fun setDragAndDropToViews() {
        binding.viewDivideThreeHorizontal.setOnLongClickListener { v ->
            lastView = v
            startDrag(
                v,
                DragAndDropListener.TAG_DIV_HOR_THREE
            )
        }
        binding.viewDivideTwoHorizontal.setOnLongClickListener { v ->
            lastView = v
            startDrag(
                v,
                DragAndDropListener.TAG_DIV_HOR_TWO
            )
        }
        binding.viewDivideTwoVertical.setOnLongClickListener { v ->
            lastView = v
            startDrag(
                v,
                DragAndDropListener.TAG_DIV_VER_TWO
            )
        }
        binding.viewDoorTurnLeft.setOnLongClickListener { v ->
            lastView = v
            startDrag(
                v,
                DragAndDropListener.TAG_DOOR_TURN_LEFT
            )
        }
        binding.viewDoorTurnRight.setOnLongClickListener { v ->
            lastView = v
            startDrag(
                v,
                DragAndDropListener.TAG_DOOR_TURN_RIGHT
            )
        }
        binding.viewPanelHorizontal.setOnLongClickListener { v ->
            lastView = v
            startDrag(
                v,
                DragAndDropListener.TAG_PANEL_HOR
            )
        }
        binding.viewPanelVertical.setOnLongClickListener { v ->
            lastView = v
            startDrag(
                v,
                DragAndDropListener.TAG_PANEL_VER
            )
        }
        binding.viewGlass.setOnLongClickListener { v ->
            lastView = v
            startDrag(
                v,
                DragAndDropListener.TAG_GLASS
            )
        }
        binding.viewWindowTiltTop.setOnLongClickListener { v ->
            lastView = v
            startDrag(
                v,
                DragAndDropListener.TAG_WINDOW_TILT_TOP
            )
        }
        binding.viewWindowTiltTopTurnLeft.setOnLongClickListener { v ->
            lastView = v
            startDrag(
                v,
                DragAndDropListener.TAG_WINDOW_TILT_TOP_TURN_LEFT
            )
        }
        binding.viewWindowTiltTopTurnRight.setOnLongClickListener { v ->
            lastView = v
            startDrag(
                v,
                DragAndDropListener.TAG_WINDOW_TILT_TOP_TURN_RIGHT
            )
        }
        binding.viewWindowTurnLeft.setOnLongClickListener { v ->
            lastView = v
            startDrag(
                v,
                DragAndDropListener.TAG_WINDOW_TURN_LEFT
            )
        }
        binding.viewWindowTurnRight.setOnLongClickListener { v ->
            lastView = v
            startDrag(
                v,
                DragAndDropListener.TAG_WINDOW_TURN_RIGHT
            )
        }
    }

    private fun startDrag(view: View?, tag: String): Boolean {
        view?.let {
            val item = ClipData.Item(tag)
            val data = ClipData(tag, arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), item)
            val shadow = View.DragShadowBuilder(it)

            if (Build.VERSION.SDK_INT >= 24) {
                it.startDragAndDrop(data, shadow, it, 0)
            } else {
                it.startDrag(data, shadow, it, 0)
            }
            return true
        } ?: return false

    }
}