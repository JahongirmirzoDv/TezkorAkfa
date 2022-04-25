package uz.algorithmgateway.tezkorakfa.measurer.ui.drawings.save_pdf

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.print.PrintAttributes
import android.print.PrintManager
import android.print.pdf.PrintedPdfDocument
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.gkemon.XMLtoPDF.PdfGenerator
import com.gkemon.XMLtoPDF.PdfGeneratorListener
import com.gkemon.XMLtoPDF.model.FailureResponse
import com.gkemon.XMLtoPDF.model.SuccessResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.core.util.toast
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.databinding.FragmentSavePdfBinding
import uz.algorithmgateway.tezkorakfa.measurer.ui.drawings.adapters.PdfAdapter
import uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.models.Drawing
import uz.algorithmgateway.tezkorakfa.measurer.viewmodel.DbViewmodel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class SavePdfFragment : Fragment(), CoroutineScope {
    @Inject
    lateinit var viewmodel: DbViewmodel
    lateinit var binding: FragmentSavePdfBinding
    lateinit var list: MutableStateFlow<List<Drawing>>

    val REQUEST_PERMISSIONS = 1

    var booleanPermission = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.savePdf(this)

    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSavePdfBinding.inflate(inflater, container, false)
        list = MutableStateFlow(emptyList())
        list.value = viewmodel.getAllDrawing()


        var adapter = PdfAdapter(requireContext())
        binding.savePdf.adapter = adapter
        launch(Dispatchers.Main) {
            list.collect {
                adapter.list = it
                adapter.notifyDataSetChanged()
            }
//            savePdf()

        }
        loadPdf()

        return binding.root
    }

    private fun loadPdf() {

        binding.save.setOnClickListener {
            if (booleanPermission) {
                try {
                    createPdf()
                    val f = File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            .toString() + "/Operation.pdf"
                    )
                    toast(f.toString())

//                    val shareIntent = Intent(Intent.ACTION_SEND)
//                    shareIntent.putExtra(
//                        Intent.EXTRA_STREAM,
//                        context?.let { it1 -> uriFromFile(it1, f) })
//                    shareIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
//                    shareIntent.type = "application/pdf"
//                    startActivity(Intent.createChooser(shareIntent, getString(R.string.share)))
                } catch (e: Exception) {
//                    mainListener?.showError("Please try again") {}
                }
            } else {
                requestPermission()
            }
        }

        requestPermission()
    }

    private fun requestPermission() {
        if ((ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED) &&
            (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), REQUEST_PERMISSIONS
            )

        } else {
            booleanPermission = true
        }
    }

    private fun createPdf() {
        val printAttrs = PrintAttributes.Builder().setColorMode(PrintAttributes.COLOR_MODE_COLOR)
            .setMediaSize(PrintAttributes.MediaSize.NA_LETTER)
            .setResolution(PrintAttributes.Resolution("zooey", Context.PRINT_SERVICE, 300, 300))
            .setMinMargins(PrintAttributes.Margins.NO_MARGINS).build()
        val document: PdfDocument = PrintedPdfDocument(requireContext(), printAttrs)
        // crate a page description
        // crate a page description
        val pageInfo = PdfDocument.PageInfo.Builder(binding?.rootView!!.width, 1000, 1).create()
        // create a new page from the PageInfo
        // create a new page from the PageInfo
        val page = document.startPage(pageInfo)
        // repaint the user's text into the page
        // repaint the user's text into the page
        val content: ViewGroup = binding!!.rootView
        content.draw(page.canvas)
        // do final processing of the page
        // do final processing of the page
        document.finishPage(page)
        // Here you could add more pages in a longer doc app, but you'd have
        // to handle page-breaking yourself in e.g., write your own word processor...
        // Now write the PDF document to a file; it actually needs to be a file
        // since the Share mechanism can't accept a byte[]. though it can
        // accept a String/CharSequence. Meh.
        // Here you could add more pages in a longer doc app, but you'd have
        // to handle page-breaking yourself in e.g., write your own word processor...
        // Now write the PDF document to a file; it actually needs to be a file
        // since the Share mechanism can't accept a byte[]. though it can
        // accept a String/CharSequence. Meh.
        try {
            val f = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .toString() + "/Operation.pdf"
            )
            val fos = FileOutputStream(f)
            document.writeTo(fos)
            document.close()
            fos.close()
        } catch (e: IOException) {
            throw RuntimeException("Error generating file", e)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        savePdf()

    }


    private fun savePdf() {

        val outputDir: File = requireActivity().cacheDir // context being the Activity pointer
//        val outputFile: File = File.createTempFile("prefix", ".pdf", outputDir)

        Log.e("TAG", "URL SAVE $outputDir")
        PdfGenerator.getBuilder()
            .setContext(requireContext())
            .fromLayoutXMLSource()
            .fromLayoutXML(R.layout.fragment_save_pdf) /* "fromLayoutXML()" takes array of layout resources.
       * You can also invoke "fromLayoutXMLList()" method here which takes list of layout resources instead of array. */
            .setFileName("Test-PD") /* It is file name */
            .setFolderNameOrPath(outputDir.toString()) /* It is folder name. If you set the folder name like this pattern (FolderA/FolderB/FolderC), then
       * FolderA creates first.Then FolderB inside FolderB and also FolderC inside the FolderB and finally
       * the pdf file named "Test-PDF.pdf" will be store inside the FolderB. */
            .openPDAfterGeneration(true) /* It true then the generated pdf will be shown after generated. */
            .build(object : PdfGeneratorListener() {
                override fun onFailure(failureResponse: FailureResponse) {
                    super.onFailure(failureResponse)
                    Log.e("TAG", "onFailure: $failureResponse")
                }

                override fun onStartPDFGeneration() {
                    /*When PDF generation begins to start*/
                }

                override fun onFinishPDFGeneration() {
                    /*When PDF generation is finished*/
                }

                override fun showLog(log: String) {
                    super.showLog(log)
                    Log.e("TAG", "onFailure: $log")
                }

                override fun onSuccess(response: SuccessResponse) {
                    super.onSuccess(response)
                    /* If PDF is generated successfully then you will find SuccessResponse
         * which holds the PdfDocument,File and path (where generated pdf is stored)*/
                }
            })

    }


    override val coroutineContext: CoroutineContext
        get() = Job()
}