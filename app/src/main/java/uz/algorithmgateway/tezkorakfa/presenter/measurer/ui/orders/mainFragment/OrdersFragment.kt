package uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.orders.mainFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import uz.algorithmgateway.tezkorakfa.databinding.ScreenOrdersBinding
import uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.orders.ViewPagerAdapter
import uz.algorithmgateway.tezkorakfa.presenter.ui.login.LoginActivity
import uz.algorithmgateway.tezkorakfa.presenter.ui.utils.SharedPref

val orderTabItemsArray = arrayOf(
    "Buyurtmalar",
    "Mening buyurtmalarim",
    "Buyurtmalar tarixi"
)


class OrdersFragment : Fragment() {

    private var _binding: ScreenOrdersBinding? = null
    private val binding get() = _binding!!
    private val sharedPref by lazy { SharedPref(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ScreenOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViewPagerAndTabLayout()

        binding.logout.setOnClickListener {
            sharedPref.isLogin = false
            sharedPref.clear()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun bindViewPagerAndTabLayout() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabOrders

        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = orderTabItemsArray[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}