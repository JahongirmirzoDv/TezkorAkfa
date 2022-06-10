package uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.orders

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.algorithmgateway.tezkorakfa.data.const.Value
import uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.orders.orderList.OrderListFragment


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