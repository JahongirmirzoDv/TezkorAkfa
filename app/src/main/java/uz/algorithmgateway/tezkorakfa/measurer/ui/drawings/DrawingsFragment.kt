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
import uz.algorithmgateway.tezkorakfa.databinding.ImageDialogBinding
import uz.algorithmgateway.tezkorakfa.measurer.ui.drawings.adapters.DrawingAdapter
import uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.models.Drawing
import uz.algorithmgateway.tezkorakfa.measurer.viewmodel.DbViewmodel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class DrawingsFragment : Fragment(), CoroutineScope {
    @Inject
    lateinit var dbViewmodel: DbViewmodel

    lateinit var binding: FragmentDrawingsBinding
    lateinit var id: String
    lateinit var list: MutableStateFlow<List<Drawing>>
    lateinit var drawingAdapter: DrawingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.drawing(this)
        list = MutableStateFlow(emptyList())
        drawingAdapter = DrawingAdapter(requireContext(),object :DrawingAdapter.onPress{
            override fun click(projetImagePath: String?) {
//                projetImagePath?.let { showDialog(it) }
            }
        })
        arguments?.let {
            id = it.getString("id").toString()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDrawingsBinding.inflate(inflater, container, false)
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

    private fun showDialog(image_path: String) {
        val root = ImageDialogBinding.inflate(layoutInflater, null, false)
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(root.root)
        val yesBtn = root.imageView
        Glide.with(requireActivity())
            .load(image_path) // Uri of the picture
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    println(e.toString())
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    return false
                }

            })
            .into(yesBtn)
        dialog.show()
    }

    private fun setupUI() {
        binding.projectId.text = "Loyiha $id"

        binding.add.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id","${id}_${id+1}")
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

}