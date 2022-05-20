package uz.algorithmgateway.tezkorakfa.presenter.servis.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.algorithmgateway.tezkorakfa.databinding.FragmentServisHomeBinding
import uz.algorithmgateway.tezkorakfa.presenter.ui.login.LoginActivity
import uz.algorithmgateway.tezkorakfa.presenter.ui.utils.SharedPref


class ServisHomeFragment : Fragment() {
    private lateinit var binding: FragmentServisHomeBinding
    private val sharedPref by lazy { SharedPref(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentServisHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        installToolbar()
    }

    private fun installToolbar() {
        binding.apply {
//            othserToolbar.visibility = View.GONE
//            backArrowFragment.visibility = View.GONE
//            titleTolbar.text = "Buyurtmalar"
//            searchToolbar.setBackgroundResource(R.drawable.ic_logout)
//            searchToolbar.setOnClickListener {
            logout.setOnClickListener {
                sharedPref.isLogin = false
                sharedPref.clear()
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }

    }

}


