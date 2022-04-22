package uz.algorithmgateway.tezkorakfa.montage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.FragmentConfirmedOrdersBinding


class ConfirmedOrdersFragment : Fragment(R.layout.fragment_confirmed_orders) {

    private val binding: FragmentConfirmedOrdersBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarInstal()
    }

    private fun toolbarInstal() {
        binding.toolbar.apply {
            backArrowFragment.visibility = View.GONE
        }

    }
}