package uz.algorithmgateway.tezkorakfa.measurer.ui.slider_standart

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription
import android.content.ContentResolver
import android.content.ContentValues
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.PixelCopy
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.databinding.LayoutChangeSizeDialogBinding
import uz.algorithmgateway.tezkorakfa.databinding.ScreenSliderBinding
import uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.models.Drawing
import uz.algorithmgateway.tezkorakfa.measurer.viewmodel.DbViewmodel
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.DragAndDropListener
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.Area
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.DesignerLayout
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import javax.inject.Inject


class SliderScreen : Fragment() {

    @Inject
    lateinit var dbViewmodel: DbViewmodel
    lateinit var id: String
    lateinit var dr_id: String
    private var _binding: ScreenSliderBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    lateinit var dragAndDropListener: Area
    private val TAG = "SliderScreen"
    var sliderObj = SliderScreen()
    private lateinit var dialogBinding: LayoutChangeSizeDialogBinding
    private var customHorVer: Boolean? = null
    var drawing: Drawing? = null
    private var imageName: String = ""

    var lastView: View? = null
    var H: Int = 1300
    var W: Int = 2000
    var H_T: Int = 1300
    var W_T: Int = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.sliderScreen(this)
//        DialogSize(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ScreenSliderBinding.inflate(inflater, container, false)
        drawing = dbViewmodel.getAllDrawing().last()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.projectId.text = "Loyiha ${drawing?.id?.ifEmpty { "" }}"
        verifyStoragePermission(requireActivity())
        navigationButtons()
        dragAndDropListener = Area(requireContext())

        setUpDesignerLayout()
        setDragAndDropToViews()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun navigationButtons() {

        binding.floatingNext.setOnClickListener {
            navController.navigate(R.id.drawingsFragment)

            saveCustomview()
//            getBitmapFromView(binding.layoutDesigner, requireActivity(), callback = {
//                saveBitmap(it, "new api", drawing)
//            })
//            toast("Chizma galareyaga saqlandi")
        }
        binding.apply {
            floatingBack.setOnClickListener {
                navController.navigateUp()
            }
            clear.setOnClickListener {
                clearView()
            }

        }
    }

    private fun saveCustomview() {
        imageName = System.currentTimeMillis().toString()
        binding.layoutDesigner.setDrawingCacheEnabled(true);
        val bitmap = binding.layoutDesigner.getDrawingCache()

        Log.d(
            "MY777",
            "IMAGE URL " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                .toString() + "/Operation.pdf"
        )
        bitmap.compress(
            Bitmap.CompressFormat.JPEG,
            90,
            object : FileOutputStream(File("/storage/emulated/0/DCIM/${imageName}image.jpg")) {
            })

        drawing?.projet_image_path = "/storage/emulated/0/DCIM/${imageName}image.jpg"
        drawing?.width = W_T
        drawing?.heigth = H_T
        drawing?.let { dbViewmodel.updateDrawing(it) }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getBitmapFromView(view: View, activity: Activity, callback: (Bitmap) -> Unit) {
        activity.window?.let { window ->
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val locationOfViewInWindow = IntArray(2)
            view.getLocationInWindow(locationOfViewInWindow)
            try {
                PixelCopy.request(
                    window,
                    Rect(
                        locationOfViewInWindow[0],
                        locationOfViewInWindow[1],
                        locationOfViewInWindow[0] + view.width,
                        locationOfViewInWindow[1] + view.height
                    ),
                    bitmap,
                    { copyResult ->
                        if (copyResult == PixelCopy.SUCCESS) {
                            callback(bitmap)
                        }
                        // possible to handle other result codes ...
                    },
                    Handler()
                )
            } catch (e: IllegalArgumentException) {
                // PixelCopy may throw IllegalArgumentException, make sure to handle it
                e.printStackTrace()
            }
        }
    }

    private val PERMISSION_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    fun verifyStoragePermission(activity: Activity) {
        val permission = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity, PERMISSION_STORAGE, 1
            )
        }
    }

    @Throws(IOException::class)
    private fun saveBitmap(bitmap: Bitmap, name: String, drawing: Drawing) {
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
            drawing.width = W_T
            drawing.heigth = H_T
            drawing.projet_image_path = imageUri.toString()
            dbViewmodel.updateDrawing(drawing)
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM
            ).toString() + File.separator + "Camera"
            val file = File(imagesDir)
            if (!file.exists()) {
                file.mkdir()
            }
            val image = File(imagesDir, "$name.png")
            fos = FileOutputStream(image)
            drawing.width = W_T
            drawing.heigth = H_T
            drawing.projet_image_path = image.toString()
            dbViewmodel.updateDrawing(drawing)
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos?.flush()
        fos?.close()
    }


    fun clearView() {
        val designer: DesignerLayout = binding.layoutDesigner
        designer.clearViews()
    }

    private fun setUpDesignerLayout() {
        val designer: DesignerLayout = binding.layoutDesigner

        designer.apply {
            setH(H, H_T)
            setW(W, W_T)
            setFunctionOnSizeViewClickV {
                showSizeDialog()
                customHorVer = true
            }
            setFunctionOnSizeViewClickH {
                showSizeDialog()
                customHorVer = false
            }
        }

        val myLayoutParams = designer.layoutParams as ConstraintLayout.LayoutParams
        myLayoutParams.dimensionRatio = "$W:$H"

    }

    private fun showSizeDialog() {
//        SizeDialog().show(childFragmentManager, "SizeDialog")

        val builder = AlertDialog.Builder(requireContext())
        dialogBinding = LayoutChangeSizeDialogBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        val alertDialog: AlertDialog = builder.create()

        dialogBinding.apply {
            btn0.setOnClickListener { setview(0) }
            btn1.setOnClickListener { setview(1) }
            btn2.setOnClickListener { setview(2) }
            btn3.setOnClickListener { setview(3) }
            btn4.setOnClickListener { setview(4) }
            btn5.setOnClickListener { setview(5) }
            btn6.setOnClickListener { setview(6) }
            btn7.setOnClickListener { setview(7) }
            btn8.setOnClickListener { setview(8) }
            btn9.setOnClickListener { setview(9) }
//
            ivBackspace.setOnClickListener { backspace() }

            btnOk.setOnClickListener {
                okBtnClick()
                alertDialog.dismiss()
            }
            btnCancel.setOnClickListener { alertDialog.dismiss() }
        }


        alertDialog.show()

    }

    private fun okBtnClick() {
        dialogBinding.apply {
            if (customHorVer != null) {
                if (customHorVer as Boolean) {
                    val height = Resources.getSystem().displayMetrics.heightPixels
                    if (etSize.text.toString().toInt() > height) {
                        H = height.plus(700)
                        H_T = etSize.text.toString().toInt()
                    } else {
                        H = etSize.text.toString().toInt()
                        H_T = etSize.text.toString().toInt()
                    }
                    setUpDesignerLayout()
                } else {
                    val width = Resources.getSystem().displayMetrics.heightPixels
                    if (etSize.text.toString().toInt() > width) {
                        W = width.minus(50)
                        W_T = etSize.text.toString().toInt()
                    } else {
                        W = etSize.text.toString().toInt()
                        W_T = etSize.text.toString().toInt()
                    }
                    setUpDesignerLayout()
                }

            }
        }
    }

    private fun setview(number: Int) {
        val text = dialogBinding.etSize.text.toString()
        dialogBinding.etSize.setText(text.plus(number.toString()))
    }

    private fun backspace() {
        dialogBinding.apply {
            val text = etSize.text
            if (text != null) {
                if (text.length > 0) {
                    etSize.setText(text.substring(0, text.length.minus(1)))
                }
            }
        }
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

    open fun SliderScreen(): SliderScreen {
        return sliderObj
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}