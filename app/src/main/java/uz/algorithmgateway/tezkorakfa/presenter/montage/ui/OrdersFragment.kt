package uz.algorithmgateway.tezkorakfa.presenter.montage.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.FragmentOrdersBinding
import uz.algorithmgateway.tezkorakfa.presenter.ui.login.LoginActivity
import uz.algorithmgateway.tezkorakfa.presenter.ui.utils.SharedPref


class OrdersFragment : Fragment() {
    //    private var _binding: FragmentOrdersBinding? = null
//    private val binding get() = _binding ?: throw NullPointerException("null")
    lateinit var binding: FragmentOrdersBinding
    private val sharedPref by lazy { SharedPref(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)

        installLogOut()

        return binding.root
    }

    private fun installLogOut() {
        binding.logout.setOnClickListener {
            sharedPref.isLogin = false
            sharedPref.clear()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }

    }


}