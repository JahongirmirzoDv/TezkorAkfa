package uz.algorithmgateway.tezkorakfa.presenter.supplier.orderList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.algorithmgateway.tezkorakfa.data.models.OrderSupplier
import uz.algorithmgateway.supplier.orderList.InterfaceOrderClick
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.FragmentOrderListSupBinding
import uz.algorithmgateway.tezkorakfa.presenter.supplier.SupplierActivity
import uz.algorithmgateway.tezkorakfa.presenter.supplier.adapter.AdapterTableSpinner

class OrderListFragmentSup : Fragment(), InterfaceOrderClick {

    lateinit var binding: FragmentOrderListSupBinding

    private var orderStatus: Int = 0
    private val orderList: List<OrderSupplier> = createOrderList()
    private var orderListAdapter: AdapterOrderList? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOrderListSupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = activity as SupplierActivity
        mainActivity.bottomNavigationViewVisibility()
        loadOrderList()
        loadOrderStatusSpinner()
        loadSearchView()
//        loadOrderHistoryButton()
    }

//    private fun loadOrderHistoryButton() {
//        binding.btnOrderHistory.setOnClickListener {
//            findNavController().navigate(R.id.orderHistoryFragment)
//        }
//    }

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
                    id: Long,
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
        orderListAdapter = AdapterOrderList {
            findNavController().navigate(R.id.productListFragment)
        }
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