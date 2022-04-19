package uz.algorithmgateway.tezkorakfa.measurer.ui.orders

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.algorithmgateway.data.const.Value
import uz.algorithmgateway.tezkorakfa.measurer.ui.orders.orderList.OrderListFragment


private const val NUM_TABS = 3

class ViewPagerAdapter(
    fragment: Fragment
) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                OrderListFragment(Value.NEW)
            }
            1 -> {
                OrderListFragment(Value.ACTIVE)
            }
            else -> {
                OrderListFragment(Value.DONE)
            }
        }
    }
}