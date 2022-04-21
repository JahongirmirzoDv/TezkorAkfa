package uz.algorithmgateway.tezkorakfa.supplier.foundList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import uz.algorithmgateway.tezkorakfa.data.models.FoundProduct
import uz.algorithmgateway.tezkorakfa.databinding.FragmentFoundListBinding
import uz.algorithmgateway.tezkorakfa.supplier.adapter.AdapterTableSpinner

class FoundListFragment : Fragment() {

    lateinit var binding: FragmentFoundListBinding

    private var productType: Int = 0
    private var byWhere: Int = 0
    private val productList: List<FoundProduct> = createOrderList()
    private var foundListAdapter: AdapterFoundList? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFoundListBinding.inflate(inflater, container, false)

        //load list
        loadOrderList()

        //load spinner
        loadFoundByWhereSpinner()
        loadProductTypeSpinner()

        //load search view
        loadSearchView()

        return binding.root
    }

    private fun loadSearchView() {
        binding.editTextSearch.doOnTextChanged { text, start, before, count ->

            val filterList: List<FoundProduct> = if (byWhere == 0 && productType == 0) {
                productList
            } else if (byWhere == 0 && productType != 0) {
                productList.filter { s -> (s.type == productType) }
            } else if (byWhere != 0 && productType == 0) {
                productList.filter { s -> (byWhere == 1 && s.countByDealer != 0 || byWhere == 2 && s.countByOutside != 0) }
            } else {
                productList.filter { s -> (s.type == productType) && (byWhere == 1 && s.countByDealer != 0 || byWhere == 2 && s.countByOutside != 0) }
            }

            val searchList = mutableListOf<FoundProduct>()
            for (i in filterList) {
                if (text.toString().toRegex().find(i.name) != null) {
                    //TODO bug
                    searchList.add(i)
                }
            }

            searchList.let {
                foundListAdapter?.updateList(searchList)
            }


        }
    }

    private fun loadFoundByWhereSpinner() {
        val adapter = AdapterTableSpinner(requireContext(), foundByWhereList(), true)
        binding.spinnerByWhere.adapter = adapter
        binding.spinnerByWhere.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    byWhere = position
                    filterOrderList()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun loadProductTypeSpinner() {
        val adapter = AdapterTableSpinner(requireContext(), productTypeList(), true)
        binding.spinnerProductType.adapter = adapter
        binding.spinnerProductType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    productType = position
                    filterOrderList()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun filterOrderList() {
        val filterList: List<FoundProduct> = if (byWhere == 0 && productType == 0) {
            productList
        } else if (byWhere == 0 && productType != 0) {
            productList.filter { s -> (s.type == productType) }
        } else if (byWhere != 0 && productType == 0) {
            productList.filter { s -> (byWhere == 1 && s.countByDealer != 0 || byWhere == 2 && s.countByOutside != 0) }
        } else {
            productList.filter { s -> (s.type == productType) && (byWhere == 1 && s.countByDealer != 0 || byWhere == 2 && s.countByOutside != 0) }
        }
        filterList.let {
            foundListAdapter?.updateList(filterList)
        }
    }

    private fun loadOrderList() {
        foundListAdapter = AdapterFoundList()
        binding.rvProductList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvProductList.adapter = foundListAdapter
    }

    private fun foundByWhereList(): List<String> = listOf(
        "Qayerdan",
        "Dilerdan",
        "Ko'chadan"
    )

    private fun productTypeList(): List<String> = listOf(
        "Turi",
        "Profil",
        "Oyna"
    )

    private fun createOrderList(): List<FoundProduct> = listOf(
        FoundProduct(1, "Aldoks-kosa(Silver)", 1, 3, "70 000", 3, "210 000", 0, "0"),
        FoundProduct(1, "Aldoks-kosa(Silver)", 1, 3, "70 000", 3, "210 000", 0, "0"),
        FoundProduct(1, "Aldoks-kosa(Silver)", 1, 3, "70 000", 0, "0", 3, "210 000"),
        FoundProduct(1, "Aldoks-kosa(Silver)", 1, 3, "70 000", 0, "0", 3, "220 000"),
        FoundProduct(1, "Aldoks-kosa(Silver)", 1, 3, "70 000", 1, "70 000", 2, "150 000"),
        FoundProduct(1, "Aldoks-kosa(Silver)", 2, 3, "70 000", 3, "210 000", 0, "0"),
        FoundProduct(1, "Aldoks-kosa(Silver)", 2, 3, "70 000", 3, "210 000", 0, "0"),
        FoundProduct(1, "Aldoks-kosa(Silver)", 2, 3, "70 000", 3, "210 000", 0, "0"),
        FoundProduct(1, "Aldoks-kosa(Silver)", 2, 3, "70 000", 0, "0", 3, "220 000"),
        FoundProduct(1, "Aldoks-kosa(Silver)", 2, 3, "70 000", 1, "210 000", 2, "150 000"),
        FoundProduct(1, "Aldoks-kosa(Silver)", 2, 3, "70 000", 2, "210 000", 1, "80 000")
    )

}