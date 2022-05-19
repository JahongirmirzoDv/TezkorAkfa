package uz.algorithmgateway.tezkorakfa.presenter.servis.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.algorithmgateway.tezkorakfa.databinding.FragmentServisHomeBinding


class ServisHomeFragment : Fragment() {
    private var _binding: FragmentServisHomeBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentServisHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        installToolbar()
    }

    private fun installToolbar() {
        binding.toolbar.apply {
            otherToolbar.visibility = View.GONE
            backArrowFragment.visibility = View.GONE
            titleTolbar.text = "Buyurtmalar"

        }

    }


}