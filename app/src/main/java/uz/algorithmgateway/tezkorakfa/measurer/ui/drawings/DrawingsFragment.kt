package uz.algorithmgateway.tezkorakfa.measurer.ui.drawings

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.databinding.FragmentDrawingsBinding
import uz.algorithmgateway.tezkorakfa.databinding.ScreenOrdersBinding
import uz.algorithmgateway.tezkorakfa.measurer.ui.drawings.adapters.DrawingAdapter
import uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.models.Drawing
import uz.algorithmgateway.tezkorakfa.measurer.viewmodel.DbViewmodel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class DrawingsFragment : Fragment(), CoroutineScope {
    @Inject
    lateinit var dbViewmodel: DbViewmodel

    private var _binding: FragmentDrawingsBinding?  = null
    private val binding get() = _binding!!
    lateinit var list: MutableStateFlow<List<Drawing>>
    lateinit var drawingAdapter: DrawingAdapter
    var drawing: Drawing? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.drawing(this)
        list = MutableStateFlow(emptyList())
        drawingAdapter = DrawingAdapter(requireContext(),object :DrawingAdapter.onPress{
            override fun click(projetImagePath: String?) {
//                projetImagePath?.let { showDialog(it) }
            }
        })
        drawing = dbViewmodel.getAllDrawing().last()

    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDrawingsBinding.inflate(inflater, container, false)
        setupUI()
        loadData()

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadData() {
        launch(Dispatchers.Main) {
            list.value = dbViewmodel.getAllDrawing()

            binding.projectsRv.adapter = drawingAdapter

            list.collect {
                drawingAdapter.list = it
                drawingAdapter.notifyDataSetChanged()
            }
        }
    }



    private fun setupUI() {
        binding.projectId.text = "Loyiha ${drawing?.id}"

        binding.add.setOnClickListener {
            val bundle = Bundle()
            val plus = drawing?.id?.toInt()?.plus(1)
            bundle.putString("id","$plus")
            findNavController().navigate(R.id.orderSelectType,bundle)
        }

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.savePdfFragment)
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Job()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}