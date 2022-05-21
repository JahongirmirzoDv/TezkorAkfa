package uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.confirm_orders.unconfirmed

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.Result
import uz.algorithmgateway.tezkorakfa.databinding.FragmentUnconfirmedBinding
import uz.algorithmgateway.tezkorakfa.presenter.measurer.ui.confirm_orders.adapter.ConfirmOrderListAdapter
import uz.algorithmgateway.tezkorakfa.presenter.measurer.viewmodel.DbViewmodel
import uz.algorithmgateway.tezkorakfa.presenter.measurer.viewmodel.NetworkViewmodel
import uz.algorithmgateway.tezkorakfa.presenter.ui.login.viewmodel.LoginViewModel
import uz.algorithmgateway.tezkorakfa.presenter.ui.utils.SharedPref
import uz.algorithmgateway.tezkorakfa.presenter.ui.utils.UIState
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class UnconfirmedFragment : Fragment(), CoroutineScope {

    @Inject
    lateinit var db: DbViewmodel

    @Inject
    lateinit var viewmodel: LoginViewModel

    @Inject
    lateinit var viewmodeltwo: NetworkViewmodel

    private val sharedPref by lazy { SharedPref(requireContext()) }

    private var _binding: FragmentUnconfirmedBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ConfirmOrderListAdapter
    lateinit var list: MutableStateFlow<Result>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.unconfirmed(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUnconfirmedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        initAction()
        send()
    }

    private fun send() {

    }

    private fun initAction() {
        binding.reload.setOnClickListener {
            loadData()
        }
        binding.configOrder.setOnClickListener {
            findNavController().navigate(R.id.confirmOrdersScreen)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadData() {
        adapter = ConfirmOrderListAdapter()
        binding.recyclerViewOrdersConfirm.adapter = adapter
        launch(Dispatchers.Main) {
            viewmodel.getOrder("not_finished", sharedPref.userId.toString())
            viewmodel.order.collect { list ->
                when (list) {
                    is UIState.Loading -> {
                        binding.progress.visibility = View.VISIBLE
                    }
                    is UIState.Error -> {
                        binding.progress.visibility = View.GONE
                    }
                    is UIState.Success -> {
                        binding.progress.visibility = View.GONE
                        adapter.list = list.data!!.results
                        adapter.notifyDataSetChanged()
                        adapter.onpress = object : ConfirmOrderListAdapter.onPress {
                            override fun complete(result: Result, sum: String) {
                                val navBuilder = NavOptions.Builder()
                                val navOptions: NavOptions =
                                    navBuilder.setPopUpTo(R.id.ordersFragment, false).build()
                                NavHostFragment.findNavController(requireParentFragment())
                                    .navigate(R.id.ordersFragment, null, navOptions)
                                val map: HashMap<String, Any> = HashMap()
                                map["order_id"] = result.id
                                map["paid_price"] = sum
                                launch(Dispatchers.Main) {
                                    viewmodeltwo.confirm("finished", map)
                                    db.deletePdf()
                                    db.delete()
                                }
                            }

                            override fun reject(result: Result) {
                                val map: HashMap<String, Any> = HashMap()
                                map["order_id"] = result.id
                                launch(Dispatchers.Main) {
                                    viewmodeltwo.confirm("canceled", map)
                                }
                            }

                            override fun edit(result: Result) {
                                db.deletePdf()
                                db.delete()
                                val navBuilder = NavOptions.Builder()
                                val navOptions: NavOptions =
                                    navBuilder.setPopUpTo(R.id.ordersFragment, false).build()
                                NavHostFragment.findNavController(requireParentFragment())
                                    .navigate(R.id.ordersFragment, null, navOptions)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}