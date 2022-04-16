package uz.algorithmgateway.measurer.ui.orders.orderList

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import org.koin.android.ext.android.get
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import uz.algorithmgateway.data.api.models.Order
import uz.algorithmgateway.measurer.databinding.FragmentTabLayoutBinding
import uz.algorithmgateway.measurer.ui.orders.AdapterOnClick
import uz.algorithmgateway.measurer.ui.orders.OrderListAdapter
import uz.algorithmgateway.measurer.ui.orders.mainFragment.OrdersFragmentDirections

/**
 * Created by Abrorjon Berdiyorov on 12.03.2022
 */


class OrderListFragment(
    private val navController: NavController,
    private val status: String
) : MvpAppCompatFragment(),
    OrderListFragmentView, AdapterOnClick {
    private var _binding: FragmentTabLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: OrderListAdapter


    @InjectPresenter
    lateinit var presenter: OrderListFragmentPresenter

    @ProvidePresenter
    fun providePresenter(): OrderListFragmentPresenter {
        val presenter: OrderListFragmentPresenter = get()
        presenter.setUp(status)
        return presenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun bindViews() {
        adapter = OrderListAdapter(
            { navController.navigate(OrdersFragmentDirections.toAcceptOrder()) },
            this
        )
        binding.rvOrders.adapter = adapter
    }

    override fun updateOrdersList(orderList: List<Order>) {
        adapter.updateList(orderList)
        binding.tvOrdersNotFound.visibility =
            if (orderList.isEmpty()) View.VISIBLE else View.INVISIBLE
    }

    override fun showError(error: String) {
        Toast.makeText(activity, "Error: $error", Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {

        Toast.makeText(activity, "show progress", Toast.LENGTH_SHORT).show()
    }

    override fun hideProgress() {

        Toast.makeText(activity, "hide progress", Toast.LENGTH_SHORT).show()
    }

    override fun onCallClick(number: String) {
        val uri = Uri.parse("tel: $number")
        val intent = Intent(Intent.ACTION_DIAL, uri)
        requireActivity().startActivity(intent)
    }
}