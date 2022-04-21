package uz.algorithmgateway.tezkorakfa.supplier.purchased

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.FragmentPurchasedBinding

class PurchasedFragment : Fragment() {
    private var tabState: Int = 0
    lateinit var binding: FragmentPurchasedBinding

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
        loadTabFragment()
    }

    private fun loadTabFragment() {
        if (tabState == 0) {
            findNavController().navigate(R.id.firstPageFragment)
            binding.tvTabFirst.setTextColor(resources.getColor(R.color.select_blue))
            binding.tvTabFirst.setBackgroundColor(resources.getColor(R.color.layout_light))
        } else {
            findNavController().navigate(R.id.secondPageFragment)
            binding.tvTabSecond.setTextColor(resources.getColor(R.color.select_blue))
            binding.tvTabSecond.setBackgroundColor(resources.getColor(R.color.layout_light))
        }

        binding.tvTabFirst.setOnClickListener {
            if (tabState != 0) {
                findNavController().navigate(R.id.firstPageFragment)
                binding.tvTabFirst.setTextColor(resources.getColor(R.color.select_blue))
                binding.tvTabFirst.setBackgroundColor(resources.getColor(R.color.layout_light))
                binding.tvTabSecond.setTextColor(resources.getColor(R.color.text_dark_color))
                binding.tvTabSecond.setBackgroundColor(resources.getColor(R.color.layout_back_color))
                tabState = 0
            }
        }
        binding.tvTabSecond.setOnClickListener {
            if (tabState != 1) {
                findNavController().navigate(R.id.secondPageFragment)
                binding.tvTabFirst.setTextColor(resources.getColor(R.color.text_dark_color))
                binding.tvTabFirst.setBackgroundColor(resources.getColor(R.color.layout_back_color))
                binding.tvTabSecond.setTextColor(resources.getColor(R.color.select_blue))
                binding.tvTabSecond.setBackgroundColor(resources.getColor(R.color.layout_light))
                tabState = 1
            }
        }


    }
}