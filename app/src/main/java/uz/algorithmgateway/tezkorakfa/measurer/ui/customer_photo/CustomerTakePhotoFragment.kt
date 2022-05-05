package uz.algorithmgateway.tezkorakfa.measurer.ui.customer_photo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.vansuita.pickimage.enums.EPickType
import com.vansuita.pickimage.listeners.IPickClick
import java.io.*


class CustomerTakePhotoFragment : AppCompatActivity() {
    lateinit var binding: CustomerTakePhotoFragment

    lateinit var setup: PickSetup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = CustomerTakePhotoFragment
//        setContentView(binding.root)

        installTakePhoto()
        setup = PickSetup()
            .setTitle("Rasm")
            .setWidth(600)
            .setHeight(600)
            .setProgressText("saqlanyapti")
            .setCancelText("bekor qilish")
            .setFlip(true)
            .setMaxSize(1)
            .setPickTypes(EPickType.CAMERA)
            .setIconGravity(Gravity.LEFT)
            .setButtonOrientation(LinearLayout.HORIZONTAL)
            .setSystemDialog(false)

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
                try {
                    takePhoto()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            next.setOnClickListener {

            }
        }
    }

    @SuppressLint("RtlHardcoded")
    fun takePhoto() {
        try {
            PickImageDialog.build(setup)
                .setOnClick(object : IPickClick {
                    override fun onGalleryClick() {
                        Toast.makeText(requireContext(), "Gallery Click!", Toast.LENGTH_LONG).show()
                    }

                    override fun onCameraClick() {
                        Toast.makeText(requireContext(), "Camera Click!", Toast.LENGTH_LONG).show()
                    }
                }).show(requireActivity())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}