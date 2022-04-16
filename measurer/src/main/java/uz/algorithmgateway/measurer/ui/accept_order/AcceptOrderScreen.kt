package uz.algorithmgateway.measurer.ui.accept_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.algorithmgateway.core.Screen
import uz.algorithmgateway.measurer.R
import uz.algorithmgateway.measurer.databinding.ScreenAcceptOrderBinding
import uz.algorithmgateway.measurer.databinding.ScreenOrdersBinding

class AcceptOrderScreen : Fragment() {

    private var _binding: ScreenAcceptOrderBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

//
//    override fun setup() {
//        super.setup()
//
//        clickBack()
//        goToLocation()
//        navigateBackOrNext()
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ScreenAcceptOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickBack()
        goToLocation()
        navigateBackOrNext()
    }


    private fun navigateBackOrNext() {


        binding.btnNext.setOnClickListener {
            navController.navigate(AcceptOrderScreenDirections.toOrderSelectType())
        }

        binding.btnBack.setOnClickListener {
            navController.navigateUp()
        }
    }

    private fun goToLocation() {
        binding.editTextAddress.setOnClickListener {
            navController.navigate(AcceptOrderScreenDirections.toOrderLocationScreen())
        }

        binding.imageLocation.setOnClickListener {
            navController.navigate(AcceptOrderScreenDirections.toOrderLocationScreen())
        }
    }

    private fun clickBack() {
        binding.imageBack.setOnClickListener {
            navController.navigateUp()
        }
    }
}