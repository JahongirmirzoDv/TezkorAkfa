package uz.algorithmgateway.tezkorakfa.measurer.ui.orders

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.algorithmgateway.core.util.layoutInflater
import uz.algorithmgateway.data.api.models.Order
import uz.algorithmgateway.tezkorakfa.data.retrofit.models.sales_order_list.Result
import uz.algorithmgateway.tezkorakfa.databinding.ItemOrderBinding

class OrderListAdapter(
    private val onAcceptClick: (order: Result) -> Unit,
    var onclick: onClick,
) :
    RecyclerView.Adapter<OrderListAdapter.VH>() {

    private var myList: MutableList<Result> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Result>) {
        myList.clear()
        myList.addAll(newList)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            ItemOrderBinding.inflate(parent.layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(myList[position])
    }

    override fun getItemCount(): Int = myList.size

    inner class VH(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun onBind(item: Result) {
            binding.tvOrderNumber.text = "№ ${item.id}"
            binding.tvCustomerName.text = "${item.client.first_name} ${item.client.last_name}"
            binding.tvPhone1.text = item.client.main_phone_number
            binding.tvPhone2.text = item.client.phone_number
            binding.tvComment.text = "Izoh: ${item.comment}"
            binding.tvAddress.text = item.address
            binding.btnAccept.setOnClickListener {
                onAcceptClick(item)
            }
            binding.btnPhone.setOnClickListener {
                item.contract_number?.let { it1 -> onclick.onCallClick(it1) }
            }
        }
    }

    interface onClick {
        fun onCallClick(number: String)
    }
}