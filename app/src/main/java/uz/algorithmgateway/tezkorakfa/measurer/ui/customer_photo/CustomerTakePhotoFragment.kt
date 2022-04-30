package uz.algorithmgateway.tezkorakfa.measurer.ui.customer_photo

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.FrameLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.drjacky.imagepicker.ImagePicker
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.FragmentCustomerTakePhotoBinding
import uz.algorithmgateway.tezkorakfa.measurer.utils.FileUriUtils
import java.io.*

class CustomerTakePhotoFragment : Fragment(R.layout.fragment_customer_take_photo) {



    private val binding: FragmentCustomerTakePhotoBinding by viewBinding(
        FragmentCustomerTakePhotoBinding::bind
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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
                .maxResultSize(1080,1920)
                .createIntent()
        )
    }


    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                binding.imageCustomer.setImageURI(uri)
            }
        }




}