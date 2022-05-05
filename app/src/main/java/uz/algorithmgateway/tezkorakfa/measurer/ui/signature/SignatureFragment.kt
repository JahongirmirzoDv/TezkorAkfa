package uz.algorithmgateway.tezkorakfa.measurer.ui.signature

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.FragmentSavePdfBinding
import uz.algorithmgateway.tezkorakfa.databinding.FragmentSignatureBinding

class SignatureFragment : Fragment(R.layout.fragment_signature) {
    private var _binding: FragmentSignatureBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignatureBinding.inflate(inflater, container, false)

        loadingBtn()
        return  binding.root
    }
    private fun loadingBtn() {
        binding.apply {
            clear.setOnClickListener {
                signature.clear()
            }
            next.setOnClickListener {
                loadSignature(signature.signatureBitmap)
            }

            toolbar.apply {
                titleTolbar.setText("Imzo")
                searchToolbar.visibility = View.GONE
                otherToolbar.visibility = View.GONE
            }
        }
    }

    private fun loadSignature(bitmap: Bitmap) {

        findNavController().navigate(R.id.customerTakePhotoFragment)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}