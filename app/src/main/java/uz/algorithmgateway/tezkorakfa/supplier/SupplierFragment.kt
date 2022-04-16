package uz.algorithmgateway.tezkorakfa.supplier

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.algorithmgateway.core.Screen
import uz.algorithmgateway.tezkorakfa.supplier.foundList.FoundListFragment
import uz.algorithmgateway.tezkorakfa.supplier.givenMoney.GivenMoneyFragment
import uz.algorithmgateway.tezkorakfa.supplier.orderList.OrderListFragment
import uz.algorithmgateway.tezkorakfa.supplier.productList.ProductListFragment
import uz.algorithmgateway.tezkorakfa.supplier.purchased.PurchasedFragment
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.FragmentSupplierBinding

class SupplierFragment:Screen(R.layout.fragment_supplier) {

    private val binding: FragmentSupplierBinding by viewBinding()

    override fun setup() {
        super.setup()

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val productListFragment = ProductListFragment()
        val orderListFragment = OrderListFragment()
        val foundListFragment = FoundListFragment()
        val givenMoneyFragment = GivenMoneyFragment()
        val purchasedFragment = PurchasedFragment()

        setCurrentFragment(productListFragment)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.page_1 -> {
                    setCurrentFragment(productListFragment)
                    true
                }
                R.id.page_2 -> {
                    setCurrentFragment(orderListFragment)
                    true
                }
                R.id.page_3 -> {
                    setCurrentFragment(foundListFragment)
                    true
                }
                R.id.page_4 -> {
                    setCurrentFragment(givenMoneyFragment)
                    true
                }
                R.id.page_5 -> {
                    setCurrentFragment(purchasedFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
    }

}