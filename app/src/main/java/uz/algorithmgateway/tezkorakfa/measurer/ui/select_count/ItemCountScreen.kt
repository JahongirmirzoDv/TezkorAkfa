package uz.algorithmgateway.tezkorakfa.measurer.ui.select_count

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.databinding.ScreenSelectCountBinding
import uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.models.Drawing
import uz.algorithmgateway.tezkorakfa.measurer.viewmodel.DbViewmodel
import javax.inject.Inject

class ItemCountScreen : Fragment() {


    @Inject
    lateinit var dbViewmodel: DbViewmodel

    private var _binding: ScreenSelectCountBinding? = null
    lateinit var drawing: Drawing
    private val binding get() = _binding!!
    var H: Int = 1300
    var W: Int = 2000

    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.counter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ScreenSelectCountBinding.inflate(inflater, container, false)
        drawing = dbViewmodel.getAllDrawing().last()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.projectId.text = "Loyiha ${drawing.id}"
        loadTabData()
        chooseCount()
        navigateButton()

//        val recStr: String = arguments.get("This is just for testing purpose")
//        bundle.clear()
    }

    private fun navigateButton() {
        binding.btnNext.setOnClickListener {
            var tab = ""
            when (binding.tabLayoutDoor.selectedTabPosition) {
                0 -> tab = "Standart"
                1 -> tab = "Slider"
            }
            drawing.count = binding.textViewCount.text.toString().toInt()
            drawing.type_type = tab

            dbViewmodel.updateDrawing(
                drawing)
            navController.navigate(R.id.sliderScreen)
        }
        binding.btnBack.setOnClickListener {
            navController.navigateUp()
        }
    }

    private fun chooseCount() {
        binding.cardPlus.setOnClickListener {
            val count = binding.textViewCount.text.toString()
            binding.textViewCount.text = (count.toInt() + 1).toString()
        }

        binding.cardMinus.setOnClickListener {
            val count = binding.textViewCount.text.toString()
            if (count.toInt() > 0) {
                binding.textViewCount.text = (count.toInt() - 1).toString()
            }
        }

    }

    private fun loadTabData() {
        with(binding) {
            tabLayoutDoor.addTab(tabLayoutDoor.newTab().setText("Standart"))
            tabLayoutDoor.addTab(tabLayoutDoor.newTab().setText("Slider"))
        }
    }

    override fun onSaveInstanceState(oldInstanceState: Bundle) {
        super.onSaveInstanceState(oldInstanceState)
        oldInstanceState.clear()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}