package uz.algorithmgateway.tezkorakfa.supplier.orderList

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.core.util.toast
import uz.algorithmgateway.tezkorakfa.data.models.OrderSupplier
import uz.algorithmgateway.supplier.orderList.InterfaceOrderClick
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.Result
import uz.algorithmgateway.tezkorakfa.databinding.FragmentOrderListSupBinding
import uz.algorithmgateway.tezkorakfa.presenter.measurer.resource.OrdersListResource
import uz.algorithmgateway.tezkorakfa.presenter.supplier.SupplierActivity
import uz.algorithmgateway.tezkorakfa.presenter.supplier.adapter.AdapterTableSpinner
import uz.algorithmgateway.tezkorakfa.presenter.supplier.orderList.AdapterOrderList
import uz.algorithmgateway.tezkorakfa.presenter.supplier.viewmodel.NetworkViewModel
import uz.algorithmgateway.tezkorakfa.presenter.ui.login.LoginActivity
import uz.algorithmgateway.tezkorakfa.presenter.ui.utils.SharedPref
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class OrderListFragmentSup : Fragment(), CoroutineScope {

    lateinit var binding: FragmentOrderListSupBinding
    private var orderStatus: Int = 0
    private lateinit var orderList: ArrayList<Result>
    private val sharedPref by lazy { SharedPref(requireContext()) }

    //        private val orderList: List<OrderSupplier> = createOrderList()
//    private var orderListAdapter: AdapterOrderList? = null
    lateinit var orderListAdapter: AdapterOrderList

    @Inject
    lateinit var networkViewModel: NetworkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.orderListSupplier(this)
    }

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

        installLogOut()
        loadNetworData()
//        loadOrderList()
        loadOrderStatusSpinner()
        loadSearchView()
//        loadOrderHistoryButton()
    }

    private fun installLogOut() {
        binding.logout.setOnClickListener {
            sharedPref.isLogin = false
            sharedPref.clear()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()
        }

    }

    private fun loadNetworData() {
        launch {
            networkViewModel.getOrdersList().collect {
                when (it) {
                    is OrdersListResource.Error -> {
                        toast(it.message)
                    }
                    OrdersListResource.Loading -> {
                        binding.progressView.visibility = View.VISIBLE
                    }
                    is OrdersListResource.SuccesList -> {
                        loadOrderList(it.list.results)
                        orderList = ArrayList()
                        orderList.addAll(it.list.results)
                        binding.progressView.visibility = View.GONE
                    }
                }

            }
        }

    }


    private fun loadSearchView() {
        binding.editTextSearch.doOnTextChanged { text, start, before, count ->

            val filterList: List<Result> = if (orderStatus == 0) {
                orderList
            } else {
                orderList.filter { s -> s.id == orderStatus }
            }
//            val filterList: List<OrderSupplier> = if (orderStatus == 0) {
//                orderList
//            } else {
//                orderList.filter { s -> s.status == orderStatus }
//        }
//
            val searchList = mutableListOf<Result>()
            for (i in orderList) {
                if (text.toString().toRegex().find(i.id.toString()) != null) {
                    searchList.add(i)
                }
            }
//
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
//        val filterList: List<OrderSupplier> = if (position == 0) {
//            orderList
//        } else {
//            orderList.filter { s -> s.status == position }
//        }
//        filterList.let {
//            orderListAdapter?.updateList(filterList)
//        }
    }

    private fun loadOrderList(list: List<Result>) {
        orderListAdapter = AdapterOrderList(list as ArrayList<Result>) {
//        orderListAdapter = AdapterOrderList() {
            findNavController().navigate(R.id.productListFragment)
        }
        binding.rvOrderList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvOrderList.adapter = orderListAdapter
    }

//    override fun onItemClick(order: OrderSupplier) {
//        Toast.makeText(requireContext(), order.id.toString(), Toast.LENGTH_SHORT).show()
//    }

    private fun orderStatusList(): List<String> = listOf(
        "Barchasi",
        "Topilganlar",
        "Topilmaganlar",
        "Kechikkanlar"
    )

    private fun createOrderList(): List<OrderSupplier> = listOf(
        OrderSupplier(1024, "3 500 000", "06.02.2022", 1),
        OrderSupplier(1025, "3 500 000", "06.02.2022", 2),
        OrderSupplier(1026, "5 500 000", "06.02.2022", 2),
        OrderSupplier(1027, "6 500 000", "06.02.2022", 3),
        OrderSupplier(1028, "7 800 000", "06.02.2022", 1),
        OrderSupplier(1029, "3 500 000", "06.02.2022", 1),
        OrderSupplier(1030, "3 500 000", "06.02.2022", 3),
        OrderSupplier(1032, "3 500 000", "06.02.2022", 1)
    )

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

}
