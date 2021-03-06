package uz.algorithmgateway.tezkorakfa.presenter.supplier.givenMoney

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.algorithmgateway.core.util.toast
import uz.algorithmgateway.tezkorakfa.base.MyApplication
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.create_money.Create_MoneyReq
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.supplier_models.get_money_list.GetMoneyListRes
import uz.algorithmgateway.tezkorakfa.databinding.FragmentGivenMoneyBinding
import uz.algorithmgateway.tezkorakfa.databinding.GivenMoneyDialogBinding
import uz.algorithmgateway.tezkorakfa.presenter.supplier.adapter.AdapterTableSpinner
import uz.algorithmgateway.tezkorakfa.presenter.supplier.resource.ProductFoundResource
import uz.algorithmgateway.tezkorakfa.presenter.supplier.viewmodel.NetworkViewModel
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GivenMoneyFragment : Fragment(), CoroutineScope {

    lateinit var binding: FragmentGivenMoneyBinding

    private lateinit var givenMoneyList: ArrayList<GetMoneyListRes>
    private var givenMoneyListAdapter: AdapterGivenMoneyList? = null


    @Inject
    lateinit var networkViewModel: NetworkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.givenMoneyFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentGivenMoneyBinding.inflate(inflater, container, false)
        givenMoneyList = ArrayList()
        loadRvData()
        loadBtn()
//
//        //load list
//        loadGivenMoneyList()
//
//        //load search view
        loadSearchView()

        return binding.root
    }

    private fun loadRvData() {
        launch {
            networkViewModel.getMoneyList().collect {
                when (it) {
                    is ProductFoundResource.Error -> {
                        toast(it.message)
                    }
                    ProductFoundResource.Loading -> {
                        toast("Loading...")
                    }
                    is ProductFoundResource.SuccesListMoney -> {
                        loadGivenMoneyList(it.data)
                        givenMoneyList = it.data
                    }
                }
            }
        }

    }

    private fun loadBtn() {
        binding.moneyBtn.setOnClickListener {
            showDialogView()
        }

    }

    private fun showDialogView() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = GivenMoneyDialogBinding.inflate(layoutInflater)
        builder.setView(dialogBinding.root)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val adapter = AdapterTableSpinner(requireContext(), productTypeList(), true)
        dialogBinding.spinnerRoomOrDoor.adapter = adapter

        dialogBinding.saveMoney.setOnClickListener {
            val selectedItemPosition = dialogBinding.spinnerRoomOrDoor.selectedItemPosition
            sendMoney(
//                productTypeList().get(selectedItemPosition),
               "23",
                dialogBinding.summa.text.toString()
            )
//            alertDialog.dismiss()
        }

        alertDialog.show()

    }

    private fun sendMoney(contractId: String, summa: String) {
        launch {
            networkViewModel.createMoney(Create_MoneyReq(contractId.toInt(), summa.toInt()))
                .collect {
                    when (it) {
                        is ProductFoundResource.Error -> {
                            toast(it.message)
                        }
                        ProductFoundResource.Loading -> {
                            toast("Loading...")
                        }
                        is ProductFoundResource.Succes -> {
                            toast("${it.success}")
                        }
                    }
                }
        }

    }


    private fun loadSearchView() {
        val list = ArrayList<GetMoneyListRes>()
        binding.editTextSearch.doOnTextChanged { text, start, before, count ->
            val text_ = text.toString()
            if (text_ != "") {
                for (i in givenMoneyList) {
                    if (i.contract_number.contains(text_.toRegex())) {
                        list.add(i)
                        givenMoneyListAdapter?.updateList(list)
                    }
                }
            } else givenMoneyListAdapter?.updateList(givenMoneyList)
        }
    }

    private fun loadGivenMoneyList(data: ArrayList<GetMoneyListRes>) {
        givenMoneyListAdapter = AdapterGivenMoneyList()
        givenMoneyListAdapter!!.updateList(data)
        binding.rvOrderList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvOrderList.adapter = givenMoneyListAdapter
    }
    private fun productTypeList(): List<String> = listOf(
        "1",
        "2",
        "3",
        "4",
        "5",
    )

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

}