package uz.algorithmgateway.tezkorakfa.supplier.purchased

import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieProperty.COLOR
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.FragmentPurchasedBinding

class PurchasedFragment : Fragment() {
    private var tabState: Int = 0
    lateinit var binding: FragmentPurchasedBinding
    lateinit var adapterViewPager2: AdapterViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPurchasedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        loadTabFragment()

        laodTablayout()
        loadViewPager2()

    }

    private fun loadViewPager2() {
        adapterViewPager2 = AdapterViewPager2(childFragmentManager, lifecycle)
        binding.viewPager2.adapter = adapterViewPager2
        TabLayoutMediator(binding.layoutTab, binding.viewPager2) { tab, position ->
//            if (position == 0) {
//                binding.layoutTab.tabTextColors = resources.getColor(R.color.blue, R.color.white)
//            }

        }.attach()

        binding.layoutTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {


            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun laodTablayout() {
        with(binding) {
            layoutTab.addTab(layoutTab.newTab().setText("Sotib olish").setId(0))
            layoutTab.addTab(layoutTab.newTab().setText("Sotib olingan").setId(1))
        }

    }


    private fun loadTabFragment() {
        if (tabState == 0) {
//            findNavController().navigate(R.id.firstPageFragment)
//            binding.layoutTab.setTextColor(resources.getColor(R.color.select_blue))
//            binding.tvTabFirst.setBackgroundColor(resources.getColor(R.color.layout_light))
        } else {
//            findNavController().navigate(R.id.secondPageFragment)
//            binding.tvTabSecond.setTextColor(resources.getColor(R.color.select_blue))
//            binding.tvTabSecond.setBackgroundColor(resources.getColor(R.color.layout_light))
        }

//        binding.tvTabFirst.setOnClickListener {
//            if (tabState != 0) {
//                findNavController().navigate(R.id.firstPageFragment)
//                binding.tvTabFirst.setTextColor(resources.getColor(R.color.select_blue))
//                binding.tvTabFirst.setBackgroundColor(resources.getColor(R.color.layout_light))
//                binding.tvTabSecond.setTextColor(resources.getColor(R.color.text_dark_color))
//                binding.tvTabSecond.setBackgroundColor(resources.getColor(R.color.layout_back_color))
//                tabState = 0
//            }
//        }
//        binding.tvTabSecond.setOnClickListener {
//            if (tabState != 1) {
//                findNavController().navigate(R.id.secondPageFragment)
//                binding.tvTabFirst.setTextColor(resources.getColor(R.color.text_dark_color))
//                binding.tvTabFirst.setBackgroundColor(resources.getColor(R.color.layout_back_color))
//                binding.tvTabSecond.setTextColor(resources.getColor(R.color.select_blue))
//                binding.tvTabSecond.setBackgroundColor(resources.getColor(R.color.layout_light))
//                tabState = 1
//            }
//        }


    }
}