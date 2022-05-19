package uz.algorithmgateway.tezkorakfa.presenter.servis.ui.accept_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.algorithmgateway.tezkorakfa.databinding.FragmentAcceptOrderServisBinding


class AcceptOrderServisFragment : Fragment() {
    private var _binding: FragmentAcceptOrderServisBinding? = null
    private val binding get() = _binding ?: throw NullPointerException("null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAcceptOrderServisBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}