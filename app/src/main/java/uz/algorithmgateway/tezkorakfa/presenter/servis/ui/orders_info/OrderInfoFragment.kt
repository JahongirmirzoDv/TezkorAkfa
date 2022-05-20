package uz.algorithmgateway.tezkorakfa.presenter.servis.ui.orders_info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.data.models.OrderSupplier
import uz.algorithmgateway.tezkorakfa.databinding.FragmentOrderInfoBinding
import uz.algorithmgateway.tezkorakfa.databinding.FragmentServisHomeBinding
import uz.algorithmgateway.tezkorakfa.presenter.servis.ui.adapters.AdaptersConfigOrdersServis
import uz.algorithmgateway.tezkorakfa.presenter.servis.ui.models.ModelConfigOrdersServis

class OrderInfoFragment : Fragment(R.layout.fragment_order_info) {

    lateinit var adaptersConfigOrdersServis: AdaptersConfigOrdersServis
    val binding: FragmentOrderInfoBinding by viewBinding(FragmentOrderInfoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        installToolbar()
        loadDataRv()
    }

    private fun installToolbar() {
        binding.toolbar.apply {
            backArrowFragment.setOnClickListener {
                findNavController().popBackStack()
            }
            otherToolbar.visibility = View.GONE
            searchToolbar.visibility = View.GONE
        }
        binding.apply {
            configOrderBtn.setOnClickListener {
                findNavController().navigate(R.id.acceptOrderServisFragment)
            }
        }
    }

    private fun loadDataRv() {
        adaptersConfigOrdersServis = AdaptersConfigOrdersServis()
        adaptersConfigOrdersServis.updateList(createOrderList())
        binding.rvList.adapter = adaptersConfigOrdersServis
    }

    private fun createOrderList(): List<ModelConfigOrdersServis> = listOf(
        ModelConfigOrdersServis("Eshik", "2", "250", "30", "50"),
        ModelConfigOrdersServis("Rom,", "3", "150", "3", "70"),
        ModelConfigOrdersServis("Eshik", "2", "250", "30", "50"),
        ModelConfigOrdersServis("Rom,", "3", "150", "3", "70"),
        ModelConfigOrdersServis("Eshik", "2", "250", "30", "50"),
        ModelConfigOrdersServis("Rom,", "3", "150", "3", "70"),
        ModelConfigOrdersServis("Eshik", "2", "250", "30", "50"),
        ModelConfigOrdersServis("Rom,", "3", "150", "3", "70"),
        ModelConfigOrdersServis("Eshik", "2", "250", "30", "50"),
        ModelConfigOrdersServis("Rom,", "3", "150", "3", "70")

    )


}