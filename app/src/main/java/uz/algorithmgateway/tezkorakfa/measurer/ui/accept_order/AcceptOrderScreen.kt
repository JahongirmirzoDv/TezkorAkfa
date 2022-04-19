package uz.algorithmgateway.tezkorakfa.measurer.ui.accept_order

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.drjacky.imagepicker.ImagePicker
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.ScreenAcceptOrderBinding
import uz.algorithmgateway.tezkorakfa.measurer.utils.FileUriUtils
import uz.algorithmgateway.tezkorakfa.ui.utils.SharedPref
import java.io.File

class AcceptOrderScreen : Fragment() {

    private var _binding: ScreenAcceptOrderBinding? = null
    private val binding get() = _binding!!
    var item: String = ""
    private var mCameraUri: Uri? = null
    private var filePath: String? = null
    private val sharedPref by lazy { SharedPref(requireContext()) }

    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                mCameraUri = uri
                filePath = FileUriUtils.getRealPath(requireActivity(), uri)
                if (filePath != null) {
                    binding.imageProduct.visibility = View.GONE
                    binding.imageProduct.visibility = View.VISIBLE
                    binding.imageProduct.setImageURI(Uri.fromFile(File(filePath!!)))
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            item = it.getSerializable("item").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ScreenAcceptOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (filePath != null) {
            binding.imageProduct.setImageURI(Uri.fromFile(File(filePath!!)))
        }
        loadView()
        clickBack()
        goToLocation()
        navigateBackOrNext()
    }

    private fun loadView() {
//        binding.apply {
//            editTextName.setText(item?.client?.first_name)
//            editTextSurname.setText(item?.client?.last_name)
//            editTextAddress.setText(item?.client?.address)
//            tvComment.setText(item?.comment)
//        }
        binding.imageProduct.setOnClickListener {
            pickCameraImage()
        }
    }

    private fun pickCameraImage() {
        cameraLauncher.launch(
            ImagePicker.with(requireActivity())
                .cameraOnly()
                .createIntent()
        )
    }

    private fun navigateBackOrNext() {


        binding.btnNext.setOnClickListener {
            navController.navigate(R.id.orderSelectType)
        }

        binding.btnBack.setOnClickListener {
            navController.navigateUp()
        }
    }

    private fun goToLocation() {
        binding.editTextAddress.setOnClickListener {
            navController.navigate(R.id.locationScreen)
        }

        binding.imageLocation.setOnClickListener {
        }
    }

    private fun clickBack() {
        binding.imageBack.setOnClickListener {
            navController.navigateUp()
        }
    }
}