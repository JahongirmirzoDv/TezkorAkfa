package uz.algorithmgateway.tezkorakfa.measurer.ui.drawings.save_pdf

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
    var height = 0
    var wil = 0
    var adapter = PdfAdapter(requireContext())

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


        binding.savePdf.adapter = adapter
        launch(Dispatchers.Main) {
            list.collect {
                adapter.list = it
                adapter.notifyDataSetChanged()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}