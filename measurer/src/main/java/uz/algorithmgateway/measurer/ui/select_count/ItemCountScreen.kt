package uz.algorithmgateway.measurer.ui.select_count

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.algorithmgateway.measurer.databinding.ScreenSelectCountBinding

class ItemCountScreen : Fragment() {

    private var _binding: ScreenSelectCountBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ScreenSelectCountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadTabData()
        chooseCount()
        navigateButton()
    }

    private fun navigateButton() {
        binding.btnNext.setOnClickListener {
            navController.navigate(ItemCountScreenDirections.toSliderScreen())
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

}