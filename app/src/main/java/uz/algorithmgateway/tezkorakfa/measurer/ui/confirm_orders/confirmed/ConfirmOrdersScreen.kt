package uz.algorithmgateway.tezkorakfa.measurer.ui.confirm_orders.confirmed

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.Result
import uz.algorithmgateway.tezkorakfa.databinding.ScreenConfirmOrdersBinding
import uz.algorithmgateway.tezkorakfa.measurer.SpinnerTextAdapter
import uz.algorithmgateway.tezkorakfa.measurer.ui.confirm_orders.adapter.ConfirmOrderListAdapter
import uz.algorithmgateway.tezkorakfa.measurer.viewmodel.NetworkViewmodel
import uz.algorithmgateway.tezkorakfa.ui.login.viewmodel.LoginViewModel
import uz.algorithmgateway.tezkorakfa.ui.utils.SharedPref
import uz.algorithmgateway.tezkorakfa.ui.utils.UIState
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class ConfirmOrdersScreen : Fragment(), CoroutineScope {
    @Inject
    lateinit var viewmodel: LoginViewModel

    @Inject
    lateinit var viewmodeltwo: NetworkViewmodel

    private var _binding: ScreenConfirmOrdersBinding? = null
    private val binding get() = _binding!!

    private val sharedPref by lazy { SharedPref(requireContext()) }


    private lateinit var adapter: ConfirmOrderListAdapter
    lateinit var list: MutableStateFlow<Result>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.confirm(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ScreenConfirmOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //tabLayout load date
        loadTabDateData()

        // spinnerType
        loadSpinnerType()
        loadData()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun loadData() {
        adapter = ConfirmOrderListAdapter()
        binding.recyclerViewOrdersConfirm.adapter = adapter
        launch(Dispatchers.Main) {
            viewmodel.getOrder("finished", sharedPref.userId.toString())
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
                        adapter.isCompleted = true
                        adapter.notifyDataSetChanged()
                        adapter.onpress = object : ConfirmOrderListAdapter.onPress {
                            override fun complete(result: Result) {

                            }

                            override fun reject(result: Result) {

                            }

                            override fun edit(result: Result) {

                            }
                        }
                    }
                }
            }
        }
    }


    private fun loadSpinnerType() {
        val adapter = SpinnerTextAdapter(requireContext())
        adapter.list = listProfiTypeData()
        binding.spinnerType.adapter = adapter
    }

    private fun listProfiTypeData(): List<String> = arrayListOf<String>(
        "Bekor qilinganlar",
        "Yakunlanganlar",
        "Havoda"
    )


    private fun loadTabDateData() {
        with(binding) {
            tabLayoutDate.addTab(tabLayoutDate.newTab().setText("Oy"))
            tabLayoutDate.addTab(tabLayoutDate.newTab().setText("Hafta"))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override val coroutineContext: CoroutineContext
        get() = Job()
}