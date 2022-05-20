package uz.algorithmgateway.tezkorakfa.presenter.servis.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.data.models.OrderSupplier
import uz.algorithmgateway.tezkorakfa.databinding.FragmentServisHomeBinding
import uz.algorithmgateway.tezkorakfa.presenter.supplier.orderList.AdapterOrderList
import uz.algorithmgateway.tezkorakfa.presenter.ui.login.LoginActivity
import uz.algorithmgateway.tezkorakfa.presenter.ui.utils.SharedPref


class ServisHomeFragment : Fragment() {
    private lateinit var binding: FragmentServisHomeBinding
    private val sharedPref by lazy { SharedPref(requireContext()) }
    private var orderListAdapter: AdapterOrderList? = null
    private val orderList: List<OrderSupplier> = createOrderList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentServisHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        installToolbar()
        loadOrderList()
    }

    private fun installToolbar() {
        binding.apply {
//            othserToolbar.visibility = View.GONE
//            backArrowFragment.visibility = View.GONE
//            titleTolbar.text = "Buyurtmalar"
//            searchToolbar.setBackgroundResource(R.drawable.ic_logout)
//            searchToolbar.setOnClickListener {
            logout.setOnClickListener {
                sharedPref.isLogin = false
                sharedPref.clear()
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }

    }

    private fun loadOrderList() {
//        orderListAdapter = AdapterOrderList(list as ArrayList<Result>) {
        orderListAdapter = AdapterOrderList() {
            findNavController().navigate(R.id.orderInfoFragment)
        }
        orderListAdapter?.updateList(orderList)
        binding.rvOrderList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvOrderList.adapter = orderListAdapter
    }

    private fun createOrderList(): List<OrderSupplier> = listOf(
        OrderSupplier(1024, "Alisher Fayz", "09.04.2022", 1),
        OrderSupplier(1025, "Alisher Uzoqov", "06.05.2022", 2),
        OrderSupplier(1026, "Alisher Sadullayev", "06.08.2022", 2),
        OrderSupplier(1027, "Alisher Zokirov", "06.02.2022", 3),
        OrderSupplier(1028, "Alisher Fayz", "09.04.2022", 1),
        OrderSupplier(1055, "Alisher Uzoqov", "06.05.2022", 2),
        OrderSupplier(1016, "Alisher Sadullayev", "06.08.2022", 2),
        OrderSupplier(1067, "Alisher Zokirov", "06.02.2022", 3),
        OrderSupplier(1024, "Alisher Fayz", "09.04.2022", 1),
        OrderSupplier(1025, "Alisher Uzoqov", "06.05.2022", 2),
        OrderSupplier(1026, "Alisher Sadullayev", "06.08.2022", 2),
        OrderSupplier(1027, "Alisher Zokirov", "06.02.2022", 3),
        OrderSupplier(1028, "Alisher Fayz", "09.04.2022", 1),
        OrderSupplier(1055, "Alisher Uzoqov", "06.05.2022", 2),
        OrderSupplier(1016, "Alisher Sadullayev", "06.08.2022", 2),
        OrderSupplier(1067, "Alisher Zokirov", "06.02.2022", 3),
    )

}


