package uz.algorithmgateway.tezkorakfa.measurer.ui.accept_order

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.drjacky.imagepicker.ImagePicker
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.Result
import uz.algorithmgateway.tezkorakfa.databinding.ScreenAcceptOrderBinding
import uz.algorithmgateway.tezkorakfa.measurer.ui.accept_order.model.Locations
import uz.algorithmgateway.tezkorakfa.measurer.ui.select_type.models.Drawing
import uz.algorithmgateway.tezkorakfa.measurer.utils.FileUriUtils
import uz.algorithmgateway.tezkorakfa.measurer.utils.compress
import uz.algorithmgateway.tezkorakfa.measurer.viewmodel.DbViewmodel
import uz.algorithmgateway.tezkorakfa.measurer.viewmodel.NetworkViewmodel
import uz.algorithmgateway.tezkorakfa.ui.utils.SharedPref
import java.io.File
import java.io.IOException
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class AcceptOrderScreen : Fragment(), CoroutineScope {

    @Inject
    lateinit var networkViewmodel: NetworkViewmodel

    private var _binding: ScreenAcceptOrderBinding? = null
    private val binding get() = _binding!!
    lateinit var item: Result
    private var mCameraUri: Uri? = null
    private var filePath: String? = null
    private val sharedPref by lazy { SharedPref(requireContext()) }

    @Inject
    lateinit var dbViewmodel: DbViewmodel

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
        MyApplication.appComponent.accept(this)
        arguments?.let {
            val string = it.getString("item")
            item = Gson().fromJson(string, Result::class.java)
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
        } else {
            Glide.with(this)
                .load(item.client_home_image) // Uri of the picture
                .into(binding.imageProduct)
        }

        loadView()
        clickBack()
        goToLocation()
        navigateBackOrNext()
    }

    @SuppressLint("SetTextI18n")
    private fun loadView() {
        binding.apply {
            projetNumber.text = "Loyiha ${item.id}"
            editTextName.setText(item.client.first_name)
            editTextSurname.setText(item.client.last_name)
            editTextAddress.setText(item.client.address)
            tvComment.setText(item.comment)
            val location = sharedPref.location
            if (location != "") {
                val fromJson = Gson().fromJson(location, Locations::class.java)
                val address = getAddress(fromJson.latitude!!, fromJson.longitude!!)
                editTextAddress.setText(address)
            }
        }
        binding.addImage.setOnClickListener {
            pickCameraImage()
        }
    }

    fun getAddress(lat: Double, lng: Double): String {
        var str = ""
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses: List<Address> = geocoder.getFromLocation(lat, lng, 1)
            val obj: Address = addresses[0]
            var add: String = obj.getAddressLine(0)
            add = """
            $add
            """.trimIndent()
            str = add
            Log.e("TAG", "getAddress: $add")
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return str
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
            launch(Dispatchers.Main) {
                val list = ArrayList<MultipartBody.Part>()
                async {
                    val builder: MultipartBody.Builder = MultipartBody.Builder()
                    if (filePath != null) {
                        for (i in 0..2) {
                            val files = File(filePath!!).compress(requireContext())
                            builder.setType(MultipartBody.FORM)
                            builder.addFormDataPart("id", item.id.toString())
                            builder.addFormDataPart("first_name",
                                binding.editTextName.text.toString())
                            builder.addFormDataPart("last_name",
                                binding.editTextSurname.text.toString())
                            builder.addFormDataPart("address",
                                binding.editTextAddress.text.toString())
                            builder.addFormDataPart("comment",
                                binding.textViewComment.text.toString())
                            builder.addFormDataPart(
                                "client_home_image",
                                files.name,
                                files.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                            )
                            val body = builder.build()
                            list.add(MultipartBody.Part.create(body))
                        }
                        networkViewmodel.updateUser(item.id.toString(), list)
                    } else {
                        builder.setType(MultipartBody.FORM)
                        builder.addFormDataPart("id", item.id.toString())
                        builder.addFormDataPart("first_name", binding.editTextName.text.toString())
                        builder.addFormDataPart("last_name",
                            binding.editTextSurname.text.toString())
                        builder.addFormDataPart("address", binding.editTextAddress.text.toString())
                        builder.addFormDataPart("comment", binding.textViewComment.text.toString())
                        builder.addFormDataPart(
                            "client_home_image",
                            "",
                        )
                    }


                }

                dbViewmodel.addDrawing(Drawing(id = item.id.toString()))
                findNavController().navigate(R.id.orderSelectType)
                sharedPref.location = ""
            }
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun goToLocation() {
        binding.imageLocation.setOnClickListener {
            findNavController().navigate(R.id.locationScreen)
        }
    }

    private fun clickBack() {
        binding.imageBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        loadView()
    }

    override fun onSaveInstanceState(oldInstanceState: Bundle) {
        super.onSaveInstanceState(oldInstanceState)
        oldInstanceState.clear()
    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}