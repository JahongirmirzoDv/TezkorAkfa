package uz.algorithmgateway.tezkorakfa.presenter.supplier.purchased

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_found_product_by_id.GetFoundProductByIdItem
import uz.algorithmgateway.tezkorakfa.databinding.FragmentPurchaseSecondBinding
import uz.algorithmgateway.tezkorakfa.presenter.supplier.adapter.AdapterTableSpinner
import uz.algorithmgateway.tezkorakfa.presenter.supplier.foundList.AdapterFoundList

class SecondPageFragment :
    Fragment() {


    lateinit var binding: FragmentPurchaseSecondBinding

    private var productType: Int = 0
    private var byWhere: Int = 0
    private lateinit var productList: List<GetFoundProductByIdItem>
    private var foundListAdapter: AdapterFoundList? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPurchaseSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productList = ArrayList()
        loadProductList()
        loadProductTypeSpinner()
        loadSearchView()
    }

    private fun loadSearchView() {
        binding.editTextSearch.doOnTextChanged { text, start, before, count ->

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
                    filterProductList()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun filterProductList() {

    }

    private fun loadProductList() {
        foundListAdapter = AdapterFoundList()
        binding.rvProductList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvProductList.adapter = foundListAdapter
    }

    private fun productTypeList(): List<String> = listOf(
        "Turi",
        "Profil",
        "Oyna"
    )
}