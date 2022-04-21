package uz.algorithmgateway.tezkorakfa.supplier.givenMoney

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import uz.algorithmgateway.tezkorakfa.data.models.GivenMoney
import uz.algorithmgateway.tezkorakfa.databinding.FragmentGivenMoneyBinding

class GivenMoneyFragment : Fragment() {

    lateinit var binding: FragmentGivenMoneyBinding

    private val givenMoneyList: List<GivenMoney> = createGivenMoneyList()
    private var givenMoneyListAdapter: AdapterGivenMoneyList? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentGivenMoneyBinding.inflate(inflater, container, false)
        //load list
        loadGivenMoneyList()

        //load search view
        loadSearchView()

        return binding.root
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