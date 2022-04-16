package uz.algorithmgateway.tezkorakfa.supplier.orderHistory

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayout
import uz.algorithmgateway.core.Screen
import uz.algorithmgateway.data.models.OrderSupplier
import uz.algorithmgateway.supplier.orderList.InterfaceOrderClick
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.FragmentOrderHistoryBinding
import java.text.SimpleDateFormat
import java.util.*

class OrderHistoryFragment : Screen(R.layout.fragment_order_history), InterfaceOrderClick {

    private val binding: FragmentOrderHistoryBinding by viewBinding()

    private val orderList: List<OrderSupplier> = createOrderList()
    private var orderListAdapter: AdapterOrderHistory? = null

    override fun setup() {
        super.setup()

        loadTabDate()

        loadDateRangePicker()

        loadBackButton()

        loadOrderList()
    }

    override fun initialize() {
        super.initialize()
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.supportFragmentManager!!.beginTransaction().apply {
                    remove(this@OrderHistoryFragment)
                    commit()
                }
            }
        })
    }

    private fun loadOrderList() {
        orderListAdapter = AdapterOrderHistory(this)
        binding.rvOrderList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvOrderList.adapter = orderListAdapter

        orderListAdapter!!.updateList(orderList)
    }


    private fun loadBackButton() {
        binding.btnBack.setOnClickListener {
            activity?.supportFragmentManager!!.beginTransaction().apply {
                remove(this@OrderHistoryFragment)
                commit()
            }
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun loadDateRangePicker() {
        val now = Calendar.getInstance()

        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .setSelection(
                    androidx.core.util.Pair(
                        now.timeInMillis, now.timeInMillis
                    )
                )
                .build()
        binding.tvDateRangePicker.setOnClickListener {
            dateRangePicker.show(
                activity?.supportFragmentManager!!, "dateRangePicker"
            )
        }
        val dateFormat = SimpleDateFormat("dd MMM yyyy");
        dateRangePicker.addOnNegativeButtonClickListener { dateRangePicker.dismiss() }
        dateRangePicker.addOnPositiveButtonClickListener {
            binding.tvDateRangePicker.text = dateFormat.format(it.first) + " - " + dateFormat.format(it.first)
        }
    }

    private fun loadTabDate() {
        with(binding) {
            tabLayoutDate.addTab(tabLayoutDate.newTab().setText("Bugun").setId(0))
            tabLayoutDate.addTab(tabLayoutDate.newTab().setText("Hafta").setId(1))
            tabLayoutDate.addTab(tabLayoutDate.newTab().setText("Oy").setId(2))
            tabLayoutDate.addTab(tabLayoutDate.newTab().setText("Barchasi").setId(3))
        }

        binding.tabLayoutDate.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                onDateTabSelected(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }

    private fun onDateTabSelected(tab: TabLayout.Tab?) {
        val now = Calendar.getInstance()
        //TODO filter date
    }

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

    override fun onItemClick(order: OrderSupplier) {
        Toast.makeText(requireContext(), order.id.toString(), Toast.LENGTH_SHORT).show()
    }

}