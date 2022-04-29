package uz.algorithmgateway.tezkorakfa.measurer.ui.select_count

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.ScreenSelectCountBinding

class ItemCountScreen : Fragment() {

    private var _binding: ScreenSelectCountBinding? = null
    lateinit var id: String
    lateinit var drawing: String
    private val binding get() = _binding!!
    var H: Int = 1300
    var W: Int = 2000

    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            id = it?.getString("id").toString()
            drawing = it?.getString("drawing").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ScreenSelectCountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.projectId.text = "Loyiha $id"
        loadTabData()
        chooseCount()
        navigateButton()

//        val recStr: String = arguments.get("This is just for testing purpose")
//        bundle.clear()
    }

    private fun navigateButton() {
        binding.btnNext.setOnClickListener {
            val bundle = Bundle()
            val height =
                if (binding.editTextWidth.text.toString() == "") H else binding.editTextHeight.text.toString()
                    .toInt()
            val width =
                if (binding.editTextWidth.text.toString() == "") W else binding.editTextWidth.text.toString()
                    .toInt()
            bundle.putInt("width", width)
            bundle.putInt("height", height)
            bundle.putString("id", id)
            bundle.putString("drawing", drawing)
            navController.navigate(R.id.sliderScreen, bundle)
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
}