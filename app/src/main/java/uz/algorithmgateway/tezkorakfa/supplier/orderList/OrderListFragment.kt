package uz.algorithmgateway.tezkorakfa.supplier.orderList

import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.algorithmgateway.core.Screen
import uz.algorithmgateway.data.models.OrderSupplier
import uz.algorithmgateway.supplier.orderList.InterfaceOrderClick
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.FragmentOrderListBinding
import uz.algorithmgateway.tezkorakfa.supplier.adapter.AdapterTableSpinner
import uz.algorithmgateway.tezkorakfa.supplier.orderHistory.OrderHistoryFragment

class OrderListFragment : Screen(R.layout.fragment_order_list), InterfaceOrderClick {

    private val binding: FragmentOrderListBinding by viewBinding()

    private var orderStatus: Int = 0
    private val orderList: List<OrderSupplier> = createOrderList()
    private var orderListAdapter: AdapterOrderList? = null

    override fun setup() {
        super.setup()

        //load list
        loadOrderList()

        //load spinner
        loadOrderStatusSpinner()

        //load search view
        loadSearchView()

        loadOrderHistoryButton()
    }

    private fun loadOrderHistoryButton() {
        val orderHistoryFragment = OrderHistoryFragment()
        binding.btnOrderHistory.setOnClickListener {
            activity?.supportFragmentManager!!.beginTransaction().apply {
                add(R.id.containerFragmentSupplier, orderHistoryFragment)
                commit()
            }
        }
    }

    private fun loadSearchView() {
        binding.editTextSearch.doOnTextChanged { text, start, before, count ->

            val filterList: List<OrderSupplier> = if (orderStatus == 0) {
                orderList
            } else {
                orderList.filter { s -> s.status == orderStatus }
            }

            val searchList = mutableListOf<OrderSupplier>()
            for (i in filterList) {
                if (text.toString().toRegex().find(i.id.toString()) != null) {
                    searchList.add(i)
                }
            }

            searchList.let {
                orderListAdapter?.updateList(searchList)
            }


        }
    }

    private fun loadOrderStatusSpinner() {
        val adapter = AdapterTableSpinner(requireContext(), orderStatusList(), true)
        binding.spinnerOrderStatus.adapter = adapter
        binding.spinnerOrderStatus.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    orderStatus = position
                    filterOrderList(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun filterOrderList(position: Int) {
        val filterList: List<OrderSupplier> = if (position == 0) {
            orderList
        } else {
            orderList.filter { s -> s.status == position }
        }
        filterList.let {
            orderListAdapter?.updateList(filterList)
        }
    }

    private fun loadOrderList() {
        orderListAdapter = AdapterOrderList(this)
        binding.rvOrderList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvOrderList.adapter = orderListAdapter
    }

    override fun onItemClick(order: OrderSupplier) {
        Toast.makeText(requireContext(), order.id.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun orderStatusList(): List<String> = listOf(
        "Barchasi",
        "Topilganlar",
        "Topilmaganlar",
        "Kechikkanlar"
    )

    private fun createOrderList(): List<OrderSupplier> = listOf(
        OrderSupplier(1024, "3 500 000", "06.02.2022", 1),
        OrderSupplier(1025, "3 500 000", "06.02.2022", 2),
        OrderSupplier(1026, "3 500 000", "06.02.2022", 2),
        OrderSupplier(1027, "3 500 000", "06.02.2022", 3),
        OrderSupplier(1028, "3 500 000", "06.02.2022", 1),
        OrderSupplier(1029, "3 500 000", "06.02.2022", 1),
        OrderSupplier(1030, "3 500 000", "06.02.2022", 3),
        OrderSupplier(1032, "3 500 000", "06.02.2022", 1)
    )

}