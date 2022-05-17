package uz.algorithmgateway.tezkorakfa.measurer.ui.orders.orderList

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.core.util.toast
import uz.algorithmgateway.data.const.Value
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.Result
import uz.algorithmgateway.tezkorakfa.databinding.FragmentTabLayoutBinding
import uz.algorithmgateway.tezkorakfa.measurer.ui.orders.OrderListAdapter
import uz.algorithmgateway.tezkorakfa.measurer.viewmodel.DbViewmodel
import uz.algorithmgateway.tezkorakfa.measurer.viewmodel.NetworkViewmodel
import uz.algorithmgateway.tezkorakfa.ui.login.viewmodel.LoginViewModel
import uz.algorithmgateway.tezkorakfa.ui.utils.SharedPref
import uz.algorithmgateway.tezkorakfa.ui.utils.UIState
import uz.algorithmgateway.tezkorakfa.utils.NetworkConnectionLiveData
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class OrderListFragment() : Fragment(), CoroutineScope {
    @Inject
    lateinit var loginViewModel: LoginViewModel

    @Inject
    lateinit var viewmodel: NetworkViewmodel

    @Inject
    lateinit var db_viewmodel: DbViewmodel

    @Inject
    lateinit var networkConnectionLiveData: NetworkConnectionLiveData

    private lateinit var adapter: OrderListAdapter

    private var _binding: FragmentTabLayoutBinding? = null
    private val binding get() = _binding!!
    private val sharedPref by lazy { SharedPref(requireContext()) }
    private var status: String = Value.NEW

    constructor(status: String) : this() {
        this.status = status
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        MyApplication.appComponent.orders(this)
        _binding = FragmentTabLayoutBinding.inflate(inflater, container, false)


        networkConnectionLiveData.observe(requireActivity()) {
            loadData(it)
        }

        return binding.root
    }

    private fun loadData(it: Boolean) {
        if (it) {
            launch(Dispatchers.Main) {
                try {
                    loginViewModel.getOrder(status, if (status == Value.NEW) "" else sharedPref.userId ?: "")
                    loginViewModel.order.collect { list ->
                        when (list) {
                            is UIState.Loading -> {
                                binding.progress.visibility = View.VISIBLE
                                binding.retry.visibility = View.GONE
                            }
                            is UIState.Error -> {
                                toast(list.message)
                                binding.progress.visibility = View.GONE
                                binding.retry.visibility = View.VISIBLE
                                binding.retry.setOnClickListener {
                                    loadData(true)
                                }
                            }
                            is UIState.Success -> {
                                viewmodel.downloadFile(list.data?.results?.get(0)?.show_room_file.toString())
                                adapter = OrderListAdapter(
                                    object : OrderListAdapter.onClick {
                                        override fun onCallClick(number: String) {
                                            val uri = Uri.parse("tel: $number")
                                            val intent = Intent(Intent.ACTION_DIAL, uri)
                                            requireActivity().startActivity(intent)
                                        }

                                        override fun onAcceptClick(item: Result) {
                                            launch {
                                                val map: HashMap<String, Any> = HashMap()
                                                map["order_id"] = item.id
                                                viewmodel.acceptOrder(map)
                                                db_viewmodel.delete()
                                                db_viewmodel.deletePdf()
                                            }
                                            val bundle = Bundle()
                                            val toJson = Gson().toJson(item)
                                            bundle.putString("item", toJson)
                                            findNavController().navigate(R.id.acceptOrderScreen,
                                                bundle)
                                        }
                                    }
                                )
                                binding.rvOrders.adapter = adapter
                                adapter.isCompleted = status == Value.DONE
                                adapter.updateList(list.data?.results ?: emptyList())
                                binding.progress.visibility = View.GONE
                                binding.retry.visibility = View.GONE
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        } else {
            binding.progress.visibility = View.VISIBLE
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Job()


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}