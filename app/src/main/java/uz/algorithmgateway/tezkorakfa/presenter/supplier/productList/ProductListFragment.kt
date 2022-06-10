package uz.algorithmgateway.tezkorakfa.presenter.supplier.productList

import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.get
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.core.util.toast
import uz.algorithmgateway.tezkorakfa.data.models.Product
import uz.algorithmgateway.supplier.productList.InterfaceProductClick
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.create_orders_detiel.CreateOrderDeteils
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders_id.Profil
import uz.algorithmgateway.tezkorakfa.databinding.CreateOrdersDialogViewBinding
import uz.algorithmgateway.tezkorakfa.databinding.FragmentProductListBinding
import uz.algorithmgateway.tezkorakfa.presenter.supplier.SupplierActivity
import uz.algorithmgateway.tezkorakfa.presenter.supplier.adapter.AdapterTableSpinner
import uz.algorithmgateway.tezkorakfa.presenter.supplier.resource.OrderDetailListResource
import uz.algorithmgateway.tezkorakfa.presenter.supplier.resource.OrdersListResource
import uz.algorithmgateway.tezkorakfa.presenter.supplier.viewmodel.NetworkViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class ProductListFragment : Fragment(), CoroutineScope {

    lateinit var binding: FragmentProductListBinding
    private var productListAdapter: AdapterProductList? = null
    private var productList: List<Product>? = null
    private var productType: Int = 0
    private lateinit var alertDialog: AlertDialog

    @Inject
    lateinit var networkViewModel: NetworkViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.productListDetail(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        productList = productList()
        val mainActivity = activity as SupplierActivity
        mainActivity.bottomNavigationViewVisibilityGone()

        installTolbar()
        loadProduct()
//        loadProductList()
//        loadProductTypeSpinner()
        loadSearchView()
    }

    private fun loadProduct() {
        val get = arguments?.getString("product_id")
        if (!get.isNullOrEmpty()) {
            launch {
                networkViewModel.getOrderDetialList(get.toString()).collect {
                    when (it) {
                        is OrderDetailListResource.Error -> {
                            toast(it.message)
                        }
                        OrderDetailListResource.Loading -> {
                            toast("Loading...")
                        }
                        is OrderDetailListResource.SuccesListBYId -> {
                            toast("Success")
                            loadProductList(it.list.profil as ArrayList<Profil>)
//                            Log.d("pro777", "${it.list}")
//                        productListAdapter?.updateList(it.list.products)
                        }
                    }
                }
            }
        }

    }

    private fun installTolbar() {
        binding.toolbar.apply {
            backArrowFragment.setOnClickListener {
                findNavController().popBackStack()
            }
            searchToolbar.visibility = View.GONE
            titleTolbar.text = "Mahsulotlar ro'yxati"
            otherToolbar.setImageResource(R.drawable.ic_next_arrow_fr)
            otherToolbar.setOnClickListener {
                findNavController().navigate(R.id.foundListFragment)
            }
        }

    }

    private fun loadSearchView() {
        binding.editTextSearch.doOnTextChanged { text, start, before, count ->

//            val filterList: List<Product> = if (productType == 0) {
//                productList!!
//            } else {
//                productList!!.filter { s -> s.type == productType }
//            }
//
//            val searchList = mutableListOf<Product>()
//            for (i in filterList) {
//                if (text.toString().lowercase().toRegex().find(i.name.lowercase()) != null) {
//                    searchList.add(i)
//                }
//            }
//
//            searchList.let {
////                productListAdapter?.updateList(searchList)
//            }


        }
    }

//    private fun loadProductTypeSpinner() {
//        val adapter = AdapterTableSpinner(requireContext(), productTypeList(), true)
//        binding.spinnerProductType.adapter = adapter
//        binding.spinnerProductType.onItemSelectedListener =
//            object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long,
//                ) {
//                    productType = position
//                    filterProductList(position)
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//                }
//            }
//    }

//    private fun filterProductList(productType: Int) {
//
//        val filterList: List<Product> = if (productType == 0) {
//            productList!!
//        } else {
//            productList!!.filter { s -> s.type == productType }
//        }
//        filterList.let {
//            productListAdapter?.updateList(filterList)
//        }
//
//    }

    private fun loadProductList(product: ArrayList<Profil>) {
//        val contractNumber = arguments?.getString("contract_nuber")
//        if (contractNumber.isNullOrEmpty()) {
//
//        }

        productListAdapter = AdapterProductList(requireContext()) {
            showDialogView(it.id)
            toast("show Alert dialog")
        }
        productListAdapter!!.updateList(product)

        binding.rvProductList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvProductList.adapter = productListAdapter
    }

    private fun showDialogView(profil_id: Int) {
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = CreateOrdersDialogViewBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        val adapter = AdapterTableSpinner(requireContext(), typeList(), true)
        dialogBinding.spinnertypeBuy.adapter = adapter
        dialogBinding.configOrderBtn.setOnClickListener {
            val selectedItemPosition = dialogBinding.spinnertypeBuy.selectedItemPosition
            sendDataProduct(
                CreateOrderDeteils(
                    profil_id,
//                    "Ko'chadan",
                    typeList().get(selectedItemPosition),
                    dialogBinding.counts.text.toString().toInt(),
                    dialogBinding.price.text.toString().toInt()
                )
            )
//            alertDialog.dismiss()
        }
        alertDialog.show()

    }

    private fun sendDataProduct(createOrderDeteils: CreateOrderDeteils) {
        launch {
            networkViewModel.createOrdersDeteils(createOrderDeteils).collect() {
                when (it) {
                    is OrderDetailListResource.Error -> {
                        toast(it.message)
                    }
                    OrderDetailListResource.Loading -> {
                        toast("loading ... ")
                    }
                    is OrderDetailListResource.SuccesOrderDetils -> {
                        toast("Success !")
                        alertDialog.dismiss()
                    }
                }
            }
        }
    }

//    private fun productList() = listOf<Product>(
//        Product(1, "Alyuminiy profil", 1, 3, 70000, 210000, false),
//        Product(2, "Plastik profil", 1, 2, 80000, 160000, false),
//        Product(3, "Yodoviy oyna", 2, 3, 70000, 210000, true),
//        Product(4, "Gulli oyna", 2, 3, 70000, 210000, false),
//        Product(5, "Tutqich(oddiy)", 3, 3, 70000, 210000, false),
//        Product(6, "Tutqich(oddiy)", 3, 3, 70000, 210000, true),
//        Product(7, "Tutqich(oddiy)", 3, 3, 70000, 210000, false),
//        Product(8, "Alyuminiy profil", 1, 3, 70000, 210000, true),
//        Product(9, "Alyuminiy profil", 1, 3, 70000, 210000, false),
//        Product(10, "Alyuminiy profil", 1, 3, 70000, 210000, false),
//        Product(11, "Alyuminiy profil", 1, 3, 70000, 210000, false),
//        Product(12, "Plastik profil", 1, 2, 80000, 160000, false),
//        Product(13, "Yodoviy oyna", 2, 3, 70000, 210000, true),
//        Product(14, "Gulli oyna", 2, 3, 70000, 210000, false),
//        Product(15, "Tutqich(oddiy)", 3, 3, 70000, 210000, false),
//        Product(16, "Tutqich(oddiy)", 3, 3, 70000, 210000, true),
//        Product(17, "Tutqich(oddiy)", 3, 3, 70000, 210000, false),
//        Product(18, "Alyuminiy profil", 1, 3, 70000, 210000, true),
//        Product(19, "Alyuminiy profil", 1, 3, 70000, 210000, false)
//
//    )

    private fun productTypeList() = listOf<String>(
        "Barchasi",
        "Profil",
        "Oyna",
        "Padogonnik"
    )

    private fun typeList() = listOf<String>(
        "Ko'chadan",
        "Dillerdan",
    )

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()


}
