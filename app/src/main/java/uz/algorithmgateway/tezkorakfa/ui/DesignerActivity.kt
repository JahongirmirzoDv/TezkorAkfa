package uz.algorithmgateway.tezkorakfa.ui

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import uz.algorithmgateway.tezkorakfa.databinding.ActivityDesignerBinding
import uz.algorithmgateway.windowdoordisegner.DragAndDropListener
import uz.algorithmgateway.windowdoordisegner.ui.DesignerLayout
import uz.algorithmgateway.windowdoordisegner.ui.sizeview.SizeDialog

/**
 * Created by Abrorjon Berdiyorov on 22.02.2022
 */

class DesignerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDesignerBinding
    var H: Int = 1300
    var W: Int = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDesignerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setUpDesignerLayout()

        setDragAndDropToViews()
    }

    private fun setUpDesignerLayout() {
        val designer: DesignerLayout = binding.layoutDesigner

        designer.apply {
            setH(H)
            setW(W)
            setFunctionOnSizeViewClick{
                showSizeDialog()
            }
        }

        val myLayoutParams = designer.layoutParams as ConstraintLayout.LayoutParams
        myLayoutParams.dimensionRatio = "$W:$H"

    }

    private fun showSizeDialog() {
        SizeDialog().show(supportFragmentManager, "SizeDialog")
    }

    private fun setDragAndDropToViews() {
        binding.viewDivideThreeHorizontal.setOnLongClickListener { v ->
            startDrag(
                v,
                DragAndDropListener.TAG_DIV_HOR_THREE
            )
        }
        binding.viewDivideTwoHorizontal.setOnLongClickListener { v ->
            startDrag(
                v,
                DragAndDropListener.TAG_DIV_HOR_TWO
            )
        }
        binding.viewDivideTwoVertical.setOnLongClickListener { v ->
            startDrag(
                v,
                DragAndDropListener.TAG_DIV_VER_TWO
            )
        }
        binding.viewDoorTurnLeft.setOnLongClickListener { v ->
            startDrag(
                v,
                DragAndDropListener.TAG_DOOR_TURN_LEFT
            )
        }
        binding.viewDoorTurnRight.setOnLongClickListener { v ->
            startDrag(
                v,
                DragAndDropListener.TAG_DOOR_TURN_RIGHT
            )
        }
        binding.viewPanelHorizontal.setOnLongClickListener { v ->
            startDrag(
                v,
                DragAndDropListener.TAG_PANEL_HOR
            )
        }
        binding.viewPanelVertical.setOnLongClickListener { v ->
            startDrag(
                v,
                DragAndDropListener.TAG_PANEL_VER
            )
        }
        binding.viewGlass.setOnLongClickListener { v ->
            startDrag(
                v,
                DragAndDropListener.TAG_GLASS
            )
        }
        binding.viewWindowTiltTop.setOnLongClickListener { v ->
            startDrag(
                v,
                DragAndDropListener.TAG_WINDOW_TILT_TOP
            )
        }
        binding.viewWindowTiltTopTurnLeft.setOnLongClickListener { v ->
            startDrag(
                v,
                DragAndDropListener.TAG_WINDOW_TILT_TOP_TURN_LEFT
            )
        }
        binding.viewWindowTiltTopTurnRight.setOnLongClickListener { v ->
            startDrag(
                v,
                DragAndDropListener.TAG_WINDOW_TILT_TOP_TURN_RIGHT
            )
        }
        binding.viewWindowTurnLeft.setOnLongClickListener { v ->
            startDrag(
                v,
                DragAndDropListener.TAG_WINDOW_TURN_LEFT
            )
        }
        binding.viewWindowTurnRight.setOnLongClickListener { v ->
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