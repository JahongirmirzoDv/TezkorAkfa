package uz.algorithmgateway.tezkorakfa.montage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.FragmentAcceptOrderServisBinding
import uz.algorithmgateway.tezkorakfa.databinding.FragmentConfirmedOrdersBinding

class ConfirmedOrdersImageInfoFragment : Fragment() {
    private var _binding: FragmentConfirmedOrdersBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmedOrdersBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}