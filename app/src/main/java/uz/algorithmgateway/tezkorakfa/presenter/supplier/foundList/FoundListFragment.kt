package uz.algorithmgateway.tezkorakfa.presenter.supplier.foundList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.data.models.FoundProduct
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_found_product_by_id.GetFoundProductByIdItem
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_orders_id.Profil
import uz.algorithmgateway.tezkorakfa.databinding.FragmentFoundListBinding
import uz.algorithmgateway.tezkorakfa.presenter.supplier.adapter.AdapterTableSpinner
import uz.algorithmgateway.tezkorakfa.presenter.supplier.resource.ProductFoundResource
import uz.algorithmgateway.tezkorakfa.presenter.supplier.viewmodel.NetworkViewModel
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class FoundListFragment : Fragment(), CoroutineScope {

    lateinit var binding: FragmentFoundListBinding

    private var productType: Int = 0
    private var byWhere: Int = 0
    private val productList: List<FoundProduct> = createOrderList()
    private var foundListAdapter: AdapterFoundList? = null
    private lateinit var listData: ArrayList<GetFoundProductByIdItem>

    @Inject
    lateinit var networkViewModel: NetworkViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.foundListFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFoundListBinding.inflate(inflater, container, false)

        //insall toolbar
        installToolbar()

        loadDataNetWork()
        loadFoundByWhereSpinner()


//        //load list
//        loadOrderList()
//
//        //load spinner
//        loadFoundByWhereSpinner()
//        loadProductTypeSpinner()
//
//        //load search view
//        loadSearchView()

        return binding.root
    }

    private fun loadDataNetWork() {
        listData = ArrayList()
        launch {
            networkViewModel.getProductFoundData("23").collect {
                when (it) {
                    is ProductFoundResource.Error -> {
                        toast(it.message)
                    }
                    ProductFoundResource.Loading -> {
                        toast("Loading..")
                    }
                    is ProductFoundResource.SuccesList -> {
                        loadOrderList(it.data)
//                        toast(it.data.toString())
                    }
                }
            }

        }
    }


    private fun installToolbar() {
        binding.toolbar.apply {
            searchToolbar.visibility = View.GONE
            otherToolbar.visibility = View.GONE
            titleTolbar.text = "Topilganlar"
            backArrowFragment.setOnClickListener {
                findNavController().popBackStack()
            }
        }

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

//            searchList.let {
//                foundListAdapter?.updateList(searchList)
//            }


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
//                    byWhere = position
                    filterList(position)
//                    filterOrderList()
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
                    filterList(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun filterList(position: Int) {
//        listData = ArrayList()
        val sortData: List<GetFoundProductByIdItem> = if (position == 0) {
            listData
        } else {
            listData.filter { s -> s.purchase.get(0).place_of_origin.equals("Dillerdan") }
        }

        sortData.let {
            foundListAdapter?.updateList(sortData)
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
//        filterList.let {
//            foundListAdapter?.updateList(filterList)
//        }
    }

    private fun loadOrderList(data: ArrayList<GetFoundProductByIdItem>) {

        listData.addAll(data)
        foundListAdapter = AdapterFoundList()
        foundListAdapter?.updateList(data)
        binding.rvProductList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvProductList.adapter = foundListAdapter

    }

    private fun foundByWhereList(): List<String> = listOf(
        "Barchasi",
        "Dilerdan",
        "Ko'chadan"
    )

    private fun productTypeList(): List<String> = listOf(
        "Barchasi",
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

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

}