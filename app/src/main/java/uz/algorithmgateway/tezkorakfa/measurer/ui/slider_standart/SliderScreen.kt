package uz.algorithmgateway.tezkorakfa.measurer.ui.slider_standart

//import com.karumi.dexter.Dexter
//import com.karumi.dexter.MultiplePermissionsReport
//import com.karumi.dexter.PermissionToken
//import com.karumi.dexter.listener.PermissionRequest
//import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.ClipDescription
import android.content.ContentResolver
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.algorithmgateway.core.util.toast
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.databinding.LayoutChangeSizeDialogBinding
import uz.algorithmgateway.tezkorakfa.databinding.ScreenSliderBinding
import uz.algorithmgateway.tezkorakfa.measurer.viewmodel.DbViewmodel
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.DragAndDropListener
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.Area
import uz.algorithmgateway.tezkorakfa.windowdoordisegner.ui.DesignerLayout
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import javax.inject.Inject


class SliderScreen : Fragment() {

    @Inject
    lateinit var dbViewmodel: DbViewmodel
    lateinit var id: String

    //    lateinit var drawing: Drawing
    private var _binding: ScreenSliderBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    lateinit var dragAndDropListener: Area
    private val TAG = "SliderScreen"
    var sliderObj = SliderScreen()
    private lateinit var dialogBinding: LayoutChangeSizeDialogBinding
    private var customHorVer: Boolean? = null

    var lastView: View? = null
    var H: Int = 1300
    var W: Int = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.sliderScreen(this)
//        DialogSize(this)
        arguments.let {
            W = it?.getInt("width")!!
            H = it.getInt("height")
            id = it.getString("id").toString()
            val t = it.getString("drawing").toString()
//            if (drawing != null) {
//                drawing = Gson().fromJson(t, Drawing::class.java)
//            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ScreenSliderBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.projectId.text = "Loyiha $id"
        navigationButtons()
        dragAndDropListener = Area(requireContext())

        setUpDesignerLayout()
        setDragAndDropToViews()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun navigationButtons() {

        binding.floatingNext.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", id)
            navController.navigate(R.id.drawingsFragment, bundle)
            verifyStoragePermission(requireActivity())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                savePdf()
            }
            getBitmapFromView(binding.view, requireActivity(), callback = {
                saveBitmap(it, "new api")
            })
            toast("Chizma galareyaga saqlandi")
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
//            drawing.id = id
//            drawing.width = W
//            drawing.heigth = H
//            drawing.projet_image_path = imageUri.toString()
//            dbViewmodel.updateDrawing(drawing)
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
//            drawing.id = id
//            drawing.width = W
//            drawing.heigth = H
//            drawing.projet_image_path = image.toString()
//            dbViewmodel.updateDrawing(drawing)
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos?.flush()
        fos?.close()
    }

//    @RequiresApi(Build.VERSION_CODES.Q)
//    fun savePdf() {
//        Dexter.withContext(requireContext())
//            .withPermissions(
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.ACCESS_NETWORK_STATE
//            ).withListener(object : MultiplePermissionsListener {
//                override fun onPermissionsChecked(report: MultiplePermissionsReport) { /* ... */
//                    PdfGenerator.getBuilder()
//                        .setContext(requireContext())
//                        .fromViewSource()
//                        .fromView(binding.view)
//                        .setFileName("Test-PDF")
//                        .setFolderNameOrPath("k")
//                        .build(object : PdfGeneratorListener() {
//                            override fun onFailure(failureResponse: FailureResponse) {
//                                super.onFailure(failureResponse)
//                                Log.e(TAG, "onFailure: $failureResponse")
//                            }
//
//                            override fun showLog(log: String) {
//                                super.showLog(log)
//                                Log.e(TAG, "log: $log")
//                            }
//
//                            override fun onStartPDFGeneration() {
//                                /*When PDF generation begins to start*/
//                            }
//
//                            override fun onFinishPDFGeneration() {
//                                Log.e(TAG, "finish: finish")
//                            }
//
//                            override fun onSuccess(response: SuccessResponse) {
//                                super.onSuccess(response)
//                                Log.e(TAG, "succes: $response")
//                            }
//                        })
//                }
//
//                override fun onPermissionRationaleShouldBeShown(
//                    permissions: List<PermissionRequest?>?,
//                    token: PermissionToken?,
//                ) { /* ... */
//                }
//            }).check()
//
//    }

    fun clearView() {
        val designer: DesignerLayout = binding.layoutDesigner
        designer.clearViews()
    }

    private fun setUpDesignerLayout() {
        val designer: DesignerLayout = binding.layoutDesigner

        designer.apply {
            setH(H)
            setW(W)
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
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)

        var width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels

        dialogBinding.apply {
            if (customHorVer != null) {
                if (customHorVer as Boolean) {
                    H = etSize.text.toString().toInt()
                    if (height >= H) {
                        setUpDesignerLayout()
                    } else {
                        toast("bu o'lcham sizning qurilmangiz uchun to'g'ri kelmaydi !")
                    }
                } else {
                    W = etSize.text.toString().toInt()
                    if (width >= W) {
                        setUpDesignerLayout()
                    } else {
                        toast("bu o'lcham sizning qurilmangiz uchun to'g'ri kelmaydi !")
                    }
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
                    etSize.setText(text?.substring(0, text.length.minus(1)))
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


}