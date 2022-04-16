package uz.algorithmgateway.supplier.givenMoney

import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.algorithmgateway.core.Screen
import uz.algorithmgateway.data.models.GivenMoney
import uz.algorithmgateway.data.models.OrderSupplier
import uz.algorithmgateway.supplier.R
import uz.algorithmgateway.supplier.databinding.FragmentGivenMoneyBinding

class GivenMoneyFragment : Screen(R.layout.fragment_given_money) {

    private val binding: FragmentGivenMoneyBinding by viewBinding()

    private val givenMoneyList: List<GivenMoney> = createGivenMoneyList()
    private var givenMoneyListAdapter: AdapterGivenMoneyList? = null

    override fun setup() {
        super.setup()

        //load list
        loadGivenMoneyList()

        //load search view
        loadSearchView()
    }

    private fun loadSearchView() {
        binding.editTextSearch.doOnTextChanged { text, start, before, count ->


            val searchList = mutableListOf<GivenMoney>()
            for (i in givenMoneyList) {
                if (text.toString().toRegex().find(i.orderId.toString()) != null) {
                    searchList.add(i)
                }
            }

            searchList.let {
                givenMoneyListAdapter?.updateList(searchList)
            }


        }
    }

    private fun loadGivenMoneyList() {
        givenMoneyListAdapter = AdapterGivenMoneyList()
        binding.rvOrderList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvOrderList.adapter = givenMoneyListAdapter
        givenMoneyListAdapter!!.updateList(createGivenMoneyList())
    }

    private fun createGivenMoneyList(): List<GivenMoney> = listOf(
        GivenMoney(1, "120 000", "110 000", "12.04.2022"),
        GivenMoney(2, "120 000", "110 000", "12.04.2022"),
        GivenMoney(3, "120 000", "110 000", "12.04.2022"),
        GivenMoney(4, "120 000", "110 000", "12.04.2022"),
        GivenMoney(5, "120 000", "110 000", "12.04.2022")
    )

}