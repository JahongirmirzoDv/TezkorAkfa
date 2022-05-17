package uz.algorithmgateway.tezkorakfa.supplier.orderHistory

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayout
import uz.algorithmgateway.tezkorakfa.data.models.OrderSupplier
import uz.algorithmgateway.supplier.orderList.InterfaceOrderClick
import uz.algorithmgateway.tezkorakfa.databinding.FragmentOrderHistoryBinding
import java.text.SimpleDateFormat
import java.util.*

class OrderHistoryFragment : Fragment(), InterfaceOrderClick {

    lateinit var binding: FragmentOrderHistoryBinding

    private val orderList: List<OrderSupplier> = createOrderList()
    private var orderListAdapter: AdapterOrderHistory? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadTabDate()
        loadDateRangePicker()
        loadBackButton()
        loadOrderList()
    }

    private fun loadOrderList() {
        orderListAdapter = AdapterOrderHistory(this)
        binding.rvOrderList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvOrderList.adapter = orderListAdapter

        orderListAdapter!!.updateList(orderList)
    }


    private fun loadBackButton() {
//        binding.btnBack.setOnClickListener {
//
//        }
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
            binding.tvDateRangePicker.text =
                dateFormat.format(it.first) + " - " + dateFormat.format(it.first)
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