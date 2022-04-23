package uz.algorithmgateway.tezkorakfa.measurer.ui.drawings.save_pdf

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.databinding.FragmentSavePdfBinding
import uz.algorithmgateway.tezkorakfa.measurer.ui.drawings.adapters.PdfAdapter
import uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.models.Drawing
import uz.algorithmgateway.tezkorakfa.measurer.viewmodel.DbViewmodel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SavePdfFragment : Fragment(), CoroutineScope {
    @Inject
    lateinit var viewmodel: DbViewmodel
    lateinit var binding: FragmentSavePdfBinding
    lateinit var list: MutableStateFlow<List<Drawing>>

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
                savePdf()
            }
        }

        return binding.root
    }

    private fun savePdf() {
        PdfGenerator.getBuilder()
            .setContext(requireContext())
            .fromLayoutXMLSource()
            .fromLayoutXML(R.layout.fragment_save_pdf) /* "fromLayoutXML()" takes array of layout resources.
       * You can also invoke "fromLayoutXMLList()" method here which takes list of layout resources instead of array. */
            .setFileName("Test-PDF") /* It is file name */
            .setFolderNameOrPath("/data/user/0/com.example.bookapp/cache/") /* It is folder name. If you set the folder name like this pattern (FolderA/FolderB/FolderC), then
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