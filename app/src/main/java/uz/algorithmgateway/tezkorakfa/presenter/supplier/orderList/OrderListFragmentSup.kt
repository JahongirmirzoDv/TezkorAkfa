package uz.algorithmgateway.tezkorakfa.presenter.supplier.orderList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.core.util.toast
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.SupplierOrderlist
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.SupplierOrderlistItem
import uz.algorithmgateway.tezkorakfa.databinding.FragmentOrderListSupBinding
import uz.algorithmgateway.tezkorakfa.presenter.supplier.SupplierActivity
import uz.algorithmgateway.tezkorakfa.presenter.supplier.adapter.AdapterTableSpinner
import uz.algorithmgateway.tezkorakfa.presenter.supplier.resource.OrdersListResource
import uz.algorithmgateway.tezkorakfa.presenter.supplier.viewmodel.NetworkViewModel
import uz.algorithmgateway.tezkorakfa.presenter.ui.login.LoginActivity
import uz.algorithmgateway.tezkorakfa.presenter.ui.utils.SharedPref
import uz.algorithmgateway.tezkorakfa.utils.Anim
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class OrderListFragmentSup : Fragment(R.layout.fragment_order_list_sup), CoroutineScope {

    lateinit var binding: FragmentOrderListSupBinding
    private var orderStatus: Int = 0

    private lateinit var orderList: ArrayList<SupplierOrderlistItem>
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
        orderList = SupplierOrderlist()

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
                        loadOrderList(it.list)
//                        orderList = ArrayList()
                        orderList = it.list
                        binding.progressView.visibility = View.GONE
                    }
                }

            }
        }

    }


    private fun loadSearchView() {
        val list = ArrayList<SupplierOrderlistItem>()
        binding.editTextSearch.doOnTextChanged { text, start, before, count ->
            val text_ = text.toString()
            if (text_ != "") {
                for (i in orderList) {
                    if (i.contract_number.toString().contains(text_.toRegex())) {
                        list.add(i)
                        loadOrderList(list)
                    }
                }
            } else loadOrderList(orderList)
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
                    val filterOrderList = filterOrderList(position)
                    loadOrderList(filterOrderList)
                    Log.e("TAG", "onItemSelected: $position")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun filterOrderList(position: Int): ArrayList<SupplierOrderlistItem> {
        val list = ArrayList<SupplierOrderlistItem>()
        return when (position) {
            0 -> {
                orderList
            }
            1 -> {
                for (i in orderList) {
                    if (i.purchase_count == i.purchase_all_count) list.add(i)
                }
                return list
            }
            2 -> {
                for (i in orderList) {
                    if (i.purchase_count != i.purchase_all_count) list.add(i)
                }
                return list
            }
            else -> {
                orderList
            }
        }
    }

    private fun loadOrderList(list: ArrayList<SupplierOrderlistItem>) {
        orderListAdapter = AdapterOrderList(list) {
//        orderListAdapter = AdapterOrderList() {
            val bundle = Bundle()
            bundle.putString("product_id", it)
            findNavController().navigate(R.id.productListFragment, bundle, Anim.navOptions)
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
    )

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

}
