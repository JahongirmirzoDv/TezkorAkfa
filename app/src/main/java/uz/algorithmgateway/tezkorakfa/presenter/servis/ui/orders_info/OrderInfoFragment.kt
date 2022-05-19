package uz.algorithmgateway.tezkorakfa.presenter.servis.ui.orders_info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.FragmentOrderInfoBinding
import uz.algorithmgateway.tezkorakfa.databinding.FragmentServisHomeBinding

class OrderInfoFragment : Fragment() {

    private var _binding: FragmentOrderInfoBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOrderInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

}