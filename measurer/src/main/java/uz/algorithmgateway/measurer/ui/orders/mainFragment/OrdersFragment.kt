package uz.algorithmgateway.measurer.ui.orders.mainFragment

import uz.algorithmgateway.measurer.databinding.ScreenOrdersBinding
import android.os.Bundle
import android.view.*
import android.widget.Toast
import org.koin.android.ext.android.get
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uz.algorithmgateway.measurer.ui.orders.ViewPagerAdapter

val orderTabItemsArray = arrayOf(
    "Buyurtmalar",
    "Mening buyurtmalarim",
    "Buyurtmalar tarixi"
)


class OrdersFragment : MvpAppCompatFragment(), OrdersFragmentView {

    @InjectPresenter
    lateinit var presenter: OrdersFragmentPresenter

    @ProvidePresenter
    fun providePresenter():OrdersFragmentPresenter = get()

    private var _binding: ScreenOrdersBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    override fun showToast() {
        Toast.makeText(activity, "Toast", Toast.LENGTH_SHORT).show()
    }
}