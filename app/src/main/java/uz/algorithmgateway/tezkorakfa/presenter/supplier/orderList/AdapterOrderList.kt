package uz.algorithmgateway.tezkorakfa.presenter.supplier.orderList

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import uz.algorithmgateway.tezkorakfa.core.util.layoutInflater
import uz.algorithmgateway.tezkorakfa.data.models.OrderSupplier
import uz.algorithmgateway.supplier.orderList.InterfaceOrderClick
import uz.algorithmgateway.tezkorakfa.R
import uz.algorithmgateway.tezkorakfa.databinding.ItemOrderListBinding

class AdapterOrderList(
    var itemClickListener: (OrderSupplier) -> Unit
) : RecyclerView.Adapter<AdapterOrderList.VH>() {
    private var myList = mutableListOf<OrderSupplier>()
    private lateinit var ctx: Context

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<OrderSupplier>) {
        myList.clear()
        myList.addAll(newList)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        ctx = parent.context
        return VH(
            ItemOrderListBinding.inflate(parent.layoutInflater, parent, false)
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(myList[position])
    }

    override fun getItemCount(): Int = myList.size

    inner class VH(private val binding: ItemOrderListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.M)
        fun onBind(item: OrderSupplier) {
            binding.tvNumber.text = item.id.toString()
            binding.tvPrice.text = item.price
            binding.tvTime.text = item.time
            when (item.status) {
                1 -> binding.ivStatus.setBackgroundColor(ctx.getColor(R.color.color_green))
                2 -> binding.ivStatus.setBackgroundColor(ctx.getColor(R.color.color_gray))
                3 -> binding.ivStatus.setBackgroundColor(ctx.getColor(R.color.color_red))
            }

            itemView.setOnClickListener {
                itemClickListener.invoke(item)
            }
        }
    }
}