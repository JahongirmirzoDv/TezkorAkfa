package uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.drawings.save_pdf

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.util.LruCache
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.data.local.entity.Pdf
import uz.algorithmgateway.tezkorakfa.databinding.FragmentSavePdfBinding
import uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.drawings.adapters.PdfAdapter
import uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.select_type.models.Drawing
import uz.algorithmgateway.tezkorakfa.presenter.measurer.viewmodel.DbViewmodel
import uz.algorithmgateway.tezkorakfa.presenter.measurer.viewmodel.NetworkViewmodel
import uz.algorithmgateway.tezkorakfa.presenter.ui.utils.UIState
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


open class SavePdfFragment : Fragment(), CoroutineScope {
    @Inject
    lateinit var viewmodel: DbViewmodel

    @Inject
    lateinit var apiVm: NetworkViewmodel

    private var _binding: FragmentSavePdfBinding? = null
    private val binding get() = _binding!!
    lateinit var list: MutableStateFlow<List<Drawing>>
    lateinit var drawing: Drawing
    var position = 0
    lateinit var adapter: PdfAdapter

    var wil = 0
    var height = 0
    val REQUEST_PERMISSIONS = 1

    var booleanPermission = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.savePdf(this)

    }


    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSavePdfBinding.inflate(inflater, container, false)
        list = MutableStateFlow(emptyList())
        list.value = viewmodel.getAllDrawing()
        drawing = viewmodel.getAllDrawing().last()

        wil = binding.savePdf.width
        height = binding.savePdf.height



        launch(Dispatchers.Main) {
            list.collect {
                adapter = PdfAdapter(requireContext(), it)
                binding.savePdf.adapter = adapter
                position = it.size
                binding.savePdf.smoothScrollToPosition(position)
            }
        }
        loadPdf()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun loadPdf() {
        binding.save.setOnClickListener {
            if (booleanPermission) {
                try {
                    val screenshotFromRecyclerView = getScreenshotFromRecyclerView(binding.savePdf)
                    screenshotFromRecyclerView?.let { it1 -> saveImageToPDF(height, it1, "chizma") }
                } catch (e: Exception) {
                    Log.e("eror", "loadPdf: ${e.message}")
                }
                findNavController().navigate(R.id.unconfirmedFragment)

                val pdf = viewmodel.getPdf().last()
                val list = viewmodel.getAllDrawing()
                var eshik = 0
                var oyna = 0
                list.forEach {
                    if (it.type == "Eshik") {
                        eshik += it.count ?: 0
                    } else if (it.type == "Oyna") {
                        oyna += it.count ?: 0
                    }
                }

                val builder: MultipartBody.Builder = MultipartBody.Builder()

                val f = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                        .toString(),
                    pdf.pdf
                )

                builder.setType(MultipartBody.FORM)
                builder.addFormDataPart(
                    "scaler_file",
                    f.name,
                    f.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                )
                builder.addFormDataPart("number_of_rooms", "$oyna")
                builder.addFormDataPart("number_of_doors", "$eshik")

                val body = builder.build()
                launch(Dispatchers.IO) {
                    apiVm.sendData(pdf.id, body).collect {
                        when (it) {
                            is UIState.Loading -> {

                            }
                            is UIState.Error -> {
                                Log.d("TAG", "send: ${it.message}")
                            }
                            is UIState.Success -> {
                                Log.d("TAG", ": $it.data?.result ?: succes")
                            }
                        }
                    }
                }
            } else {
                requestPermission()
            }

        }
        requestPermission()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                try {
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent.addCategory("android.intent.category.DEFAULT")
                    intent.data = Uri.parse(
                        String.format(
                            "package:%s",
                            requireContext().applicationContext.packageName
                        )
                    )
                    startActivityForResult(intent, 2296)
                    booleanPermission = true
                } catch (e: Exception) {
                    val intent = Intent()
                    intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION;
                    startActivityForResult(intent, 2296)
                    booleanPermission = true
                }
            }else{
                booleanPermission = true
            }
        } else {
            if ((ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                ) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED)
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ), REQUEST_PERMISSIONS
                )
            } else {
                booleanPermission = true
            }
        }
    }

    private fun randomKey(): String = List(6) {
        (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
    }.joinToString("")


    fun saveImageToPDF(heig: Int, bitmap: Bitmap, filename: String) {
        val randomKey = randomKey()
        val mFile =
            File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                    .toString(),
                "${filename}_${randomKey}.pdf"
            )
        viewmodel.addPdf(
            Pdf(
                id = drawing.id,
                pdf = "${filename}_${randomKey}.pdf",
                signature = null,
                image = null
            )
        )
        if (!mFile.exists()) {
            val height = heig + bitmap.height
            val document = PdfDocument()
            val pageInfo = PageInfo.Builder(bitmap.width, height, 1).create()
            val page = document.startPage(pageInfo)
            val canvas = page.canvas
            binding.savePdf.draw(canvas)
            canvas.drawBitmap(
                bitmap,
                null,
                Rect(0, heig, bitmap.width, bitmap.height),
                null
            )
            document.finishPage(page)
            try {
                mFile.createNewFile()
                val out: OutputStream = FileOutputStream(mFile)
                document.writeTo(out)
                document.close()
                out.close()
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("log_e", "saveImageToPDF: ${e.message}")
            }
        }
    }


    fun getScreenshotFromRecyclerView(view: RecyclerView): Bitmap? {
        val adapter = view.adapter
        var bigBitmap: Bitmap? = null
        if (adapter != null) {
            val size = adapter.itemCount
            var height = 0
            val paint = Paint()
            var iHeight = 0
            val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

            // Use 1/8th of the available memory for this memory cache.
            val cacheSize = maxMemory / 8
            val bitmaCache: LruCache<String, Bitmap> = LruCache(cacheSize)
            for (i in 0 until size) {
                val holder = adapter.createViewHolder(view, adapter.getItemViewType(i))
                adapter.onBindViewHolder(holder, i)
                holder.itemView.measure(
                    View.MeasureSpec.makeMeasureSpec(
                        view.width,
                        View.MeasureSpec.EXACTLY
                    ),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                )
                holder.itemView.layout(
                    0,
                    0,
                    holder.itemView.measuredWidth,
                    holder.itemView.measuredHeight
                )
                holder.itemView.isDrawingCacheEnabled = true
                holder.itemView.buildDrawingCache()
                val drawingCache = holder.itemView.drawingCache
                if (drawingCache != null) {
                    bitmaCache.put(i.toString(), drawingCache)
                }
                height += holder.itemView.measuredHeight
            }
            bigBitmap = Bitmap.createBitmap(view.measuredWidth, height, Bitmap.Config.ARGB_8888)
            val bigCanvas = Canvas(bigBitmap)
            bigCanvas.drawColor(Color.WHITE)
            for (i in 0 until size) {
                val bitmap: Bitmap = bitmaCache.get(i.toString())
                bigCanvas.drawBitmap(bitmap, 0f, iHeight.toFloat(), paint)
                iHeight += bitmap.height
                bitmap.recycle()
            }
        }
        return bigBitmap
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}