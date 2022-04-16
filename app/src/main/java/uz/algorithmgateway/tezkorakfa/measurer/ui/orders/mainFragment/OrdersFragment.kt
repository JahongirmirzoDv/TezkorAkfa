package uz.algorithmgateway.tezkorakfa.measurer.ui.orders.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import uz.algorithmgateway.measurer.ui.orders.ViewPagerAdapter
import uz.algorithmgateway.tezkorakfa.databinding.ScreenOrdersBinding

val orderTabItemsArray = arrayOf(
    "Buyurtmalar",
    "Mening buyurtmalarim",
    "Buyurtmalar tarixi"
)


class OrdersFragment : Fragment() {

    private var _binding: ScreenOrdersBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }


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


    }

    private fun bindViewPagerAndTabLayout() {
        val viewPager = binding.viewPager
        val tabLayout = binding.tabOrders

        val adapter = ViewPagerAdapter(parentFragmentManager, lifecycle, navController)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = orderTabItemsArray[position]
        }.attach()
    }

}