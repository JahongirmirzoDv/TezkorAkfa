package uz.algorithmgateway.tezkorakfa.supplier.purchased

import by.kirich1409.viewbindingdelegate.viewBinding
import uz.algorithmgateway.core.Screen
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.FragmentPurchasedBinding

class PurchasedFragment : Screen(R.layout.fragment_purchased) {
    private var tabState: Int = 0
    private val binding: FragmentPurchasedBinding by viewBinding()
    override fun setup() {
        super.setup()

        //load tab fragment
        loadTabFragment()
    }

    private fun loadTabFragment() {

        val firstFragment = FirstPageFragment()
        val secondFragment = SecondPageFragment()
        if (tabState == 0) {
            parentFragmentManager.beginTransaction().add(
                R.id.fragment_container_view, firstFragment
            ).commit()
            binding.tvTabFirst.setTextColor(resources.getColor(R.color.select_blue))
            binding.tvTabFirst.setBackgroundColor(resources.getColor(R.color.layout_light))
        } else {
            parentFragmentManager.beginTransaction().add(
                R.id.fragment_container_view, secondFragment
            ).commit()
            binding.tvTabSecond.setTextColor(resources.getColor(R.color.select_blue))
            binding.tvTabSecond.setBackgroundColor(resources.getColor(R.color.layout_light))
        }

        binding.tvTabFirst.setOnClickListener {
            if (tabState != 0) {
                parentFragmentManager.beginTransaction().replace(
                    R.id.fragment_container_view, firstFragment
                ).commit()
                binding.tvTabFirst.setTextColor(resources.getColor(R.color.select_blue))
                binding.tvTabFirst.setBackgroundColor(resources.getColor(R.color.layout_light))
                binding.tvTabSecond.setTextColor(resources.getColor(R.color.text_dark_color))
                binding.tvTabSecond.setBackgroundColor(resources.getColor(R.color.layout_back_color))
                tabState = 0
            }
        }
        binding.tvTabSecond.setOnClickListener {
            if (tabState != 1) {
                parentFragmentManager.beginTransaction().replace(
                    R.id.fragment_container_view, secondFragment
                ).commit()
                binding.tvTabFirst.setTextColor(resources.getColor(R.color.text_dark_color))
                binding.tvTabFirst.setBackgroundColor(resources.getColor(R.color.layout_back_color))
                binding.tvTabSecond.setTextColor(resources.getColor(R.color.select_blue))
                binding.tvTabSecond.setBackgroundColor(resources.getColor(R.color.layout_light))
                tabState = 1
            }
        }


    }
}