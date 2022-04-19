package uz.algorithmgateway.tezkorakfa.measurer.ui.orders.orderList

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.core.util.toast
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.Result
import uz.algorithmgateway.tezkorakfa.databinding.FragmentTabLayoutBinding
import uz.algorithmgateway.tezkorakfa.measurer.ui.orders.OrderListAdapter
import uz.algorithmgateway.tezkorakfa.ui.login.viewmodel.LoginViewModel
import uz.algorithmgateway.tezkorakfa.ui.utils.UIState
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class OrderListFragment(
    private val status: String,
) : Fragment(), CoroutineScope {
    @Inject
    lateinit var loginViewModel: LoginViewModel

    private lateinit var adapter: OrderListAdapter

    private var _binding: FragmentTabLayoutBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        MyApplication.appComponent.orders(this)
        _binding = FragmentTabLayoutBinding.inflate(inflater, container, false)

        launch(Dispatchers.Main) {
            loginViewModel.getOrder()
            loginViewModel.order.collect { list ->
                when (list) {
                    is UIState.Loading -> {

                    }
                    is UIState.Error -> {
                        toast(list.message)
                    }
                    is UIState.Success -> {
                        adapter = OrderListAdapter(
                            object : OrderListAdapter.onClick {
                                override fun onCallClick(number: String) {
                                    val uri = Uri.parse("tel: $number")
                                    val intent = Intent(Intent.ACTION_DIAL, uri)
                                    requireActivity().startActivity(intent)
                                }

                                override fun onAcceptClick(item: Result) {
                                    val bundle = Bundle()
//                                    bundle.putSerializable("item",item)
                                    findNavController().navigate(R.id.acceptOrderScreen, bundle)
                                }
                            }
                        )
                        binding.rvOrders.adapter = adapter
                        adapter.updateList(list.data?.results ?: emptyList())
                    }
                }
            }
        }

        return binding.root
    }

    override val coroutineContext: CoroutineContext
        get() = Job()

}