package uz.algorithmgateway.tezkorakfa.measurer.ui.orders.orderList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import uz.algorithmgateway.data.retrofit.ApiService
import uz.algorithmgateway.tezkorakfa.databinding.FragmentTabLayoutBinding
import uz.algorithmgateway.tezkorakfa.ui.login.viewmodel.LoginViewModel
import javax.inject.Inject

/**
 * Created by Abrorjon Berdiyorov on 12.03.2022
 */


class OrderListFragment(
    private val navController: NavController,
    private val status: String,
) : Fragment() {
    @Inject
    lateinit var apiService: ApiService

    lateinit var loginViewModel: LoginViewModel

    private var _binding: FragmentTabLayoutBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTabLayoutBinding.inflate(inflater, container, false)

        return binding.root
    }

}