package uz.algorithmgateway.measurer.ui.orders

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.algorithmgateway.core.util.layoutInflater
import uz.algorithmgateway.data.api.models.Order
import uz.algorithmgateway.data.api.models.OrderResponse
import uz.algorithmgateway.data.models.UiOrder
import uz.algorithmgateway.measurer.databinding.ItemOrderBinding

class OrderListAdapter(
    private val onAcceptClick: (order: Order) -> Unit,
    private val adapterOnClick: AdapterOnClick
) :
    RecyclerView.Adapter<OrderListAdapter.VH>() {

    private var myList: MutableList<Order> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Order>) {
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
        fun onBind(item: Order) {
            binding.tvOrderNumber.text = "№ ${item.id}"
            binding.tvCustomerName.text = "${item.client.firstName} ${item.client.lastName}"
            binding.tvPhone1.text = item.client.mainPhoneNumber
            binding.tvPhone2.text = item.client.phoneNumber
            binding.tvComment.text = "Izoh: ${item.comment}"
            binding.tvAddress.text = item.address
            binding.btnAccept.setOnClickListener {
                onAcceptClick(item)
            }
            binding.btnPhone.setOnClickListener {
                item.contractNumber?.let { it1 -> adapterOnClick.onCallClick(it1) }
            }
        }
    }
}