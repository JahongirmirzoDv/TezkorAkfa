package uz.algorithmgateway.tezkorakfa.measurer.ui.confirm_orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.algorithmgateway.data.models.UiConfirmOrder
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.ScreenConfirmOrdersBinding
import uz.algorithmgateway.tezkorakfa.measurer.SpinnerTextAdapter

class ConfirmOrdersScreen : Fragment() {

    private var _binding: ScreenConfirmOrdersBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    private lateinit var adapter: ConfirmOrderListAdapter
    private lateinit var list: ArrayList<UiConfirmOrder>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ScreenConfirmOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //tabLayout load date
        loadTabDateData()

        // spinnerType
        loadSpinnerType()

        loadConfirmOrderData()
    }


    private fun loadConfirmOrderData() {
        adapter = ConfirmOrderListAdapter(dataList()) {
//            navController.navigate(OrdersScreenDirections.toAcceptOrder())
        }

        binding.recyclerViewOrdersConfirm.adapter = adapter
    }

    private fun dataList(): List<UiConfirmOrder> {
        list = ArrayList()

        list.add(UiConfirmOrder(0, R.drawable.ic_cancel_order, true))
        list.add(UiConfirmOrder(0, R.drawable.ic_green, false))
        list.add(UiConfirmOrder(0, R.drawable.ic_green, false))
        list.add(UiConfirmOrder(0, R.drawable.ic_green, false))
        list.add(UiConfirmOrder(0, R.drawable.ic_green, false))
        list.add(UiConfirmOrder(0, R.drawable.ic_green, false))
        list.add(UiConfirmOrder(0, R.drawable.ic_green, false))
        list.add(UiConfirmOrder(0, R.drawable.ic_green, false))
        list.add(UiConfirmOrder(0, R.drawable.ic_green, false))
        list.add(UiConfirmOrder(0, R.drawable.ic_green, false))
        list.add(UiConfirmOrder(0, R.drawable.ic_green, false))
        list.add(UiConfirmOrder(0, R.drawable.ic_green, false))
        list.add(UiConfirmOrder(0, R.drawable.ic_green, false))
        list.add(UiConfirmOrder(0, R.drawable.ic_green, false))


        return list
    }

    private fun loadSpinnerType() {
        val adapter = SpinnerTextAdapter(requireContext(), listProfiTypeData(), false)
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
}