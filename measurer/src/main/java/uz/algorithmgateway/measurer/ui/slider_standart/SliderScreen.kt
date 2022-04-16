package uz.algorithmgateway.measurer.ui.slider_standart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.algorithmgateway.measurer.databinding.ScreenSliderBinding

class SliderScreen : Fragment() {

    private var _binding: ScreenSliderBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ScreenSliderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationButtons()
    }

    private fun navigationButtons() {

        binding.floatingNext.setOnClickListener {
            navController.navigate(SliderScreenDirections.toConfirmOrdersScreen())
        }

        binding.floatingBack.setOnClickListener {
            navController.navigateUp()
        }
    }
}