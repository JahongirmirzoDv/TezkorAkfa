package uz.algorithmgateway.tezkorakfa.presenter.montage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.FragmentAcceptOrderServisBinding
import uz.algorithmgateway.tezkorakfa.databinding.FragmentOrderBinding


class OrdersFragment : Fragment() {
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)

        return binding.root
    }


}