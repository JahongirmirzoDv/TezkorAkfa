package uz.algorithmgateway.tezkorakfa.measurer.ui.slider_standart

import android.content.ClipData
import android.content.ClipDescription
import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.ScreenSliderBinding
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.DragAndDropListener
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.Area
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.DesignerLayout
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.sizeview.SizeDialog
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


class SliderScreen : Fragment() {

    private var _binding: ScreenSliderBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    lateinit var dragAndDropListener: Area

    var lastView: View? = null
    var H: Int = 1300
    var W: Int = 2000


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ScreenSliderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationButtons()
        dragAndDropListener = Area(requireContext())

        setUpDesignerLayout()
        setDragAndDropToViews()
    }

    private fun navigationButtons() {

        binding.floatingNext.setOnClickListener {
            navController.navigate(R.id.confirmOrdersScreen)
            val saveImage = saveImage(binding.root)
            saveBitmap(saveImage, "rasm")
            dragAndDropListener.removeWindow()
        }

        binding.floatingBack.setOnClickListener {
            navController.navigateUp()
        }
    }

    private fun saveImage(view: View): Bitmap {
        val specWidth =
            View.MeasureSpec.makeMeasureSpec(view.measuredWidth, View.MeasureSpec.AT_MOST)
        val specHeight =
            View.MeasureSpec.makeMeasureSpec(view.measuredHeight, View.MeasureSpec.AT_MOST)
        view.measure(specWidth, specHeight)
        val width = view.measuredWidth
        val height = view.measuredHeight
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.layout(view.left, view.top, view.right, view.bottom)
        view.draw(canvas)
        return bitmap
    }

    @Throws(IOException::class)
    private fun saveBitmap(bitmap: Bitmap, name: String) {
        val saved: Boolean
        val fos: OutputStream?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver: ContentResolver = requireActivity().contentResolver
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/Camera")
            val imageUri: Uri? =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM).toString() + File.separator + "Camera"
            val file = File(imagesDir)
            if (!file.exists()) {
                file.mkdir()
            }
            val image = File(imagesDir, "$name.png")
            fos = FileOutputStream(image)
        }
        saved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos?.flush()
        fos?.close()
    }


    private fun setUpDesignerLayout() {
        val designer: DesignerLayout = binding.layoutDesigner

        designer.apply {
            setH(H)
            setW(W)
            setFunctionOnSizeViewClick {
                showSizeDialog()
            }
        }

        val myLayoutParams = designer.layoutParams as ConstraintLayout.LayoutParams
        myLayoutParams.dimensionRatio = "$W:$H"

    }

    private fun showSizeDialog() {
        SizeDialog().show(childFragmentManager, "SizeDialog")
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