package uz.algorithmgateway.tezkorakfa.presenter.supplier.productList

import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.algorithmgateway.core.util.toast
import uz.algorithmgateway.tezkorakfa.data.models.Product
import uz.algorithmgateway.supplier.productList.InterfaceProductClick
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.CreateOrdersDialogViewBinding
import uz.algorithmgateway.tezkorakfa.databinding.FragmentProductListBinding
import uz.algorithmgateway.tezkorakfa.presenter.supplier.SupplierActivity
import uz.algorithmgateway.tezkorakfa.presenter.supplier.adapter.AdapterTableSpinner


class ProductListFragment : Fragment(), InterfaceProductClick {

    lateinit var binding: FragmentProductListBinding
    private var productListAdapter: AdapterProductList? = null
    private var productList: List<Product>? = null
    private var productType: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productList = productList()
        val mainActivity = activity as SupplierActivity
        mainActivity.bottomNavigationViewVisibilityGone()

        installTolbar()
        loadProductList()
        loadProductTypeSpinner()
        loadSearchView()
    }

    private fun installTolbar() {
        binding.toolbar.apply {
            backArrowFragment.setOnClickListener {
                findNavController().popBackStack()
            }
            searchToolbar.visibility = View.GONE
            titleTolbar.setText("Mahsulotlar ro'yxati")
            otherToolbar.setImageResource(R.drawable.ic_next_arrow_fr)
            otherToolbar.setOnClickListener {
                findNavController().navigate(R.id.foundListFragment)
            }
        }

    }

    private fun loadSearchView() {
        binding.editTextSearch.doOnTextChanged { text, start, before, count ->

            val filterList: List<Product> = if (productType == 0) {
                productList!!
            } else {
                productList!!.filter { s -> s.type == productType }
            }

            val searchList = mutableListOf<Product>()
            for (i in filterList) {
                if (text.toString().lowercase().toRegex().find(i.name.lowercase()) != null) {
                    searchList.add(i)
                }
            }

            searchList.let {
                productListAdapter?.updateList(searchList)
            }


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
                    filterProductList(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    private fun filterProductList(productType: Int) {

        val filterList: List<Product> = if (productType == 0) {
            productList!!
        } else {
            productList!!.filter { s -> s.type == productType }
        }
        filterList.let {
            productListAdapter?.updateList(filterList)
        }

    }

    private fun loadProductList() {
        productListAdapter = AdapterProductList(requireContext()) {

            showDialogView()
            toast("show Alert dialog")
        }
        binding.rvProductList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvProductList.adapter = productListAdapter
    }

    private fun showDialogView() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = CreateOrdersDialogViewBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))


        alertDialog.show()

    }

    private fun productList() = listOf<Product>(
        Product(1, "Alyuminiy profil", 1, 3, 70000, 210000, false),
        Product(2, "Plastik profil", 1, 2, 80000, 160000, false),
        Product(3, "Yodoviy oyna", 2, 3, 70000, 210000, true),
        Product(4, "Gulli oyna", 2, 3, 70000, 210000, false),
        Product(5, "Tutqich(oddiy)", 3, 3, 70000, 210000, false),
        Product(6, "Tutqich(oddiy)", 3, 3, 70000, 210000, true),
        Product(7, "Tutqich(oddiy)", 3, 3, 70000, 210000, false),
        Product(8, "Alyuminiy profil", 1, 3, 70000, 210000, true),
        Product(9, "Alyuminiy profil", 1, 3, 70000, 210000, false),
        Product(10, "Alyuminiy profil", 1, 3, 70000, 210000, false),
        Product(11, "Alyuminiy profil", 1, 3, 70000, 210000, false),
        Product(12, "Plastik profil", 1, 2, 80000, 160000, false),
        Product(13, "Yodoviy oyna", 2, 3, 70000, 210000, true),
        Product(14, "Gulli oyna", 2, 3, 70000, 210000, false),
        Product(15, "Tutqich(oddiy)", 3, 3, 70000, 210000, false),
        Product(16, "Tutqich(oddiy)", 3, 3, 70000, 210000, true),
        Product(17, "Tutqich(oddiy)", 3, 3, 70000, 210000, false),
        Product(18, "Alyuminiy profil", 1, 3, 70000, 210000, true),
        Product(19, "Alyuminiy profil", 1, 3, 70000, 210000, false)

    )

    private fun productTypeList() = listOf<String>(
        "Barchasi",
        "Profil",
        "Oyna",
        "Padogonnik"
    )

    override fun onButtonClick(product: Product) {
        Toast.makeText(requireContext(), "FoundButtonClicked", Toast.LENGTH_SHORT).show()
    }


}
