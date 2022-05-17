package uz.algorithmgateway.tezkorakfa.measurer.ui.customer_photo

import android.app.Activity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.drjacky.imagepicker.ImagePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.databinding.FragmentCustomerTakePhotoBinding
import uz.algorithmgateway.tezkorakfa.measurer.utils.FileUriUtils
import uz.algorithmgateway.tezkorakfa.measurer.utils.compress
import uz.algorithmgateway.tezkorakfa.measurer.viewmodel.DbViewmodel
import uz.algorithmgateway.tezkorakfa.measurer.viewmodel.NetworkViewmodel
import uz.algorithmgateway.tezkorakfa.ui.utils.UIState
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CustomerTakePhotoFragment : Fragment(), CoroutineScope {

    @Inject
    lateinit var viewmodel: DbViewmodel

    @Inject
    lateinit var apiVm: NetworkViewmodel

    private var _binding: FragmentCustomerTakePhotoBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.photo(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCustomerTakePhotoBinding.inflate(inflater, container, false)

        installTakePhoto()

        return binding.root
    }

    private fun installTakePhoto() {
        binding.apply {
            toolbar.apply {
                searchToolbar.visibility = View.GONE
                otherToolbar.visibility = View.GONE
                backArrowFragment.setOnClickListener {
                    findNavController().popBackStack()
                }
            }
            takePhotoBtn.setOnClickListener {
                takePhoto()
            }
            next.setOnClickListener {
                findNavController().navigate(R.id.unconfirmedFragment)
            }

        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //Clear the Activity's bundle of the subsidiary fragments' bundles.
        outState.clear()
    }

    fun takePhoto() {
        cameraLauncher.launch(
            ImagePicker.with(requireActivity())
                .cameraOnly()
                .createIntent()
        )
    }


    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                val pdf = viewmodel.getPdf().last()
                pdf.image = uri.toString()
                val filePath = FileUriUtils.getRealPath(requireActivity(), uri)
                binding.imageCustomer.setImageURI(uri)
                val files = filePath?.let { it1 -> File(it1).compress(requireContext()) }
                launch(Dispatchers.IO) {
                    val builder: MultipartBody.Builder = MultipartBody.Builder()

                    val f = File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                            .toString(),
                        pdf.pdf)
                    val signature = File(requireActivity().cacheDir, "Signature.png")

                    builder.setType(MultipartBody.FORM)
                    builder.addFormDataPart(
                        "scaler_file",
                        f.name,
                        f.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )
                    builder.addFormDataPart(
                        "signature",
                        signature.name,
                        signature.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    )
                    if (files != null) {
                        builder.addFormDataPart(
                            "image",
                            files.name,
                            files.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                        )
                    }
                    val body = builder.build()
                    apiVm.sendData(pdf.id, body).collect {
                        when (it) {
                            is UIState.Loading -> {

                            }
                            is UIState.Error -> {

                            }
                            is UIState.Success -> {
                                Log.d("TAG", ": $it.data?.result ?: succes")
                            }
                        }
                    }
                }
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.next.isEnabled = true
                }, 800)
            }
        }

    override val coroutineContext: CoroutineContext
        get() = Job()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}