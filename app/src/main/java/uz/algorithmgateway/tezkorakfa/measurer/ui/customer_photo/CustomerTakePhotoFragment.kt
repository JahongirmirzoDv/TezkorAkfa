package uz.algorithmgateway.tezkorakfa.measurer.ui.customer_photo

import android.app.Activity
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.drjacky.imagepicker.ImagePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CustomerTakePhotoFragment : Fragment(R.layout.fragment_customer_take_photo), CoroutineScope {

    @Inject
    lateinit var viewmodel: DbViewmodel

    @Inject
    lateinit var apiVm: NetworkViewmodel


    private val binding: FragmentCustomerTakePhotoBinding by viewBinding(
        FragmentCustomerTakePhotoBinding::bind
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.photo(this)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        installTakePhoto()

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
                sendData()

                findNavController().navigate(R.id.confirmOrdersScreen)
            }

        }

    }

    private fun sendData() {


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
                viewmodel.updatePdf(pdf)
                binding.imageCustomer.setImageURI(uri)
                val files = filePath?.let { it1 -> File(it1).compress(requireContext()) }
                launch(Dispatchers.IO) {
                    val builder: MultipartBody.Builder = MultipartBody.Builder()

                    val f = File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            .toString() + "/Operation.pdf"
                    )
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
                    apiVm.sendData(pdf.id, body)
                }
            }
        }
    override val coroutineContext: CoroutineContext
        get() = Job()


}