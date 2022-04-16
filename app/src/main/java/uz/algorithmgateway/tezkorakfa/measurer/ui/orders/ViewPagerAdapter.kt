package uz.algorithmgateway.measurer.ui.orders

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.algorithmgateway.data.const.Value
import uz.algorithmgateway.tezkorakfa.measurer.ui.orders.orderList.OrderListFragment

/**
 * Created by Abrorjon Berdiyorov on 12.03.2022
 */


private const val NUM_TABS = 3


class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val navController: NavController
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                OrderListFragment(navController, Value.NEW)
            }
            1 -> {
                OrderListFragment(navController, Value.ACTIVE)
            }
            else -> {
                OrderListFragment(navController, Value.DONE)
            }
        }
    }
}