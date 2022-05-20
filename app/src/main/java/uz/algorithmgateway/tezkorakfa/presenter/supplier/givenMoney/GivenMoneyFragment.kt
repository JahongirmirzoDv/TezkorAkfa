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
import uz.algorithmgateway.tezkorakfa.data.models.GivenMoney
import uz.algorithmgateway.tezkorakfa.databinding.CreateOrdersDialogViewBinding
import uz.algorithmgateway.tezkorakfa.databinding.FragmentGivenMoneyBinding
import uz.algorithmgateway.tezkorakfa.databinding.GivenMoneyDialogBinding
import uz.algorithmgateway.tezkorakfa.presenter.supplier.adapter.AdapterTableSpinner

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

        loadBtn()

        //load list
        loadGivenMoneyList()

        //load search view
        loadSearchView()

        return binding.root
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
            alertDialog.dismiss()
        }

        alertDialog.show()

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

    private fun productTypeList(): List<String> = listOf(
        "1",
        "2",
        "3",
        "4",
        "5",
    )

}